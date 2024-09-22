package com.hibernate.practice.dto.card;

import java.time.LocalDate;


public record CardReadDto(
        Long id,
        Long accountId,
        String number,
        String cvv,
        LocalDate expiryDate
) {
}
