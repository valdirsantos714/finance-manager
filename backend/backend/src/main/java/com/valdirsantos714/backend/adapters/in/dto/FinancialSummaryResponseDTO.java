package com.valdirsantos714.backend.adapters.in.dto;

public record FinancialSummaryResponseDTO(
    String name,
    Double totalIncome,
    Double totalExpenses,
    Double balance
) {
    public FinancialSummaryResponseDTO(String name, Double totalIncome, Double totalExpenses) {
        this(name, totalIncome, totalExpenses, totalIncome - totalExpenses);
    }
}
