package com.hibernate.practice.dto.transaction;

import com.hibernate.practice.annotation.ValidCandidate;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@ValidCandidate
public record TransactionWithdrawDto(
        @NotNull Long accountId,
        BigDecimal amount
) {
}
