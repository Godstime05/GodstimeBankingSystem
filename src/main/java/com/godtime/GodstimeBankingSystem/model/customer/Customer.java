package com.godtime.GodstimeBankingSystem.model.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godtime.GodstimeBankingSystem.model.branch.Branch;
import com.godtime.GodstimeBankingSystem.model.facility.Facility;
import com.godtime.GodstimeBankingSystem.model.loan.Loan;
import com.godtime.GodstimeBankingSystem.model.product.Product;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "Customer")
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Pattern(
            regexp = "^([1-9]{1})([0-9]{2})([0-9]{2})([0-9]{2})([0-9]{2})[0-9]{3}([0-9]{1})[0-9]{1}$",
            message = "Invalid National ID number"
    )
    private String nationalId;

    @Column(name = "isCommissionPaid")
    private boolean isCommissionPaid;

    @Column(name = "commissionPaidDate")
    private LocalDate commissionPaidDate;

    @ManyToOne
    @JoinColumn(
            name = "branch_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "branch_customer_fk_id"
            )
    )
    @JsonIgnore
    private Branch branch;

    @ManyToOne
    @JoinColumn(
            name = "branch_id",
            referencedColumnName = "id",
            insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "customer_product_id_fk")
    )
    private Product product;

    @JsonIgnore
    @OneToOne(mappedBy = "customer")
    private Facility facility;

    @JsonIgnore
    @OneToMany(mappedBy = "grantedCustomer")
    private List<Loan> loansOfGrantedCustomer = new LinkedList<>();

    @Column(name = "requested_column")
    private String grantorNationalId;

    @Column(name = "commission_amount")
    private double commissionAmount;



    public Customer(String firstName, String lastName, String phoneNumber, String nationalId, boolean b) {
    }

    @Transactional
    public void addLoanToGrantedCustomer(Loan loan) {
        loansOfGrantedCustomer.add(loan);
        loan.setGrantedCustomer(this);
    }

    public void removeLoanFromGrantedCustomer(Loan loan) {
        if (loansOfGrantedCustomer.contains(loan)) {
            loansOfGrantedCustomer.remove(loan);
            loan.setCustomer(null);
        }
    }

    public void addFacility(Facility facility) {
        this.setFacility(facility);
        facility.setCustomer(this);
    }
}