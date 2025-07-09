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

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid IncomeRequestDTO dto) {
        Income income = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(IncomeMapper.toResponseDTO(income));
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok().body(IncomeMapper.toIncomeResponseDTOList(service.findAll()));
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity getByUserEmail(@PathVariable(name = "userEmail") String userEmail) {
        List<Income> incomes = service.findByUserEmail(userEmail);
        return ResponseEntity.ok().body(
                incomes.stream()
                        .map(IncomeMapper::toResponseDTO)
                        .toList()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid IncomeRequestDTO dto) {
        Income income = service.update(id, dto);
        return ResponseEntity.ok().body(IncomeMapper.toResponseDTO(income));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
