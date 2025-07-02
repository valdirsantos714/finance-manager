package com.valdirsantos714.backend.application.core.ports.in;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequest;
import com.valdirsantos714.backend.application.core.domain.Expense;

public interface CreateExpenseInputPort {
    void create(ExpenseRequest expense);
}