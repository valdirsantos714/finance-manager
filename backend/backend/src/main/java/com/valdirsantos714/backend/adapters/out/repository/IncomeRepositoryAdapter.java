package com.valdirsantos714.backend.adapters.out.repository;

import com.valdirsantos714.backend.adapters.out.repository.entity.IncomeEntity;
import com.valdirsantos714.backend.adapters.out.repository.mapper.IncomeMapper;
import com.valdirsantos714.backend.adapters.out.repository.mapper.UserMapper;
import com.valdirsantos714.backend.application.core.domain.Income;
import com.valdirsantos714.backend.application.ports.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncomeRepositoryAdapter implements IncomeRepository {

    private final IncomeJpaRepository incomeJpaRepository;
    private final String ENTITY_NOT_FOUND_MESSAGE = "Income not found with id: ";

    public IncomeRepositoryAdapter(IncomeJpaRepository incomeJpaRepository) {
        this.incomeJpaRepository = incomeJpaRepository;
    }

    @Override
    public Income save(Income income) {
        IncomeEntity entity = new IncomeEntity(income);
        incomeJpaRepository.save(entity);
        return IncomeMapper.toIncome(entity);
    }

    @Override
    public List<Income> findAll() {
        List<IncomeEntity> entities = incomeJpaRepository.findAll();
        return entities.stream().map(IncomeMapper::toIncome).toList();
    }

    @Override
    public Income findById(Long id) {
        return incomeJpaRepository.findById(id)
                .map(IncomeMapper::toIncome)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE + id));
    }

    @Override
    public Income update(Long id, Income income) {
        IncomeEntity entity = incomeJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE + id));

        entity = updateIncomeEntity(entity, income);

        incomeJpaRepository.save(entity);
        return IncomeMapper.toIncome(entity);
    }

    @Override
    public void delete(Long id) {
        incomeJpaRepository.deleteById(id);
    }

    private IncomeEntity updateIncomeEntity(IncomeEntity entity, Income income) {
        entity.setName(income.getName());
        entity.setDescription(income.getDescription());
        entity.setAmount(income.getAmount());
        entity.setDate(income.getDate());
        entity.setUser(UserMapper.toUserEntity(income.getUser()));
        return entity;
    }
}