package com.medilabo.riskreportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class RiskReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiskReportServiceApplication.class, args);
    }

}
