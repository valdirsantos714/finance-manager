package com.valdirsantos714.backend.adapters.out.repository;

import com.valdirsantos714.backend.adapters.out.repository.entity.ExpenseEntity;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.core.ports.out.ExpenseOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpenseRepositoryAdapter implements ExpenseOutputPort {

    private final ExpenseJpaRepository expenseJpaRepository;

    @Override
    public void save(Expense expense) {
        ExpenseEntity entity = new ExpenseEntity(
                expense.getId(),
                expense.getName(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                expense.getUser(),
                expense.getCategory()
        );
        expenseJpaRepository.save(entity);
    }
}
