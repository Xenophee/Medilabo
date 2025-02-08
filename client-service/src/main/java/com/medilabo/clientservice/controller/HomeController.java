package com.medilabo.clientservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private static final String HOME_VIEW = "views/login";

    @GetMapping("/")
    public String getAllPatients(Model model) {
        return HOME_VIEW;
    }
}
