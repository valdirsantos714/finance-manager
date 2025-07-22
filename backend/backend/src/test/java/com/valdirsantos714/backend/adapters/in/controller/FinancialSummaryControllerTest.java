package com.valdirsantos714.backend.adapters.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdirsantos714.backend.adapters.out.dto.FinancialSummaryResponseDTO;
import com.valdirsantos714.backend.adapters.out.repository.UserRepositoryAdapter;
import com.valdirsantos714.backend.application.service.FinancialSummaryServiceImpl;
import com.valdirsantos714.backend.infrastructure.security.SecurityFilter;
import com.valdirsantos714.backend.infrastructure.security.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FinancialSummaryController.class)
class FinancialSummaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancialSummaryServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepositoryAdapter userRepositoryAdapter;

    @MockBean
    private SecurityFilter securityFilter;

    @Test
    @DisplayName("should get financial summary by user email")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldGetFinancialSummaryByUserEmail() throws Exception {
        // Given
        String email = "admin@email.com";
        FinancialSummaryResponseDTO summary = new FinancialSummaryResponseDTO(
                "admin",
                1000.0,
                500.0,
                500.0
        );

        given(service.getFinancialSummary(eq(email))).willReturn(summary);

        // When + Then
        mockMvc.perform(get("/api/financial-summary/{email}", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(summary.name()))
                .andExpect(jsonPath("$.totalIncome").value(summary.totalIncome()))
                .andExpect(jsonPath("$.totalExpenses").value(summary.totalExpenses()))
                .andExpect(jsonPath("$.balance").value(summary.balance()));
    }
}
