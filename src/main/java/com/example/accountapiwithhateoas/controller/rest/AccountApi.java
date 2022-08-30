package com.example.accountapiwithhateoas.controller.rest;

import com.example.accountapiwithhateoas.config.AccountModelAssembler;
import com.example.accountapiwithhateoas.dto.requests.AccountDto;
import com.example.accountapiwithhateoas.dto.requests.AmountDto;
import com.example.accountapiwithhateoas.dto.responses.ResponseAccountDto;
import com.example.accountapiwithhateoas.entities.Account;
import com.example.accountapiwithhateoas.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountApi {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountModelAssembler modelAssembler;

    @GetMapping("/all")
    public CollectionModel<EntityModel<ResponseAccountDto>> getAllResponseAccount(){

        final List<ResponseAccountDto> allAccount = accountService.getAllAccount();
        List<EntityModel<ResponseAccountDto>> listEntityModel = allAccount.stream().map(modelAssembler::toModel).toList();

        CollectionModel<EntityModel<ResponseAccountDto>> collectionModel = CollectionModel.of(listEntityModel);
        collectionModel.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withSelfRel());

        return collectionModel;

//        if (allAccount.isEmpty()){
//            return ResponseEntity.noContent().build();
//        }
//
//        for (ResponseAccountDto dto: allAccount){
//            dto.add(linkTo(methodOn(AccountApi.class).getUserByAccountNumber(dto.getAccountNumber())).withSelfRel());
//            dto.add(linkTo(methodOn(AccountApi.class).deposit(dto.getAccountNumber(), null)).withRel("deposits"));
//            dto.add(linkTo(methodOn(AccountApi.class).withdraw(dto.getAccountNumber(), null)).withRel("withdrawals"));
//            dto.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));
//        }
//        CollectionModel<ResponseAccountDto> model = CollectionModel.of(allAccount);
//        model.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));
//        return new ResponseEntity<>(model, HttpStatus.OK);


    }
    @GetMapping("/{id}")
    public ResponseEntity<Account> getOne(@PathVariable("id") Long id){
        try {
            final Account account = accountService.get(id);
//            account.add(linkTo(methodOn(AccountApi.class).getOne(id)).withSelfRel());
            if (account == null){
                return ResponseEntity.noContent().build();
            }

            return new ResponseEntity<>(account, HttpStatus.OK);

        }catch (NoSuchElementException exception){
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/numbers/{accountNumber}")
    public HttpEntity<EntityModel<ResponseAccountDto>> getUserByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        try {
            final ResponseAccountDto responseAccountDto = accountService.getAccountByAccountNumber(accountNumber);
            if (responseAccountDto == null){
                return ResponseEntity.noContent().build();
            }
            EntityModel<ResponseAccountDto> model = modelAssembler.toModel(responseAccountDto);
//            account.add(linkTo(methodOn(AccountApi.class).getUserByAccountNumber(accountNumber)).withSelfRel());
//            account.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));

            return new ResponseEntity<>(model, HttpStatus.OK);

        }catch (NoSuchElementException exception){
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/add")
    public HttpEntity<EntityModel<ResponseAccountDto>> addAccount(@RequestBody AccountDto dto){
        try {
            final ResponseAccountDto savedAccount = accountService.addAccount(dto);

            if (savedAccount == null){
                return ResponseEntity.noContent().build();
            }
            EntityModel<ResponseAccountDto> model = modelAssembler.toModel(savedAccount);

            return ResponseEntity.created(linkTo(methodOn(AccountApi.class)
                    .getUserByAccountNumber(savedAccount.getAccountNumber())).toUri()).body(model);

        }catch (NoSuchElementException exception){
            return ResponseEntity.noContent().build();
        }




//        savedAccount.add(linkTo(methodOn(AccountApi.class).getUserByAccountNumber(savedAccount.getAccountNumber())).withSelfRel());
//        savedAccount.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));
//
//        return ResponseEntity.created(linkTo(methodOn(AccountApi.class).getOne(savedAccount.getId())).toUri()).body(savedAccount);


    }

    @PutMapping("/update")
    public HttpEntity<EntityModel<ResponseAccountDto>> replace(@RequestBody AccountDto dto) {
        try {

            final ResponseAccountDto updatedAccount = accountService.updatedAccount(dto);
            if (updatedAccount == null){
                return ResponseEntity.noContent().build();
            }
            return new ResponseEntity<>(modelAssembler.toModel(updatedAccount), HttpStatus.OK);

        }catch (NoSuchElementException exception){
            return ResponseEntity.noContent().build();
        }




//        updatedAccount.add(linkTo(methodOn(AccountApi.class).getUserByAccountNumber(updatedAccount.getAccountNumber())).withSelfRel());
//        updatedAccount.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));
//
//        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);

    }


    @PatchMapping("/{accountNumber}/deposits")
    public HttpEntity<EntityModel<ResponseAccountDto>> deposit(@PathVariable("accountNumber") String accountNumber, @RequestBody AmountDto dto){
        try {

            final ResponseAccountDto deposit = accountService.deposit(accountNumber, dto.getAmount());
            if (deposit == null){
                return ResponseEntity.noContent().build();
            }
            return new ResponseEntity<>(modelAssembler.toModel(deposit), HttpStatus.OK);
        }catch (NoSuchElementException exception){
            return ResponseEntity.noContent().build();
        }



//        deposit.add(linkTo(methodOn(AccountApi.class).getUserByAccountNumber(accountNumber)).withSelfRel());
//        deposit.add(linkTo(methodOn(AccountApi.class).deposit(accountNumber, null)).withRel("deposits"));
//        deposit.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));
//        return new ResponseEntity<>(deposit, HttpStatus.OK);


    }

    @PatchMapping("/{accountNumber}/withdrawal")
    public HttpEntity<EntityModel<ResponseAccountDto>> withdraw(@PathVariable("accountNumber") String accountNumber, @RequestBody AmountDto dto){

        try {

            final ResponseAccountDto withdraw = accountService.withdraw(accountNumber, dto.getAmount());
            if (withdraw == null){
                return ResponseEntity.noContent().build();
            }
            return new ResponseEntity<>(modelAssembler.toModel(withdraw), HttpStatus.OK);

        }catch (NoSuchElementException exception){
            return ResponseEntity.noContent().build();
        }

//        withdraw.add(linkTo(methodOn(AccountApi.class).getUserByAccountNumber(accountNumber)).withSelfRel());
//        withdraw.add(linkTo(methodOn(AccountApi.class).withdraw(accountNumber, null)).withRel("withdrawals"));
//        withdraw.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));
//
//        return new ResponseEntity<>(withdraw, HttpStatus.OK);


    }


}
