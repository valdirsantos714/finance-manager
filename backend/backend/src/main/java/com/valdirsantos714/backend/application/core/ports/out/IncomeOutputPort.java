package com.valdirsantos714.backend.application.core.ports.out;

import com.valdirsantos714.backend.application.core.domain.Income;

public interface IncomeOutputPort {
    void save(Income income);
}
