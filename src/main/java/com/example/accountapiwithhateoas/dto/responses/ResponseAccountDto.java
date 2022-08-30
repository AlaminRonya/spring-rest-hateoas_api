package com.example.accountapiwithhateoas.dto.responses;

import com.example.accountapiwithhateoas.entities.Account;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
public class ResponseAccountDto {
    private Long id;
    private String username;
    private String accountNumber;
    private Double balance;
    private Date createdAt;
    private Date modifiedAt;
    private Date deletedAt;
}
