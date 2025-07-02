package com.valdirsantos714.backend.application.core.ports.out;

import com.valdirsantos714.backend.application.core.domain.User;

public interface UserOutputPort {
    void save(User user);
}
