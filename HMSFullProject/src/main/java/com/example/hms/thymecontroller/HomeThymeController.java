package com.example.hms.thymecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeThymeController {

    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("title", "Hospital Management System");
        return "home"; // This should match the name of your HTML template (home.html)
    }
}

