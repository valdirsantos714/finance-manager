package com.valdirsantos714.backend.adapters.in.dto;

import com.valdirsantos714.backend.application.core.domain.enums.ExpenseCategory;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ExpenseRequestDTO(
    @NotBlank(message = "Name is required")
    String name,

    String description,

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    Double amount,

    @NotNull(message = "Date is required")
    LocalDate date,

    @NotNull(message = "Category is required")
    ExpenseCategory category,

    @NotNull(message = "User ID is required")
    Long userId
) {}
