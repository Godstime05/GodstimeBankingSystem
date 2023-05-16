package com.godtime.GodstimeBankingSystem.services;

import com.godtime.GodstimeBankingSystem.dto.requests.RegistrationRequest;
import com.godtime.GodstimeBankingSystem.model.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmployeeService employeeService;

    public ResponseEntity<?>
    registerEmployee(RegistrationRequest registrationRequest) {
        return employeeService.signUpUser(
                new Employee(
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPassword(),
                        registrationRequest.getRole()
                )
        );
    }
}
