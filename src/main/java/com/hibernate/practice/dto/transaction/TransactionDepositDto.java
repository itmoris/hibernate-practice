package com.hibernate.practice.dto.transaction;

import com.hibernate.practice.annotation.ValidCandidate;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@ValidCandidate
public record TransactionDepositDto(
        @NotNull Long accountId,
        BigDecimal amount
) {
}
