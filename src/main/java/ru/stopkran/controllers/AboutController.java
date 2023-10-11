package ru.stopkran.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

    @GetMapping()
    public String aboutPage(){
        return "about/detail";
    }

    @ModelAttribute(name = "About Page")
    public String title(){
        return "About Page";
    }
}
