package com.valdirsantos714.backend.application.usecase;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequestDTO;
import com.valdirsantos714.backend.application.core.domain.Expense;

import java.util.List;

public interface ExpenseUseCases {
    Expense save(ExpenseRequestDTO expense);
    List<Expense> findAll();
    Expense findById(Long id);
    Expense update(Long id, ExpenseRequestDTO expense);
    void delete(Long id);
    List<Expense> findByUserId(Long userId);
}
