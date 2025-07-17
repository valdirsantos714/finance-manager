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

    @PostMapping("/{email}")
    public ResponseEntity createExpense(@PathVariable String email, @RequestBody @Valid ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = service.save(email, expenseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ExpenseMapper.toResponse(expense));
    }

    @GetMapping
    public ResponseEntity getAllExpenses() {
        List<Expense> expenses = service.findAll();
        return ResponseEntity.ok().body(ExpenseMapper.toExpenseResponseDTOList(expenses));
    }

    @GetMapping("/{email}")
    public ResponseEntity getExpensesByUserEmail(@PathVariable String email) {
        List<Expense> expenses = service.findByUserEmail(email);
        return ResponseEntity.ok().body(
                expenses.stream()
                        .map(ExpenseMapper::toResponse)
                        .toList()
        );
    }

    @PutMapping("/{email}/{id}")
    public ResponseEntity updateExpense(
            @PathVariable String email,
            @PathVariable Long id,
            @RequestBody @Valid ExpenseRequestDTO expenseRequestDTO) {
        Expense updatedExpense = service.update(id, email, expenseRequestDTO);
        return ResponseEntity.ok().body(ExpenseMapper.toResponse(updatedExpense));
    }

    @DeleteMapping("/{email}/{id}")
    public ResponseEntity deleteExpense(@PathVariable String email, @PathVariable Long id) {
        service.delete(email, id);
        return ResponseEntity.noContent().build();
    }

}
