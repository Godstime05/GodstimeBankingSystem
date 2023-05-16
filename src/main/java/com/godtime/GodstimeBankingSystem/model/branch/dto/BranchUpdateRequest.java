package com.godtime.GodstimeBankingSystem.model.branch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BranchUpdateRequest {

    @NotBlank(message = "Branch Id cannot be empty")
    private final Long branchId;

    @NotBlank(message = "Branch name cannot be empty")
    private final String branchName;

    @NotBlank(message = "Branch address cannot be empty")
    private final String branchAddress;


}
