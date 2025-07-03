package com.valdirsantos714.backend.adapters.out.repository;

import com.valdirsantos714.backend.adapters.out.repository.entity.ExpenseEntity;
import com.valdirsantos714.backend.adapters.out.repository.mapper.ExpenseMapper;
import com.valdirsantos714.backend.adapters.out.repository.mapper.UserMapper;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.ports.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpenseRepositoryAdapter implements ExpenseRepository {

    private final ExpenseJpaRepository expenseJpaRepository;
    private final String ENTITY_NOT_FOUND_MESSAGE = "Expense not found with id: ";

    public ExpenseRepositoryAdapter(ExpenseJpaRepository expenseJpaRepository) {
        this.expenseJpaRepository = expenseJpaRepository;
    }

    @Override
    public Expense save(Expense expense) {
        ExpenseEntity entity = new ExpenseEntity(expense);
        expenseJpaRepository.save(entity);
        return ExpenseMapper.toExpense(entity);
    }

    @Override
    public List<Expense> findAll() {
        List<ExpenseEntity> entities = expenseJpaRepository.findAll();
        return entities.stream().map(ExpenseMapper::toExpense).toList();
    }

    @Override
    public Expense findById(Long id) {
        return expenseJpaRepository.findById(id)
                .map(ExpenseMapper::toExpense)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public Expense update(Long id, Expense expense) {
        ExpenseEntity entity = expenseJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE + id));

        entity = updateExpenseEntity(entity, expense);
        expenseJpaRepository.save(entity);
        return ExpenseMapper.toExpense(entity);
    }

    @Override
    public void delete(Long id) {
        expenseJpaRepository.deleteById(id);
    }

    private ExpenseEntity updateExpenseEntity(ExpenseEntity entity, Expense expense) {
        entity.setName(expense.getName());
        entity.setDescription(expense.getDescription());
        entity.setAmount(expense.getAmount());
        entity.setDate(expense.getDate());
        entity.setCategory(expense.getCategory());
        entity.setUser(UserMapper.toUserEntity(expense.getUser()));

        return entity;
    }
}
