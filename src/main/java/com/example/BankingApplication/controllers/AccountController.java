package com.example.BankingApplication.controllers;

import com.example.BankingApplication.DTO.AccountDTO;
import com.example.BankingApplication.DTO.TransferDTO;
import com.example.BankingApplication.models.Account;
import com.example.BankingApplication.services.classService.PersonServiceImpl;
import com.example.BankingApplication.services.interfaceServices.AccountService;
import com.example.BankingApplication.services.interfaceServices.PersonService;
import com.example.BankingApplication.util.AccountErrorResponse;
import com.example.BankingApplication.util.AccountNotCreatedException;
import com.example.BankingApplication.util.AccountValidator;
import com.example.BankingApplication.util.TransferValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final PersonService personServiceImpl;
    private final AccountService accountServiceImpl;
    private final AccountValidator accountValidator;
    private final TransferValidator transferValidator;

    @Autowired
    public AccountController(PersonService personServiceImpl, AccountService accountServiceImpl, AccountValidator accountValidator, TransferValidator transferValidator) {
        this.personServiceImpl = personServiceImpl;
        this.accountServiceImpl = accountServiceImpl;
        this.accountValidator = accountValidator;
        this.transferValidator = transferValidator;
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<HttpStatus> accountAdd(@RequestBody @Valid Account account,
                          BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            ExceptionAccount(bindingResult);
        }
        account.setPerson(personServiceImpl.findByPersonLogin(account.getPerson().getLogin()));
        accountServiceImpl.save(account);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/deposit")
    @ResponseBody
    public void accountDeposit(@RequestBody @Valid AccountDTO accountDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ExceptionAccount(bindingResult);
        }
        System.out.println("START");
        accountServiceImpl.deposit(accountDTO.getId(), accountDTO.getCash());
    }
//    @PostMapping("/deposit")
//    @ResponseBody
//    public ResponseEntity<HttpStatus> accountDeposit(@RequestBody @Valid AccountDTO accountDTO, BindingResult bindingResult){
//        if(bindingResult.hasErrors()){
//            ExceptionAccount(bindingResult);
//        }
//        System.out.println("START");
//        accountServiceImpl.deposit(accountDTO.getId(), accountDTO.getCash());
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
    @PostMapping("/withDraw")
    @ResponseBody
    public ResponseEntity<HttpStatus> accountWithDraw(@RequestBody @Valid AccountDTO accountDTO, BindingResult bindingResult){

        accountValidator.validate(accountDTO, bindingResult);
        bindingResult.getPropertyEditorRegistry();
        if(bindingResult.hasErrors()){
            ExceptionAccount(bindingResult);
        }

        accountServiceImpl.withDraw(accountDTO.getId(), accountDTO.getCash());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity<HttpStatus> accountTransfer(@RequestBody @Valid TransferDTO transferDTO, BindingResult bindingResult){
        transferValidator.validate(transferDTO, bindingResult);
//        System.out.println("transferDTO = " + transferDTO.getIdA() + transferDTO.getCash());
        if(bindingResult.hasErrors()){
            ExceptionAccount(bindingResult);
        }

        accountServiceImpl.transfer(transferDTO.getIdA(), transferDTO.getIdB(), transferDTO.getCash());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/info")
    @ResponseBody
    public ResponseEntity<HttpStatus> accountInfo(@RequestBody AccountDTO accountDTO){
//        System.out.println(accountServiceImpl.findOne(accountDTO.getId()).getCash());
        List<Account> accounts = accountServiceImpl.findAll();
        int sum = 0;
        for (int i = 0; i < accounts.size(); i++) {
            sum = sum + accounts.get(i).getCash();
        }
        System.out.println("Total sum = " + sum);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private void ExceptionAccount(BindingResult bindingResult){
        StringBuilder errorMassage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMassage.append(error.getField()) // поле на котором совершена ошибка
                    .append(" - ").append(error.getDefaultMessage()) // выдаст ошибку на этом поле
                    .append(";");
        }
        throw new AccountNotCreatedException(errorMassage.toString());
    }

    @ExceptionHandler // Обрабатывает исключение при неудачном добавлении счета
    private ResponseEntity<AccountErrorResponse> handleException(AccountNotCreatedException e){
        AccountErrorResponse response = new AccountErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
