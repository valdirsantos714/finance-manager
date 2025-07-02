package com.valdirsantos714.backend.core.entity;


import com.valdirsantos714.backend.core.domain.CommomAtributes;
import com.valdirsantos714.backend.core.domain.ExpenseCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_EXPENSE")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends CommomAtributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

}
