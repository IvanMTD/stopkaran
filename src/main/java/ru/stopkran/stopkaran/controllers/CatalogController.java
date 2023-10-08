package ru.stopkran.stopkaran.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.stopkran.stopkaran.models.Product;
import ru.stopkran.stopkaran.services.CategoryServices;

import java.util.ArrayList;
import java.util.List;

@Data
@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CategoryServices categoryServices;
    List<Product> products;
    private int menuNumber;

    private int pageNumber;
    private int pageSize = 6;
    private int pageTotal;

    @GetMapping("/menu")
    public String categoryPage(Model model){
        model.addAttribute("categories",categoryServices.findAll());
        return "catalog/menu";
    }

    @GetMapping("/menu/{menuNumber}")
    public String redirectOnMenu(@PathVariable("menuNumber") int menuNumber){
        this.menuNumber = menuNumber;
        products = categoryServices.findAll().get(menuNumber).getProducts();
        return "redirect:/catalog/menu/list/0";
    }

    @GetMapping("/menu/list/{pageNumber}")
    public String catalogListPage(Model model, @PathVariable("pageNumber") int pageNumber){
        int diff = products.size() % pageSize;
        if(diff > 0){
            pageTotal = (products.size() / pageSize) + 1;
        }else{
            pageTotal = products.size() / pageSize;
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        model.addAttribute("products",categoryServices.findAllProductsByPageableSort(pageable,menuNumber));
        model.addAttribute("pageTotal",pageTotal);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageSize",pageSize);
        return "catalog/list";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Catalog Page";
    }
}
