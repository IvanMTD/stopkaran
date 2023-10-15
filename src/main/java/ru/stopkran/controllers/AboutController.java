package ru.stopkran.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.stopkran.utils.ImageEncryptUtil;

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

    @ModelAttribute(name = "logo")
    public String logo(){
        return ImageEncryptUtil.loadImage("./src/main/resources/static/images/inverse_default.png");
    }

    @ModelAttribute(name = "auth")
    public boolean auth(@AuthenticationPrincipal User user){
        if(user != null){
            if(user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
