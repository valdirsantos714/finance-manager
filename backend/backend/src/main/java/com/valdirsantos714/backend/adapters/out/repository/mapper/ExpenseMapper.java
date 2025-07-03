package com.valdirsantos714.backend.adapters.out.repository.mapper;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequestDTO;
import com.valdirsantos714.backend.adapters.in.dto.ExpenseResponseDTO;
import com.valdirsantos714.backend.adapters.out.repository.entity.ExpenseEntity;
import com.valdirsantos714.backend.application.core.domain.Expense;

import java.util.List;

public class ExpenseMapper {

    public static Expense toExpense(ExpenseEntity expenseEntity) {
        Expense expense = new Expense();
        expense.setId(expenseEntity.getId());
        expense.setName(expenseEntity.getName());
        expense.setDescription(expenseEntity.getDescription());
        expense.setAmount(expenseEntity.getAmount());
        expense.setDate(expenseEntity.getDate());
        expense.setUser(UserMapper.toUser(expenseEntity.getUser()));
        expense.setCategory(expenseEntity.getCategory());
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

    public static ExpenseEntity toExpenseEntity(ExpenseRequestDTO request) {
        ExpenseEntity entity = new ExpenseEntity();
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setAmount(request.amount());
        entity.setDate(request.date());
        entity.setCategory(request.category());
        return entity;
    }

    public static ExpenseResponseDTO toResponse(ExpenseEntity entity) {
        return new ExpenseResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getAmount(),
            entity.getDate(),
            entity.getCategory(),
            entity.getUser() != null ? entity.getUser().getId() : null
        );
    }

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

    public static Expense toExpense(ExpenseRequestDTO request) {
        Expense expense = new Expense();
        expense.setName(request.name());
        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setDate(request.date());
        expense.setCategory(request.category());
        return expense;
    }

    public static List<ExpenseResponseDTO> toExpenseResponseDTOList(List<Expense> expenses) {
        return expenses.stream()
            .map(ExpenseMapper::toResponse)
            .toList();
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
