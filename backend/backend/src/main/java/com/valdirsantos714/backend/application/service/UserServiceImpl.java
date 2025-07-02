package com.valdirsantos714.backend.application.service;


import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequest;
import com.valdirsantos714.backend.adapters.in.dto.UserRequestDTO;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.core.domain.User;
import com.valdirsantos714.backend.application.core.ports.in.CreateExpenseInputPort;
import com.valdirsantos714.backend.application.core.ports.in.CreateUserInputPort;
import com.valdirsantos714.backend.application.ports.service.ExpenseService;
import com.valdirsantos714.backend.application.ports.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CreateUserInputPort createUserInputPort;

    @Override
    public User save(UserRequestDTO request) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User update(Long id, UserRequestDTO request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}