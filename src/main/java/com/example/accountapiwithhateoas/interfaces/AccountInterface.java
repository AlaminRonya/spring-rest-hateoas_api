package com.example.accountapiwithhateoas.interfaces;

import com.example.accountapiwithhateoas.dto.requests.AccountDto;
import com.example.accountapiwithhateoas.entities.Account;

public interface AccountInterface {
    Account dtoToEntity(AccountDto dto);
}
