package com.godtime.GodstimeBankingSystem.exceptions;

public class EmployeeNotFoundInBranchException extends RuntimeException{
    public EmployeeNotFoundInBranchException(String message){
        super(message);
    }
}
