package com.valdirsantos714.backend.adapters.in.dto;

public record FinancialSummaryResponseDTO(
    String userName,
    Double totalIncome,
    Double totalExpenses,
    Double balance
) {
    public FinancialSummaryResponseDTO(String userName, Double totalIncome, Double totalExpenses) {
        this(userName, totalIncome, totalExpenses, totalIncome - totalExpenses);
    }
}
