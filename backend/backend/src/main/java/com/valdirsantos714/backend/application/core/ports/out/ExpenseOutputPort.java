package com.valdirsantos714.backend.application.core.ports.out;

import com.valdirsantos714.backend.application.core.domain.Expense;

public interface ExpenseOutputPort {
    void save(Expense expense);
}
