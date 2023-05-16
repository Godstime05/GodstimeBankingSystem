package com.godtime.GodstimeBankingSystem.model.branch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godtime.GodstimeBankingSystem.model.customer.Customer;
import com.godtime.GodstimeBankingSystem.model.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Branch")
@Table(name = "branch")
@NoArgsConstructor
@Getter
@Setter
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_sequence")
    @SequenceGenerator(name = "branch_sequence", sequenceName = "branch_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "branch_address", nullable = false)
    private String branchAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "branch", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Employee> employeeList = new ArrayList<>();

    @OneToMany(mappedBy = "branch", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Customer> customers = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "branch_parent_id", referencedColumnName = "id")
    private Branch parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<Branch> branches = new ArrayList<>();

    public Branch(String branchName, String branchAddress) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
    }

    public void addCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            customers.add(customer);
            customer.setBranch(this);
        }
    }

    public void removeCustomer(Customer customer) {
        if (customers.contains(customer)) {
            customers.remove(customer);
            customer.setBranch(null);
        }
    }

    public void addEmployee(Employee employee) {
        if (!employeeList.contains(employee)) {
            employeeList.add(employee);
            employee.setBranch(this);
        }
    }

    public void removeEmployee(Employee employee) {
        if (employeeList.contains(employee)) {
            employeeList.remove(employee);
            employee.setBranch(null);
        }
    }

    public void addBranch(Branch branch) {
        if (!branches.contains(branch)) {
            branches.add(branch);
            branch.setParent(this);
        }
    }
}