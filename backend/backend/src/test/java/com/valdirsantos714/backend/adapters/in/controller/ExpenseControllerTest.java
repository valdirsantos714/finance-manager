package com.valdirsantos714.backend.adapters.in.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdirsantos714.backend.adapters.in.dto.ExpenseRequestDTO;
import com.valdirsantos714.backend.adapters.in.dto.ExpenseResponseDTO;
import com.valdirsantos714.backend.adapters.out.repository.UserRepositoryAdapter;
import com.valdirsantos714.backend.adapters.out.repository.mapper.ExpenseMapper;
import com.valdirsantos714.backend.application.core.domain.Expense;
import com.valdirsantos714.backend.application.core.domain.enums.ExpenseCategory;
import com.valdirsantos714.backend.application.service.ExpenseServiceImpl;
import com.valdirsantos714.backend.infrastructure.security.SecurityFilter;
import com.valdirsantos714.backend.infrastructure.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;

    @MockBean
    private SecurityFilter securityFilter;

    @Test
    @DisplayName("should create a new expense and return 201 status")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateExpense() throws Exception {
        // Given
        String email = "admin@email.com";
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO(
                "Test Expense",
                "Description",
                100.0,
                LocalDate.now(),
                ExpenseCategory.FOOD
        );

        Expense expenseDomain = ExpenseMapper.toExpense(requestDTO);
        expenseDomain.setId(1L); // simular id gerado

        given(service.save(eq(email), any(ExpenseRequestDTO.class)))
                .willReturn(expenseDomain);

        // When + Then
        mockMvc.perform(post("/api/expenses/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(requestDTO.name()));
    }

    @Test
    @DisplayName("should return all expenses")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldGetAllExpenses() throws Exception {
        // Given
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setName("Expense 1");

        given(service.findAll()).willReturn(List.of(expense));

        // When + Then
        mockMvc.perform(get("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Expense 1"));
    }

    @Test
    @DisplayName("should get expenses by user email")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldGetExpensesByUserEmail() throws Exception {
        // Given
        String email = "admin@email.com";
        Expense expense = new Expense();
        expense.setId(2L);
        expense.setName("Expense 2");

        given(service.findByUserEmail(email)).willReturn(List.of(expense));

        // When + Then
        mockMvc.perform(get("/api/expenses/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Expense 2"));
    }

    @Test
    @DisplayName("should update an existing expense")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldUpdateExpense() throws Exception {
        // Given
        String email = "admin@email.com";
        Long id = 1L;
        ExpenseRequestDTO requestDTO = new ExpenseRequestDTO(
                "Updated Expense",
                "Updated Desc",
                150.0,
                LocalDate.now(),
                ExpenseCategory.LEISURE
        );

        Expense updatedExpense = ExpenseMapper.toExpense(requestDTO);
        updatedExpense.setId(id);

        given(service.update(eq(id), eq(email), any(ExpenseRequestDTO.class)))
                .willReturn(updatedExpense);

        // When + Then
        mockMvc.perform(put("/api/expenses/{email}/{id}", email, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Expense"));
    }

    @Test
    @DisplayName("should delete an expense and return 204 status")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteExpense() throws Exception {
        // Given
        String email = "admin@email.com";
        Long id = 1L;

        // No need to mock void method, but you can verify call later if wanted

        // When + Then
        mockMvc.perform(delete("/api/expenses/{email}/{id}", email, id))
                .andExpect(status().isNoContent());

        Mockito.verify(service).delete(email, id);
    }
}
