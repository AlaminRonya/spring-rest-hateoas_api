package com.example.accountapiwithhateoas.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String username;
    private String accountNumber;
    private Double balance;
    private Date createdAt;
}
