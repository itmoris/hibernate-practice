package com.hibernate.practice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountType {
    CREDIT(true),
    DEBIT(false);

    private final boolean availableOverdraft;
}
