package com.valdirsantos714.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TB_USER")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Income> incomes;

    @OneToMany(mappedBy = "user")
    private List<Expense> expenses;
}
