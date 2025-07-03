package com.valdirsantos714.backend.application.ports.repository;

import com.valdirsantos714.backend.application.core.domain.User;

import java.util.List;

public interface UserRepository {
    User save(User User);
    List<User> findAll();
    User findById(Long id);
    User update(Long id, User User);
    void delete(Long id);
}
