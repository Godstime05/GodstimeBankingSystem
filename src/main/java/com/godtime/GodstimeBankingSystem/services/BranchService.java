package com.godtime.GodstimeBankingSystem.services;

import com.godtime.GodstimeBankingSystem.exceptions.NotFoundException;
import com.godtime.GodstimeBankingSystem.model.branch.Branch;
import com.godtime.GodstimeBankingSystem.model.branch.dto.BranchCreationRequest;
import com.godtime.GodstimeBankingSystem.model.employee.Employee;
import com.godtime.GodstimeBankingSystem.repository.BranchRepository;
import com.godtime.GodstimeBankingSystem.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BranchService {
    public final BranchRepository branchRepository;
    public final EmployeeRepository employeeRepository;
    private Optional<Branch>findBranchById(Long id){
        return branchRepository.findBranchById(id);
    }

    public ResponseEntity<?>addBranch(BranchCreationRequest branchCreationRequest){
        Optional<Branch> db = Optional.ofNullable(findBranchById(branchCreationRequest.getBranchId())
                .orElseThrow(() ->new NotFoundException(String.format("Branch id %s not found", branchCreationRequest.getBranchId()))));

        db.get().addBranch(new Branch(branchCreationRequest.getBranchName(),branchCreationRequest.getBranchAddress()));
        branchRepository.save(db.get());

        return ResponseEntity.status(HttpStatus.OK).body(branchCreationRequest);
    }

    public ResponseEntity<?> getBranch(Long id){
        Optional<Branch> branch = Optional.ofNullable(findBranchById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", id))));
        return ResponseEntity.status(HttpStatus.OK).body(branch);
    }

    public ResponseEntity<?> updateBranch(String branchName, String branchAddress, Long id) {
        Optional<Branch> branch = Optional.ofNullable(findBranchById(id).
                orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", id))));

        branchRepository.updateBranch(branchName, branchAddress, id);
        return ResponseEntity.status(HttpStatus.OK).body(branch);
    }

    public ResponseEntity<?> deleteBranch(Long branchId) {
        Optional<Branch> branch = Optional.ofNullable(findBranchById(branchId).
                orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", branchId))));

        for (Branch br : branch.get().getBranches()) {
            br.setParent(branch.get().getParent());
        }
        branchRepository.deleteById(branchId);
        return ResponseEntity.status(HttpStatus.OK).body
                (branch);
    }

    public ResponseEntity<?> addEmployeesToBranch(Long branchId, Long[] employeesId) {
        Optional<Branch> branch = Optional.ofNullable(branchRepository.findBranchById(branchId).
                orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", branchId))));
        for (Long id : employeesId) {
            Optional<Employee> employee = employeeRepository.findById(id);
            if (employee.isPresent()) branch.get().addEmployee(employee.get());
        }
        branchRepository.save(branch.get());
        return ResponseEntity.status(HttpStatus.OK).body
                (branch.get());
    }

    public ResponseEntity<?> removeEmployeesFromBranch(Long branchId, Long[] employeesId) {
        Optional<Branch> branch = Optional.ofNullable(findBranchById(branchId).
                orElseThrow(() -> new NotFoundException(String.format("Branch with id %s not found", branchId))));
        for (Long id : employeesId) {
            Optional<Employee> employee = employeeRepository.findById(id);
//            branch.get().getEmployeeList().remove(employee.get());
            branch.get().removeEmployee(employee.get());
        }

        branchRepository.save(branch.get());

        return ResponseEntity.status(HttpStatus.OK).body(branch);
    }

}
