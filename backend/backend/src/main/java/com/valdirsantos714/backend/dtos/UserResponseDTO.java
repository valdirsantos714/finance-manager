package com.valdirsantos714.backend.dtos;

public record UserResponseDTO(
        Long id,
        String fullName,
        String email
) {}
