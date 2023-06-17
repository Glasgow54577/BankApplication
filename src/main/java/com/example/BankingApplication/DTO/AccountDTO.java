package com.example.BankingApplication.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AccountDTO {

    private int id;

    @Min(value = 0, message = "Cash should not be less than 0")
    private int cash;

}
