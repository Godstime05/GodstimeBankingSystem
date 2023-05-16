package com.godtime.GodstimeBankingSystem.model.branch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class BranchCreationRequest {

    private final Long branchId;
    private final String branchName;
    private final String branchAddress;

}
