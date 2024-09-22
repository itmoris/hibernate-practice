package com.hibernate.practice.dto.card;

import com.hibernate.practice.annotation.ValidCandidate;
import jakarta.validation.constraints.NotNull;

@ValidCandidate
public record CardCreateDto(
        @NotNull Long accountId
) {
}
