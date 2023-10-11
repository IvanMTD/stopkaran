package ru.stopkran.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.stopkran.models.Category;
import ru.stopkran.models.News;
import ru.stopkran.services.NewsService;
import ru.stopkran.utils.ImageEncryptUtil;

import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final NewsService newsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public String adminPage(){
        return "admin/main";
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @GetMapping("/news")
    public String adminNewsPage(Model model){
        model.addAttribute("news", new News());
        model.addAttribute("newsList", newsService.findAll());
        return "admin/news-maker";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/news/add")
    public String addNews(@ModelAttribute(name = "news") News news, @RequestPart(name = "file") MultipartFile file){
        if(file.isEmpty()){
            news.setImage(ImageEncryptUtil.loadImage(".src/main/resources/static/images/news_default.png"));
        }else{
            try {
                news.setImage(ImageEncryptUtil.getImgData(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        newsService.save(news);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/catalog")
    public String adminCatalogPage(Model model){
        model.addAttribute("category", new Category());
        return "admin/catalog-maker";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/catalog/add")
    public String adminCatalogPage(@ModelAttribute(name = "category") Category category, @RequestPart(name = "file") MultipartFile file){
        return "redirect:/admin";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Admin Page";
    }
}
