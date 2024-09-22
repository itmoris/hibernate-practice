package com.hibernate.practice.mapper.transaction;

import com.hibernate.practice.dto.transaction.TransactionWithdrawDto;
import com.hibernate.practice.entity.Account;
import com.hibernate.practice.entity.Transaction;
import com.hibernate.practice.entity.enums.TransactionType;
import com.hibernate.practice.mapper.Mapper;
import com.hibernate.practice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TransactionWithdrawMapper implements Mapper<TransactionWithdrawDto, Transaction> {
    private final AccountRepository accountRepository;

    @Override
    public Transaction mapFrom(TransactionWithdrawDto from) {
        Account account = accountRepository.findById(from.accountId()).orElseThrow(IllegalArgumentException::new);

        Transaction transaction = Transaction.builder()
                .account(account)
                .amount(from.amount())
                .dateTime(LocalDateTime.now())
                .type(TransactionType.WITHDRAW)
                .build();

        transaction.addAccount(account);
        return transaction;
    }
}
