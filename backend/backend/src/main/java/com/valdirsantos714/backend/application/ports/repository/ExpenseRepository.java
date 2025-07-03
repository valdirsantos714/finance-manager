package com.valdirsantos714.backend.application.ports.repository;

import com.valdirsantos714.backend.application.core.domain.Expense;

import java.util.List;

public interface ExpenseRepository {
    Expense save(Expense expense);
    List<Expense> findAll();
    Expense findById(Long id);
    Expense update(Long id, Expense expense);
    void delete(Long id);
}
