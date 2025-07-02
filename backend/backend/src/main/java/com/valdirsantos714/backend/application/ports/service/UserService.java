package com.valdirsantos714.backend.application.ports.service;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequest;
import com.valdirsantos714.backend.application.core.domain.Expense;

import java.util.List;

public interface ExpenseService {
    Expense save(ExpenseRequest request);
    List<Expense> findAll();
    Expense update(Long id, ExpenseRequest request);
    void delete(Long id);

}
