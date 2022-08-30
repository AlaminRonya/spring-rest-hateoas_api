package com.example.accountapiwithhateoas.controller;

import com.example.accountapiwithhateoas.dto.requests.AccountDto;
import com.example.accountapiwithhateoas.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class HomeController {
    @Autowired
    private AccountService accountService;
    private final LocalDate localDate = LocalDate.now();

    @GetMapping("/")
    public String getHomePage(){
        AccountDto dto = new AccountDto();
        dto.setUsername("abc@gmail.com");
        dto.setAccountNumber("123456789");
        dto.setBalance(20000.0);
        dto.setCreatedAt(Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        accountService.addAccount(dto);
        return null;
    }
}
