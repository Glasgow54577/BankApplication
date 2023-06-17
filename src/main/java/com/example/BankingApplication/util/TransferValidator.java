package com.example.BankingApplication.util;

import com.example.BankingApplication.DTO.TransferDTO;
import com.example.BankingApplication.services.classService.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TransferValidator implements Validator {
    private final AccountServiceImpl accountService;

    @Autowired
    public TransferValidator(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TransferDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransferDTO transferDTO = (TransferDTO) target;
        if((accountService.findOne(transferDTO.getIdA()).getCash()-transferDTO.getCash()) < 0){
            errors.rejectValue("cash", "", "Cash should not be less than 0" );
        }
    }
}
