package com.valdirsantos714.backend.application.service;

import com.valdirsantos714.backend.adapters.in.dto.IncomeRequestDTO;
import com.valdirsantos714.backend.adapters.out.repository.IncomeRepositoryAdapter;
import com.valdirsantos714.backend.adapters.out.repository.mapper.IncomeMapper;
import com.valdirsantos714.backend.application.core.domain.Income;
import com.valdirsantos714.backend.application.core.domain.User;
import com.valdirsantos714.backend.application.usecase.IncomeUseCases;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeUseCases {

    private final IncomeRepositoryAdapter incomeRepositoryAdapter;
    private final UserServiceImpl userService;

    public IncomeServiceImpl(IncomeRepositoryAdapter incomeRepositoryAdapter, UserServiceImpl userService) {
        this.incomeRepositoryAdapter = incomeRepositoryAdapter;
        this.userService = userService;
    }

    @Override
    public Income save(IncomeRequestDTO income) {
        userService.findById(income.userId());
        return incomeRepositoryAdapter.save(IncomeMapper.toIncome(income));
    }

    @Override
    public List<Income> findAll() {
        return incomeRepositoryAdapter.findAll();
    }

    @Override
    public Income findById(Long id) {
        return incomeRepositoryAdapter.findById(id);
    }

    @Override
    public Income update(Long id, IncomeRequestDTO Income) {
        return incomeRepositoryAdapter.update(id, IncomeMapper.toIncome(Income));
    }

    @Override
    public void delete(Long id) {
        incomeRepositoryAdapter.delete(id);
    }

    @Override
    public List<Income> findByUserId(Long userId) {
        User user = userService.findById(userId);
        return user.getIncomes();
    }
}
