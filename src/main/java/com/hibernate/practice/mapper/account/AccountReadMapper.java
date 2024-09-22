package com.hibernate.practice.mapper.account;

import com.hibernate.practice.dto.account.AccountReadDto;
import com.hibernate.practice.entity.Account;
import com.hibernate.practice.mapper.Mapper;

public class AccountReadMapper implements Mapper<Account, AccountReadDto> {
    @Override
    public AccountReadDto mapFrom(Account from) {
        return new AccountReadDto(
                from.getId(),
                from.getClient().getId(),
                from.getNumber(),
                from.getBalance(),
                from.getAccountType().name()
        );
    }
}
