package com.valdirsantos714.backend.adapters.in.controller;

import com.valdirsantos714.backend.application.service.FinancialSummaryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial-summary")
@CrossOrigin("*")
public class FinancialSummaryController {

    private final FinancialSummaryServiceImpl service;

    public FinancialSummaryController(FinancialSummaryServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{email}")
    public ResponseEntity getFinancialSummary(@PathVariable String email) {
        System.out.println(service.getFinancialSummary(email));
        return ResponseEntity.ok(service.getFinancialSummary(email));
    }
}
