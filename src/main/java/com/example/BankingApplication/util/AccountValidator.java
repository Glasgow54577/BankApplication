package com.example.BankingApplication.util;

import com.example.BankingApplication.DTO.AccountDTO;
import com.example.BankingApplication.services.classService.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {
    private final AccountServiceImpl accountService;

    @Autowired
    public AccountValidator(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;
        if((accountService.findOne(accountDTO.getId()).getCash()-accountDTO.getCash()) < 0){
            errors.rejectValue("cash", "", "Cash should not be less than 0" );
        }
    }
}
