package com.hibernate.practice.dto.account;

import com.hibernate.practice.annotation.ValidCandidate;
import com.hibernate.practice.entity.enums.AccountType;
import jakarta.validation.constraints.NotNull;

@ValidCandidate
public record AccountCreateDto(
        @NotNull Long clientId,
        @NotNull AccountType accountType
) {
}
