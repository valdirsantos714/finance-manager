package com.valdirsantos714.backend.adapters.out.repository;

import com.valdirsantos714.backend.adapters.out.repository.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeJpaRepository extends JpaRepository<IncomeEntity, Long> {
    @Query("SELECT i FROM IncomeEntity i WHERE i.user.email = :email")
    List<IncomeEntity> findByUserEmail(@Param("email") String email);
}
