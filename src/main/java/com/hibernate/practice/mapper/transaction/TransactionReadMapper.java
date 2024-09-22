package com.hibernate.practice.mapper.transaction;

import com.hibernate.practice.dto.transaction.TransactionReadDto;
import com.hibernate.practice.entity.Transaction;
import com.hibernate.practice.mapper.Mapper;

public class TransactionReadMapper implements Mapper<Transaction, TransactionReadDto> {
    @Override
    public TransactionReadDto mapFrom(Transaction from) {
        return new TransactionReadDto(
                from.getId(),
                from.getAccount().getId(),
                from.getAmount(),
                from.getType().name(),
                from.getDateTime()
        );
    }
}
