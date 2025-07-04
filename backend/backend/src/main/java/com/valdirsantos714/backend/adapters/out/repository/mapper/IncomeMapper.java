package com.valdirsantos714.backend.adapters.out.repository.mapper;

import com.valdirsantos714.backend.adapters.in.dto.IncomeRequestDTO;
import com.valdirsantos714.backend.adapters.in.dto.IncomeResponseDTO;
import com.valdirsantos714.backend.adapters.out.repository.entity.IncomeEntity;
import com.valdirsantos714.backend.application.core.domain.Income;
import com.valdirsantos714.backend.application.core.domain.User;
import com.valdirsantos714.backend.application.core.domain.enums.IncomeCategory;

import java.util.List;

public class IncomeMapper {

    public static Income toIncome(IncomeEntity incomeEntity) {
        Income income = new Income();
        income.setId(incomeEntity.getId());
        income.setName(incomeEntity.getName());
        income.setDescription(incomeEntity.getDescription());
        income.setAmount(incomeEntity.getAmount());
        income.setDate(incomeEntity.getDate());
        income.setCategory(incomeEntity.getCategory());

        if (incomeEntity.getUser() != null) {
            User user = new User();
            user.setId(incomeEntity.getUser().getId());
            income.setUser(user);
        }

        return income;
    }

    public static IncomeEntity toIncomeEntity(Income income) {
        return new IncomeEntity(
            income.getId(),
            income.getName(),
            income.getDescription(),
            income.getAmount(),
            income.getDate(),
            income.getUser() != null ? UserMapper.toUserEntity(income.getUser()) : null,
            income.getCategory()
        );
    }

    public static Income toIncome(IncomeRequestDTO request) {
        Income income = new Income();
        income.setName(request.name());
        income.setDescription(request.description());
        income.setAmount(request.amount());
        income.setDate(request.date());
        income.setCategory(IncomeCategory.valueOf(request.category().toUpperCase()));

        // Criar um User temporário apenas com o ID
        User user = new User();
        user.setId(request.userId());
        income.setUser(user);

        return income;
    }

    public static IncomeRequestDTO toRequestDTO(Income income) {
        return new IncomeRequestDTO(
            income.getName(),
            income.getDescription(),
            income.getAmount(),
            income.getDate(),
            income.getUser().getId(),
            income.getCategory().name()
        );
    }

    public static List<IncomeResponseDTO> toIncomeResponseDTOList(List<Income> incomeList) {
        return incomeList.stream().map(IncomeMapper::toResponseDTO)
                .toList();
    }

    public static IncomeResponseDTO toResponseDTO(Income income) {
        return new IncomeResponseDTO(
            income.getId(),
            income.getName(),
            income.getDescription(),
            income.getAmount(),
            income.getDate(),
            income.getUser() != null ? income.getUser().getId() : null,
            income.getCategory()
        );
    }

    public static List<IncomeEntity> toIncomeEntityList(List<Income> incomes) {
        return incomes.stream()
                .map(IncomeMapper::toIncomeEntity)
                .toList();
    }

    public static List<Income> toIncomeList(List<IncomeEntity> incomes) {
        return incomes.stream()
                .map(IncomeMapper::toIncome)
                .toList();
    }
}
