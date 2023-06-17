package com.example.BankingApplication.services.classService;

import com.example.BankingApplication.models.Account;
import com.example.BankingApplication.repositories.AccountRepository;
import com.example.BankingApplication.services.interfaceServices.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(readOnly = true )
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    @Override
    public Account findOne(int id){
        Optional<Account> foundAccount = accountRepository.findById(id);
        return foundAccount.orElse(null);
    }

    @Override
    @Transactional
    public void save(Account account){
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void update (int id, Account updateAccount){
        updateAccount.setId(id);
        accountRepository.save(updateAccount);
    }
    AtomicInteger deposit = new AtomicInteger(0);
    AtomicInteger withDraw = new AtomicInteger(0);

//    AtomicInteger cashD = new AtomicInteger(0);
    @Override
    @Transactional
    public void deposit (int id, int cashDelta){
        findOne(id).setCash(findOne(id).getCash()+cashDelta);
        update(id, findOne(id));
    }
    @Override
    @Transactional
    public void withDraw (int id, int cashDelta){
        findOne(id).setCash(findOne(id).getCash()-cashDelta);
        update(id, findOne(id));
    }
    @Override
    @Transactional
    public void transfer (int IDA, int IDB, int cash){

        withDraw(IDA, cash);
        deposit(IDB, cash);

    }


}
