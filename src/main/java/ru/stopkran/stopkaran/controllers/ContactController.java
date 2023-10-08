package ru.stopkran.stopkaran.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @GetMapping()
    public String contactPage(){
        return "contact/info";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Contact Page";
    }
}
