package com.example.accountapiwithhateoas.converter;

import com.example.accountapiwithhateoas.dto.requests.AccountDto;
import com.example.accountapiwithhateoas.dto.responses.ResponseAccountDto;
import com.example.accountapiwithhateoas.entities.Account;
import com.example.accountapiwithhateoas.interfaces.AccountInterface;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountConverter implements AccountInterface {
    @Override
    public Account dtoToEntity(AccountDto dto) {
        Account account = new Account();
        account.setUsername(dto.getUsername());
        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setCreatedAt(dto.getCreatedAt());
        return account;
    }

    public ResponseAccountDto getResponseAccountDto(Account account){
        ResponseAccountDto dto = new ResponseAccountDto();
        dto.setId(account.getId());
        dto.setUsername(account.getUsername());
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setModifiedAt(account.getModifiedAt());
        dto.setDeletedAt(account.getDeletedAt());
        return dto;
    }

    public List<ResponseAccountDto> getAllResponseAccount(List<Account> accounts){
        return accounts.stream().map(this::getResponseAccountDto).toList();
    }
}
