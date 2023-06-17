package com.example.BankingApplication.controllers;

import com.example.BankingApplication.models.Person;
import com.example.BankingApplication.services.classService.AccountServiceImpl;
import com.example.BankingApplication.services.classService.PersonServiceImpl;
import com.example.BankingApplication.services.interfaceServices.AccountService;
import com.example.BankingApplication.services.interfaceServices.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personServiceImpl;
    private final AccountService accountServiceImpl;

    @Autowired
    public PersonController(PersonService personServiceImpl, AccountService accountServiceImpl) {
        this.personServiceImpl = personServiceImpl;
        this.accountServiceImpl = accountServiceImpl;
    }

    @PostMapping("/inform")
    public void inf(@RequestBody String notification){
        System.out.println("Отправляем оповещение: " + notification);
    }

    @PostMapping("/add")
    public void personAdd(@RequestBody @Valid Person person,
                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ///
        }
        personServiceImpl.save(person);
    }



}
