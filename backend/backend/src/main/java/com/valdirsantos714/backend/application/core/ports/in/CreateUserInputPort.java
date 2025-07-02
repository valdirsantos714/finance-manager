package com.valdirsantos714.backend.application.core.ports.in;

import com.valdirsantos714.backend.application.core.domain.User;

public interface CreateUserInputPort {
    void create(User user);
}