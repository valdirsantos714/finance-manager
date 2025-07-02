package com.valdirsantos714.backend.application.core.usecase;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequest;
import com.valdirsantos714.backend.application.core.ports.in.CreateExpenseInputPort;
import com.valdirsantos714.backend.application.core.ports.out.ExpenseOutputPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateExpenseUseCase implements CreateExpenseInputPort {

    private final ExpenseOutputPort expenseOutputPort;

    @Override
    public void create(ExpenseRequest expense) {
        expenseOutputPort.save(expense);
    }
}
