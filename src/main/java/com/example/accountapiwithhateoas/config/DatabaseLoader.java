package com.example.accountapiwithhateoas.config;

import com.example.accountapiwithhateoas.converter.AccountConverter;
import com.example.accountapiwithhateoas.dto.requests.AccountDto;
import com.example.accountapiwithhateoas.entities.Account;
import com.example.accountapiwithhateoas.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class DatabaseLoader {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountConverter accountConverter;
//    private final LocalDate localDate = LocalDate.now();

    @Bean
    public CommandLineRunner initDatabase(){
        return args -> {
            final LocalDate localDate = LocalDate.now();
            List<AccountDto> accountsDto = List.of(
                    new AccountDto("user0","1234567890",1000.0, Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())),
                    new AccountDto("user1","1234567891",1000.0, Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())),
                    new AccountDto("user2","1234567892",1000.0, Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())),
                    new AccountDto("user3","1234567893",1000.0, Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
            );
            List<Account> accounts = new ArrayList<>();
            for (AccountDto dto: accountsDto){
                accounts.add(accountConverter.dtoToEntity(dto));
            }
            accountRepository.saveAll(accounts);

            System.out.println("Sample database initialized.");
        };
    }


}
