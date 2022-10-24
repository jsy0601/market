package com.market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {
    @GetMapping("/api/doc")
    public String redirectSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
