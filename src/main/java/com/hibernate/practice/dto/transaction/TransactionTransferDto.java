package com.hibernate.practice.dto.transaction;

import com.hibernate.practice.annotation.ValidCandidate;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@ValidCandidate
public record TransactionTransferDto(
        @NotNull Long fromAccountId,
        @NotNull Long toAccountId,
        BigDecimal amount
) {
    public TransactionWithdrawDto toWithdrawTransaction() {
        return new TransactionWithdrawDto(fromAccountId, amount);
    }

    public TransactionDepositDto toDepositTransaction() {
        return new TransactionDepositDto(toAccountId, amount);
    }
}
