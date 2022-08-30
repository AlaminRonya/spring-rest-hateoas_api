package com.example.accountapiwithhateoas.services;

import com.example.accountapiwithhateoas.converter.AccountConverter;
import com.example.accountapiwithhateoas.dto.requests.AccountDto;
import com.example.accountapiwithhateoas.dto.responses.ResponseAccountDto;
import com.example.accountapiwithhateoas.entities.Account;
import com.example.accountapiwithhateoas.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountConverter accountConverter;

    private final LocalDate localDate = LocalDate.now();


    public List<ResponseAccountDto> getAllAccount(){
        final List<Account> all = accountRepository.findAll();
        if (all != null){
            final List<Account> accounts = List.copyOf(all);
            return accountConverter.getAllResponseAccount(accounts);
        }
        return null;
    }

    public ResponseAccountDto getAccountByAccountNumber(String accountNumber){
        return getAccount(accountNumber);
    }

    public ResponseAccountDto addAccount(AccountDto dto){
        final Account account = accountConverter.dtoToEntity(dto);
        final Account account1 = insertAccount(account);
        if (account1 != null){
            return accountConverter.getResponseAccountDto(account1);
        }
        return null;
    }

    public ResponseAccountDto updatedAccount(AccountDto dto){
        final Account account = accountConverter.dtoToEntity(dto);
        return updatedAccount(account);
    }

    private Account insertAccount(Account account){
        final boolean present = accountRepository.findAccountByAccountNumber(account.getAccountNumber()).isPresent();
        if (!present){
            return accountRepository.save(account);
        }
        return null;
    }

    private ResponseAccountDto updatedAccount(Account account){

        accountRepository.updatedBalance(account.getAccountNumber(), account.getBalance(), Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        return getAccount(account.getAccountNumber());
    }


    public Account get(Long id) {
        return accountRepository.findById(id).get();
    }

    public ResponseAccountDto deposit(String accountNumber, Double amount){
        accountRepository.deposit(amount, accountNumber);
        return getAccount(accountNumber);
    }

    public ResponseAccountDto withdraw(String accountNumber, Double amount){
        accountRepository.withdraw(amount, accountNumber);
        return getAccount(accountNumber);
    }

    private ResponseAccountDto getAccount(String accountNumber){
        final Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElse(null);
        if (account != null){
            return accountConverter.getResponseAccountDto(account);
        }
        return null;
    }
}
