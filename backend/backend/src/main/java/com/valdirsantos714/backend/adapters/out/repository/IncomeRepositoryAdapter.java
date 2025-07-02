package com.valdirsantos714.backend.adapters.out.repository;


import com.valdirsantos714.backend.adapters.out.repository.entity.IncomeEntity;
import com.valdirsantos714.backend.application.core.domain.Income;
import com.valdirsantos714.backend.application.core.ports.out.IncomeOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncomeRepositoryAdapter implements IncomeOutputPort {

    private final IncomeJpaRepository incomeJpaRepository;

    @Override
    public void save(Income income) {
        IncomeEntity entity = new IncomeEntity(
                income.getId(),
                income.getName(),
                income.getDescription(),
                income.getAmount(),
                income.getDate(),
                income.getUser(),
                income.getCategory()
        );
        incomeJpaRepository.save(entity);
    }
}