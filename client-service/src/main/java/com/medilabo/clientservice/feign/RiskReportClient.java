package com.medilabo.clientservice.feign;


import com.medilabo.clientservice.DTO.RiskReportDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "risk-report-service", url = "http://localhost:8080")
public interface RiskReportClient {

    @GetMapping("/risk-report/{patId}")
    RiskReportDTO getRiskReportByPatientId(@PathVariable String patId);
}
