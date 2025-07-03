package com.valdirsantos714.backend.adapters.in.controller;

import com.valdirsantos714.backend.adapters.in.dto.UserRequestDTO;
import com.valdirsantos714.backend.adapters.out.repository.mapper.UserMapper;
import com.valdirsantos714.backend.application.core.domain.User;
import com.valdirsantos714.backend.application.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User user = service.save(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponseDTO(user));
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(UserMapper.toResponseDTOList(service.findAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid UserRequestDTO userRequestDTO) {
        User user = service.update(id, userRequestDTO);
        return ResponseEntity.ok().body(UserMapper.toResponseDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
