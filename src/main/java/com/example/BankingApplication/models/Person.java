package com.example.BankingApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    @NotEmpty(message = "login should not be empty")
    @Size(min = 3, max = 30, message = "Login should be between 3 and 30 characters")
    private String login;

    @Column(name = "password")
    @NotEmpty(message = "password should not be empty")
    @Size(min = 3, max = 30, message = "password should be between 3 and 30 characters")
    private String password;

//    @OneToMany
//    @JoinColumn(name = "account") // указывается колонка через которую осущ связь в таблице персон
//    private List<Account> account;

}
