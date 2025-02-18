package com.medilabo.clientservice.feign;

import com.medilabo.clientservice.DTO.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "patient-service", url = "http://gateway:8080")
public interface PatientClient {

    @GetMapping("/patients")
    List<PatientDTO> getAllPatients();

    @GetMapping("/patients/{patId}")
    PatientDTO getPatientById(@PathVariable String patId);

    @PostMapping("/patients")
    PatientDTO create(@RequestBody PatientDTO newPatient);

    @PutMapping("/patients/{patId}")
    PatientDTO update(@PathVariable String patId, @RequestBody PatientDTO updatedPatient);
}
