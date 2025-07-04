package com.valdirsantos714.backend.application.service;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequestDTO;
import com.valdirsantos714.backend.adapters.out.repository.ExpenseRepositoryAdapter;
import com.valdirsantos714.backend.adapters.out.repository.mapper.ExpenseMapper;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.usecase.ExpenseUseCases;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseUseCases {

    private final ExpenseRepositoryAdapter expenseRepositoryAdapter;
    private final UserServiceImpl userService;

    public ExpenseServiceImpl(ExpenseRepositoryAdapter expenseRepositoryAdapter, UserServiceImpl userService) {
        this.expenseRepositoryAdapter = expenseRepositoryAdapter;
        this.userService = userService;
    }

    @Override
    public Expense save(ExpenseRequestDTO expense) {
        var user = userService.findById(expense.userId());
        return expenseRepositoryAdapter.save(ExpenseMapper.toExpenseWithUser(expense, user));
    }

    @Override
    public List<Expense> findAll() {
        return expenseRepositoryAdapter.findAll();
    }

    @Override
    public Expense findById(Long id) {
        return expenseRepositoryAdapter.findById(id);
    }

    @Override
    public Expense update(Long id, ExpenseRequestDTO expense) {
        return expenseRepositoryAdapter.update(id, ExpenseMapper.toExpense(expense));
    }

    @Override
    public void delete(Long id) {
        expenseRepositoryAdapter.delete(id);
    }
}