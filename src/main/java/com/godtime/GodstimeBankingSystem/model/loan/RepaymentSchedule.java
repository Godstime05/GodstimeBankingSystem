package com.godtime.GodstimeBankingSystem.model.loan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.internal.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity(name = "RepaymentSchedule")
@Table(name = "repayment_schedule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "repayment_sequence")
    @SequenceGenerator(name = "repayment_sequence", sequenceName = "repayment_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "monthly_amount")
    private double monthlyAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "number_of_installments")
    private int numberOfInstallments;

    @Column(name = "current_installment")
    private int currentInstallment;

    @Transient
    private ArrayList<Pair<LocalDate, Boolean>> installment = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "loan_id", foreignKey = @ForeignKey(name = "repayment_schedule_loan_fk"),
            referencedColumnName = "id")
    private Loan loan;

    public RepaymentSchedule(double totalAmount, double monthlyAmount,
                             LocalDate startDate, LocalDate endDate, int numberOfInstallments,
                             int currentInstallment) {
        this.totalAmount = totalAmount;
        this.monthlyAmount = monthlyAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfInstallments = numberOfInstallments;
        this.currentInstallment = currentInstallment;
    }

}
