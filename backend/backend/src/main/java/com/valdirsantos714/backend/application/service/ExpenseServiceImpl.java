package com.valdirsantos714.backend.application.service;


import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequest;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.core.ports.in.CreateExpenseInputPort;
import com.valdirsantos714.backend.application.ports.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final CreateExpenseInputPort createExpenseInputPort;
    private final UserServiceImpl userService;

    @Override
    public Expense save(ExpenseRequest request) {

        createExpenseInputPort.create(request);

        return null;
    }

    @Override
    public List<Expense> findAll() {
        return List.of();
    }

    @Override
    public Expense update(Long id, ExpenseRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}