package com.valdirsantos714.backend.application.ports.service;

import com.valdirsantos714.backend.application.core.domain.User;
import com.valdirsantos714.backend.adapters.in.dto.UserRequestDTO;

import java.util.List;

public interface UserService {
    User save(UserRequestDTO request);
    List<User> findAll();
    User update(Long id, UserRequestDTO request);
    void delete(Long id);

}
