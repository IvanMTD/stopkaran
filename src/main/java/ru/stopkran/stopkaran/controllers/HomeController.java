package ru.stopkran.stopkaran.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @GetMapping()
    public String homePage(){
        return "home";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Home Page";
    }
}
