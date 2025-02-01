package com.medilabo.riskreportservice.controller;

import com.medilabo.riskreportservice.DTO.RiskReportDTO;
import com.medilabo.riskreportservice.enums.RiskLevel;
import com.medilabo.riskreportservice.service.RiskReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/risk-report")
public class RiskReportController {

    private final RiskReportService riskReportService;

    public RiskReportController(RiskReportService riskReportService) {
        this.riskReportService = riskReportService;
    }

    @GetMapping("/{patId}")
    public ResponseEntity<RiskReportDTO> getRiskReport(@PathVariable String patId) {
        RiskReportDTO riskReport = riskReportService.assessRisk(patId);
        return ResponseEntity.status(HttpStatus.OK).body(riskReport);
    }
}

