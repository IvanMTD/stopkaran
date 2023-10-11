package ru.stopkran.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.stopkran.models.Category;
import ru.stopkran.models.News;
import ru.stopkran.models.Product;
import ru.stopkran.services.CategoryService;
import ru.stopkran.services.NewsService;
import ru.stopkran.services.ProductService;
import ru.stopkran.utils.ImageEncryptUtil;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final NewsService newsService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public String adminPage(){
        return "admin/main";
    }

    @PreAuthorize(("hasRole('ADMIN')"))
    @GetMapping("/news")
    public String adminNewsPage(Model model){
        model.addAttribute("news", new News());
        return "admin/news-maker";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/news/add")
    public String addNews(@ModelAttribute(name = "news") @Valid News news, Errors errors, @RequestPart(name = "file") MultipartFile file){
        if(errors.hasErrors()){
            return "admin/news-maker";
        }
        if(file.isEmpty()){
            news.setImage(ImageEncryptUtil.loadImage("./src/main/resources/static/images/default.png"));
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
    public String adminCatalogPage(@ModelAttribute(name = "category") @Valid Category category, Errors errors, @RequestPart(name = "file") MultipartFile file){
        if(errors.hasErrors()){
            return "admin/catalog-maker";
        }
        if(file.isEmpty()){
            category.setImage(ImageEncryptUtil.loadImage("./src/main/resources/static/images/default.png"));
        }else{
            try {
                category.setImage(ImageEncryptUtil.getImgData(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        category.setProducts(new ArrayList<>());
        categoryService.save(category);
        return "redirect:/admin";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/products")
    public String adminProductsPage(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categoryList", categoryService.findAll());
        return "admin/product-maker";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products/add")
    public String adminProductPage(
            @ModelAttribute(name = "product") @Valid Product product,
            Errors errors,
            Model model,
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam(name = "select") long id
    ){
        if(errors.hasErrors()){
            model.addAttribute("categoryList", categoryService.findAll());
            return "admin/product-maker";
        }
        if(file.isEmpty()){
            product.setImage(ImageEncryptUtil.loadImage("./src/main/resources/static/images/default.png"));
        }else{
            try {
                product.setImage(ImageEncryptUtil.getImgData(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        productService.save(product);
        Category category = categoryService.findById(id);
        category.getProducts().add(product);
        categoryService.save(category);
        return "redirect:/admin";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Admin Page";
    }
}
