package com.hibernate.practice.dto.account;

import java.math.BigDecimal;

public record AccountReadDto(
        Long id,
        Long clientId,
        String number,
        BigDecimal balance,
        String accountType
) {
}
