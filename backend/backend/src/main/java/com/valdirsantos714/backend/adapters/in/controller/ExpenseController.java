package com.valdirsantos714.backend.adapters.in.controller;

import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequestDTO;
import com.valdirsantos714.backend.adapters.out.repository.mapper.ExpenseMapper;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.service.ExpenseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseServiceImpl service;

    public ExpenseController(ExpenseServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity createExpense(@RequestBody @Valid ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = service.save(expenseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ExpenseMapper.toResponse(expense));
    }

    @GetMapping
    public ResponseEntity getAllExpenses() {
        List<Expense> expenses = service.findAll();
        return ResponseEntity.ok().body(ExpenseMapper.toExpenseResponseDTOList(expenses));
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity getExpensesByUserEmail(@PathVariable(name = "userEmail") String userEmail) {
        List<Expense> expenses = service.findByUserEmail(userEmail);
        return ResponseEntity.ok().body(
                expenses.stream()
                        .map(ExpenseMapper::toResponse)
                        .toList()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity updateExpense(@PathVariable Long id, @RequestBody @Valid ExpenseRequestDTO expenseRequestDTO) {
        Expense updatedExpense = service.update(id, expenseRequestDTO);
        return ResponseEntity.ok().body(ExpenseMapper.toResponse(updatedExpense));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteExpense(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
