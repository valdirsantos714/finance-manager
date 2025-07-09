package com.valdirsantos714.backend.application.ports.repository;

import com.valdirsantos714.backend.application.core.domain.Income;

import java.util.List;

public interface IncomeRepository {
    Income save(Income Income);
    List<Income> findAll();
    Income findById(Long id);
    Income update(Long id, Income Income);
    void delete(Long id);
    List<Income> findByUserEmail(String email);
}
