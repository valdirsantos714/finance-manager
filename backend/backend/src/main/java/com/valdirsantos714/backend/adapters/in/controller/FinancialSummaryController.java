package com.valdirsantos714.backend.adapters.in.controller;

import com.valdirsantos714.backend.adapters.in.dto.FinancialSummaryResponseDTO;
import com.valdirsantos714.backend.application.service.FinancialSummaryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financial-summary")
public class FinancialSummaryController {

    private final FinancialSummaryServiceImpl service;

    public FinancialSummaryController(FinancialSummaryServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{email}")
    public ResponseEntity<FinancialSummaryResponseDTO> getFinancialSummary(@PathVariable String email) {
        var summary = service.getFinancialSummary(email);
        return ResponseEntity.ok(summary);
    }
}
