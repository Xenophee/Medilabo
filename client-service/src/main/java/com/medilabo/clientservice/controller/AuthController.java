package com.medilabo.clientservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/views/403";
    }
}
