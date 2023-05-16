package com.godtime.GodstimeBankingSystem.dto;

import com.godtime.GodstimeBankingSystem.enums.ProductType;
import com.godtime.GodstimeBankingSystem.model.loan.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String firstName;
    private String LastName;
    private String phoneNumber;
    private String nationalId;
    private String facilityName;
    private Long branchId;
    private Long productId;
    private Boolean isCommissionPaid;
    private List<Loan> loanOfGrantedCustomer;
    private ProductType productType;
    private double commissionAmount;
}
