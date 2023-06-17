package com.example.BankingApplication.util;

public class AccountNotCreatedException extends RuntimeException{
    public AccountNotCreatedException(String msg){
        super(msg);
    }
}
