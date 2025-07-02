package com.valdirsantos714.backend.application.core.ports.in;

import com.valdirsantos714.backend.application.core.domain.Income;

public interface CreateIncomeInputPort {
    void create(Income income);
}
