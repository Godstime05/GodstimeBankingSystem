package com.godtime.GodstimeBankingSystem.model.loan;

import com.godtime.GodstimeBankingSystem.enums.Status;
import com.godtime.GodstimeBankingSystem.model.customer.Customer;
import com.godtime.GodstimeBankingSystem.model.employee.Employee;
import com.godtime.GodstimeBankingSystem.model.facility.Facility;
import com.godtime.GodstimeBankingSystem.model.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.internal.Pair;

import java.time.LocalDate;

@Entity(name = "Loan")
@Table(name = "loan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_sequence")
    @SequenceGenerator(name = "loan_sequence", sequenceName = "loan_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "loan_product_fk"), referencedColumnName = "id")
    private Product product;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "loan_customer_fk"), referencedColumnName = "id")
    private Customer customer;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "facility_id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "granted_customer_id", referencedColumnName = "id")
    private Customer grantedCustomer;

    @ManyToOne
    @JoinColumn(name = "loan_officer_id")
    private Employee loanOfficer;

    @Column(name = "amount")
    private double amount;

    @Column(name = "number_of_repayments")
    private int numberOfRepayments;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    private RepaymentSchedule repaymentSchedule;

    public Loan(Product product, Customer customer, Facility facility, double amount, int repayments, Status status) {
    }

    public void addProduct(Product product) {
        setProduct(product);
    }

    public void addRepaymentSchedule(RepaymentSchedule repaymentSchedule) {
        for (int i = 1; i < numberOfRepayments; i++) {
            repaymentSchedule.getInstallment().add(Pair.of(LocalDate.now().plusMonths(i), false));
        }
        setRepaymentSchedule(repaymentSchedule);
        repaymentSchedule.setLoan(this);
    }

}