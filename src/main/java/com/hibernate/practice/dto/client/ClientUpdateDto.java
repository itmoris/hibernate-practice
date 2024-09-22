package com.hibernate.practice.dto.client;

import com.hibernate.practice.annotation.ValidCandidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@ValidCandidate
public record ClientUpdateDto(
        @NotNull Long id,
        @NotBlank @Size(min = 5, max = 50) String username,
        @NotBlank String password
) {
}
