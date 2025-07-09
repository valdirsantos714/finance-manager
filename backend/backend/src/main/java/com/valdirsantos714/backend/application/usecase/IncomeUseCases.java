package com.valdirsantos714.backend.application.usecase;

import com.valdirsantos714.backend.adapters.in.dto.IncomeRequestDTO;
import com.valdirsantos714.backend.application.core.domain.Income;

import java.util.List;

public interface IncomeUseCases {
    Income save(IncomeRequestDTO Income);
    List<Income> findAll();
    Income findById(Long id);
    Income update(Long id, IncomeRequestDTO Income);
    void delete(Long id);
    List<Income> findByUserEmail(String email);
}
