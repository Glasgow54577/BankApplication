package com.example.BankingApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cash")
    @Min(value = 0, message = "Cash should not be less than 0")
    private int cash;

    @ManyToOne
    @JoinColumn(name = "person", referencedColumnName = "login") // связь через ид
    @NotNull
    private Person person;
}
