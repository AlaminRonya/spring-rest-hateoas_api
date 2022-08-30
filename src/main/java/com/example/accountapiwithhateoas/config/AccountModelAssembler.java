package com.example.accountapiwithhateoas.config;

import com.example.accountapiwithhateoas.controller.rest.AccountApi;
import com.example.accountapiwithhateoas.dto.requests.AccountDto;
import com.example.accountapiwithhateoas.dto.responses.ResponseAccountDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class AccountModelAssembler implements RepresentationModelAssembler<ResponseAccountDto, EntityModel<ResponseAccountDto>> {
    @Override
    public EntityModel<ResponseAccountDto> toModel(ResponseAccountDto entity) {
        EntityModel<ResponseAccountDto> ResponseAccountDtoModel = EntityModel.of(entity);
        ResponseAccountDtoModel.add(linkTo(methodOn(AccountApi.class).getUserByAccountNumber(entity.getAccountNumber())).withSelfRel());
        ResponseAccountDtoModel.add(linkTo(methodOn(AccountApi.class).getAllResponseAccount()).withRel(IanaLinkRelations.COLLECTION));

        ResponseAccountDtoModel.add(linkTo(methodOn(AccountApi.class).deposit(entity.getAccountNumber(), null)).withRel("deposits"));
        ResponseAccountDtoModel.add(linkTo(methodOn(AccountApi.class).withdraw(entity.getAccountNumber(), null)).withRel("withdrawals"));

        return ResponseAccountDtoModel;
    }

}
