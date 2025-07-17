package com.valdirsantos714.backend.application.service;

import com.valdirsantos714.backend.adapters.in.dto.FinancialSummaryResponseDTO;
import com.valdirsantos714.backend.adapters.out.repository.ExpenseJpaRepository;
import com.valdirsantos714.backend.adapters.out.repository.IncomeJpaRepository;
import com.valdirsantos714.backend.application.usecase.FinancialSummaryUseCases;
import org.springframework.stereotype.Service;

@Service
public class FinancialSummaryServiceImpl implements FinancialSummaryUseCases {
    private final ExpenseJpaRepository expenseRepository;
    private final IncomeJpaRepository incomeRepository;
    private final UserServiceImpl userService;

    public FinancialSummaryServiceImpl(ExpenseJpaRepository expenseRepository,
                                     IncomeJpaRepository incomeRepository,
                                     UserServiceImpl userService) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
        this.userService = userService;
    }

    @Override
    public FinancialSummaryResponseDTO getFinancialSummary(String email) {
        var user = userService.findByEmail(email);
        var totalExpenses = expenseRepository.sumExpensesByUserEmail(email);
        var totalIncomes = incomeRepository.sumIncomesByUserEmail(email);

        return new FinancialSummaryResponseDTO(
                user.getName(),
                totalIncomes,
                totalExpenses
        );
    }
}
