package com.valdirsantos714.backend.dtos;

import java.time.LocalDate;

public record ExpenseResponseDTO(
        Long id,
        String name,
        String description,
        Double amount,
        LocalDate date,
        Long userId,
        String userName,
        String category
) {}
