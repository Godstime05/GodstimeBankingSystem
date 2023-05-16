package com.godtime.GodstimeBankingSystem.model.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godtime.GodstimeBankingSystem.enums.Role;
import com.godtime.GodstimeBankingSystem.model.branch.Branch;
import com.godtime.GodstimeBankingSystem.model.loan.Loan;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Employee")
@Table(name = "employee")
@NoArgsConstructor
@Getter
@Setter
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "First name must not be empty")
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name must not be empty")
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email(message = "Check email constraints")
    private String email;

    @NotBlank(message = "Password must not be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "branch_id_fk"))
    @JsonIgnore
    private Branch branch;

    @JsonIgnore
    @OneToMany(mappedBy = "loanOfficer", cascade = CascadeType.ALL)
    private List<Loan> loansOfLoanOfficer = new LinkedList<>();

    public Employee(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void addLoanToLoanOfficer(Loan loan) {
        if (!loansOfLoanOfficer.contains(loan)) {
            loansOfLoanOfficer.add(loan);
            loan.setLoanOfficer(this);
        }
    }

    public void removeLoanFromLoanOfficer(Loan loan) {
        if (loansOfLoanOfficer.contains(loan)) {
            loansOfLoanOfficer.remove(loan);
            loan.setLoanOfficer(null);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
