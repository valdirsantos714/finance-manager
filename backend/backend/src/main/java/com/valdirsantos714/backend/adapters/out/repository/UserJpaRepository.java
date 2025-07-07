package com.valdirsantos714.backend.adapters.out.repository;

import com.valdirsantos714.backend.adapters.out.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserDetails> findByEmail(String username);
}
