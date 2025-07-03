package com.valdirsantos714.backend.adapters.out.repository;

import com.valdirsantos714.backend.adapters.out.repository.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseJpaRepository extends JpaRepository<ExpenseEntity, Long> {
}
