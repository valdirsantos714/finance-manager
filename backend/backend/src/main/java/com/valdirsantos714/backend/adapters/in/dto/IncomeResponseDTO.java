package com.valdirsantos714.backend.adapters.in.dto;

import java.time.LocalDate;

public record IncomeResponseDTO(
        Long id,
        String name,
        String description,
        Double amount,
        LocalDate date,
        Long userId,
        String userName,
        String category
) {}
