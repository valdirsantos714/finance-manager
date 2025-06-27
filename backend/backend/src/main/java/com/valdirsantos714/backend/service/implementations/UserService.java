package com.valdirsantos714.backend.service.implementations;

import com.valdirsantos714.backend.domain.User;
import com.valdirsantos714.backend.repository.UserRepository;
import com.valdirsantos714.backend.service.interfaces.CrudMethodsI;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService implements CrudMethodsI<User, Long> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public User read(Long aLong) {
        return null;
    }

    @Override
    public User update(Long entity) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public List<User> findAll() {
        return List.of();
    }
}
