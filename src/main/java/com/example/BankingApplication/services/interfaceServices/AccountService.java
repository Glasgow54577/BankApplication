package com.example.BankingApplication.services.interfaceServices;

import com.example.BankingApplication.models.Account;

import java.util.List;

public interface AccountService {
    public List<Account> findAll();
    public Account findOne(int id);
    public void save(Account account);
    public void update (int id, Account updateAccount);
    public void deposit (int id, int cash);
    public void withDraw (int id, int cash);
    public void transfer (int IDA, int IDB, int cash);
}
