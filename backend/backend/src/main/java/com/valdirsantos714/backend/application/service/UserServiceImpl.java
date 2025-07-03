package com.valdirsantos714.backend.application.service;

import com.valdirsantos714.backend.adapters.in.dto.UserRequestDTO;
import com.valdirsantos714.backend.adapters.out.repository.UserRepositoryAdapter;
import com.valdirsantos714.backend.adapters.out.repository.mapper.UserMapper;
import com.valdirsantos714.backend.application.core.domain.User;
import com.valdirsantos714.backend.application.usecase.UserUseCases;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserUseCases {

    private final UserRepositoryAdapter userRepositoryAdapter;

    public UserServiceImpl(UserRepositoryAdapter userRepositoryAdapter) {
        this.userRepositoryAdapter = userRepositoryAdapter;
    }

    @Override
    public User save(UserRequestDTO user) {
        return userRepositoryAdapter.save(UserMapper.toUser(user));
    }

    @Override
    public List<User> findAll() {
        return userRepositoryAdapter.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepositoryAdapter.findById(id);
    }

    @Override
    public User update(Long id, UserRequestDTO User) {
        return userRepositoryAdapter.update(id, UserMapper.toUser(User));
    }

    @Override
    public void delete(Long id) {
        userRepositoryAdapter.delete(id);
    }
}