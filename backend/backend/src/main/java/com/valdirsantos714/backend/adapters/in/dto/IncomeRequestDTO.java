package com.valdirsantos714.backend.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record IncomeRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        Double amount,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "Category is required")
        String category
) {}
