package com.hibernate.practice.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionReadDto(
        Long id,
        Long accountId,
        BigDecimal amount,
        String transactionType,
        LocalDateTime dateTime
) {
}
