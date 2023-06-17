package com.example.BankingApplication.DTO;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class TransferDTO {
    private int idA;
    private int idB;

    @Min(value = 0, message = "Cash should not be less than 0")
    private int cash;
}
