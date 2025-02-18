package com.medilabo.riskreportservice.feign;

import com.medilabo.riskreportservice.DTO.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "patient-service", url = "http://gateway:8080")
public interface PatientClient {

    @GetMapping("/patients/{patId}")
    PatientDTO getPatientById(@PathVariable String patId);
}

