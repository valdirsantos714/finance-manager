package com.valdirsantos714.backend.adapters.out.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.valdirsantos714.backend.adapters.out.repository.mapper.ExpenseMapper;
import com.valdirsantos714.backend.adapters.out.repository.mapper.IncomeMapper;
import com.valdirsantos714.backend.application.core.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USER")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<IncomeEntity> incomes = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ExpenseEntity> expenses = new ArrayList<>();

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.incomes = IncomeMapper.toIncomeEntityList(user.getIncomes());
        this.expenses = ExpenseMapper.toExpenseEntityList(user.getExpenses());
    }
}