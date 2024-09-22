package com.hibernate.practice.service;

import com.hibernate.practice.dto.account.AccountCreateDto;
import com.hibernate.practice.dto.account.AccountReadDto;
import com.hibernate.practice.entity.Account;
import com.hibernate.practice.mapper.account.AccountCreateMapper;
import com.hibernate.practice.mapper.account.AccountReadMapper;
import com.hibernate.practice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountCreateMapper accountCreateMapper;
    private final AccountReadMapper accountReadMapper;

    @Transactional
    public Optional<AccountReadDto> findById(Long id) {
        return accountRepository.findById(id)
                .map(accountReadMapper::mapFrom);
    }

    @Transactional
    public List<AccountReadDto> findAll() {
        return accountRepository.findAll().stream()
                .map(accountReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AccountReadDto> findAllByClientId(Long clientId) {
        return accountRepository.findAllByClientId(clientId).stream()
                .map(accountReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long create(AccountCreateDto dto) {
        Account account = accountCreateMapper.mapFrom(dto);
        return accountRepository.save(account);
    }

    @Transactional
    public boolean updateBalance(Long id, BigDecimal newBalance) {
        Optional<Account> byId = accountRepository.findById(id);
        byId.ifPresent(account -> account.setBalance(newBalance));
        return byId.isPresent();
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Account> maybeAccount = accountRepository.findById(id);
        maybeAccount.ifPresent(accountRepository::delete);
        return maybeAccount.isPresent();
    }
}
