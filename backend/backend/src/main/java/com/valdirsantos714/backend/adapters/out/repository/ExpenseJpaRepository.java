package com.valdirsantos714.backend.adapters.out.repository;

import com.valdirsantos714.backend.adapters.out.repository.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseJpaRepository extends JpaRepository<ExpenseEntity, Long> {
    @Query("SELECT e FROM ExpenseEntity e WHERE e.user.email = :email")
    List<ExpenseEntity> findByUserEmail(@Param("email") String email);
}
