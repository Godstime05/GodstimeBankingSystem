package com.godtime.GodstimeBankingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanDTO {

    private String customerNationalId;
    private String facilityName;
    private Long productId;
    private double amount;
    private int repayments;
    private String grantorNationalId;
}
