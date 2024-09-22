package com.hibernate.practice.mapper.account;

import com.hibernate.practice.dto.account.AccountCreateDto;
import com.hibernate.practice.entity.Account;
import com.hibernate.practice.entity.Client;
import com.hibernate.practice.mapper.Mapper;
import com.hibernate.practice.repository.ClientRepository;
import com.hibernate.practice.util.GeneratorUtils;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class AccountCreateMapper implements Mapper<AccountCreateDto, Account> {
    private final ClientRepository clientRepository;

    @Override
    public Account mapFrom(AccountCreateDto from) {
        Client client = clientRepository.findById(from.clientId()).orElseThrow(IllegalArgumentException::new);

        Account account = Account.builder()
                .number(GeneratorUtils.generateNumber(6))
                .balance(BigDecimal.ZERO)
                .accountType(from.accountType())
                .build();

        account.addClient(client);
        return account;
    }
}
