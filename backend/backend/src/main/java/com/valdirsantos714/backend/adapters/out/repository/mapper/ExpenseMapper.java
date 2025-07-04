package com.valdirsantos714.backend.adapters.out.repository.mapper;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequestDTO;
import com.valdirsantos714.backend.adapters.in.dto.ExpenseResponseDTO;
import com.valdirsantos714.backend.adapters.out.repository.entity.ExpenseEntity;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.core.domain.User;

import java.util.List;

public class ExpenseMapper {

    public static ExpenseResponseDTO toResponse(Expense expense) {
        return new ExpenseResponseDTO(
            expense.getId(),
            expense.getName(),
            expense.getDescription(),
            expense.getAmount(),
            expense.getDate(),
            expense.getCategory(),
            expense.getUser() != null ? expense.getUser().getId() : null
        );
    }

    public static List<ExpenseResponseDTO> toExpenseResponseDTOList(List<Expense> expenses) {
        return expenses.stream()
                .map(ExpenseMapper::toResponse)
                .toList();
    }

    public static Expense toExpense(ExpenseEntity expenseEntity) {
        Expense expense = new Expense();
        expense.setId(expenseEntity.getId());
        expense.setName(expenseEntity.getName());
        expense.setDescription(expenseEntity.getDescription());
        expense.setAmount(expenseEntity.getAmount());
        expense.setDate(expenseEntity.getDate());
        expense.setCategory(expenseEntity.getCategory());

        if (expenseEntity.getUser() != null) {
            User user = new User();
            user.setId(expenseEntity.getUser().getId());
            expense.setUser(user);
        }

        return expense;
    }

    public static ExpenseEntity toExpenseEntity(Expense expense) {
        return new ExpenseEntity(
            expense.getId(),
            expense.getName(),
            expense.getDescription(),
            expense.getAmount(),
            expense.getDate(),
            expense.getUser() != null ? UserMapper.toUserEntity(expense.getUser()) : null,
            expense.getCategory()
        );
    }

    public static Expense toExpense(ExpenseRequestDTO request) {
        Expense expense = new Expense();
        expense.setName(request.name());
        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setDate(request.date());
        expense.setCategory(request.category());

        User user = new User();
        user.setId(request.userId());
        return expense;
    }

    public static Expense toExpenseWithUser(ExpenseRequestDTO request, User user) {
        Expense expense = toExpense(request);
        expense.setUser(user);
        return expense;
    }

    public static List<ExpenseEntity> toExpenseEntityList(List<Expense> expenses) {
        return expenses.stream()
            .map(ExpenseMapper::toExpenseEntity)
            .toList();
    }

    public static List<Expense> toExpenseList(List<ExpenseEntity> expenses) {
        return expenses.stream()
            .map(ExpenseMapper::toExpense)
            .toList();
    }
}
