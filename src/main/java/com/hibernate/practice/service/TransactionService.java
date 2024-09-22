package com.hibernate.practice.service;

import com.hibernate.practice.dto.transaction.TransactionDepositDto;
import com.hibernate.practice.dto.transaction.TransactionReadDto;
import com.hibernate.practice.dto.transaction.TransactionTransferDto;
import com.hibernate.practice.dto.transaction.TransactionWithdrawDto;
import com.hibernate.practice.entity.Account;
import com.hibernate.practice.entity.Transaction;
import com.hibernate.practice.entity.enums.TransactionType;
import com.hibernate.practice.exception.TransactionException;
import com.hibernate.practice.mapper.transaction.TransactionDepositMapper;
import com.hibernate.practice.mapper.transaction.TransactionReadMapper;
import com.hibernate.practice.mapper.transaction.TransactionWithdrawMapper;
import com.hibernate.practice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionReadMapper transactionReadMapper;
    private final TransactionDepositMapper transactionDepositMapper;
    private final TransactionWithdrawMapper transactionWithdrawMapper;

    @Transactional
    public Optional<TransactionReadDto> findById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionReadMapper::mapFrom);
    }

    @Transactional
    public List<TransactionReadDto> findAll() {
        return transactionRepository.findAll().stream()
                .map(transactionReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TransactionReadDto> findAllByAccountId(Long accountId) {
        return transactionRepository.findAllByAccountId(accountId).stream()
                .map(transactionReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public void withdraw(TransactionWithdrawDto dto) throws TransactionException {
        Transaction transaction = transactionWithdrawMapper.mapFrom(dto);
        Account account = transaction.getAccount();
        BigDecimal newBalance = account.getBalance().subtract(transaction.getAmount());

        if (newBalance.compareTo(BigDecimal.ZERO) <= 0 && !account.getAccountType().isAvailableOverdraft()) {
            throw new TransactionException("Insufficient funds in account", transaction);
        }

        account.setBalance(newBalance);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void deposit(TransactionDepositDto dto) {
        Transaction transaction = transactionDepositMapper.mapFrom(dto);
        Account account = transaction.getAccount();
        BigDecimal newBalance = account.getBalance().add(transaction.getAmount());
        account.setBalance(newBalance);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void transfer(TransactionTransferDto dto) throws TransactionException {
        TransactionDepositDto depositTransaction = dto.toDepositTransaction();
        TransactionWithdrawDto withdrawTransaction = dto.toWithdrawTransaction();
        withdraw(withdrawTransaction);
        deposit(depositTransaction);
    }
}
