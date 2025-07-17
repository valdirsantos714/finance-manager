package com.valdirsantos714.backend.adapters.in.controller;

import com.valdirsantos714.backend.adapters.in.dto.IncomeRequestDTO;
import com.valdirsantos714.backend.adapters.out.repository.mapper.IncomeMapper;
import com.valdirsantos714.backend.application.core.domain.Income;
import com.valdirsantos714.backend.application.service.IncomeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin("*")
public class IncomeController {

    private final IncomeServiceImpl service;

    public IncomeController(IncomeServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/{email}")
    public ResponseEntity save(@PathVariable String email, @RequestBody @Valid IncomeRequestDTO dto) {
        Income income = service.save(email, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(IncomeMapper.toResponseDTO(income));
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(IncomeMapper.toIncomeResponseDTOList(service.findAll()));
    }

    @GetMapping("/{email}")
    public ResponseEntity getByUserEmail(@PathVariable String email) {
        List<Income> incomes = service.findByUserEmail(email);
        return ResponseEntity.ok().body(
                incomes.stream()
                        .map(IncomeMapper::toResponseDTO)
                        .toList()
        );
    }

    @PutMapping("/{email}/{id}")
    public ResponseEntity update(
            @PathVariable String email,
            @PathVariable Long id,
            @RequestBody @Valid IncomeRequestDTO dto) {
        Income income = service.update(id, email, dto);
        return ResponseEntity.ok().body(IncomeMapper.toResponseDTO(income));
    }

    @DeleteMapping("/{email}/{id}")
    public ResponseEntity delete(@PathVariable String email, @PathVariable Long id) {
        service.delete(email, id);
        return ResponseEntity.noContent().build();
    }
}
