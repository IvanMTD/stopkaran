package ru.stopkran.controllers;

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
import ru.stopkran.models.Category;
import ru.stopkran.models.Product;
import ru.stopkran.services.CategoryService;

import java.util.List;

@Data
@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CategoryService categoryService;
    List<Product> products;
    private int menuNumber;

    private int pageNumber;
    private int pageSize = 6;
    private int pageTotal;

    private int lastPage;

    @GetMapping("/menu")
    public String categoryPage(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "catalog/menu";
    }

    @GetMapping("/menu/{menuNumber}")
    public String redirectOnMenu(@PathVariable("menuNumber") int categoryId){
        this.menuNumber = categoryId;
        products = categoryService.findById(categoryId).getProducts();
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
        lastPage = pageNumber;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        model.addAttribute("products", categoryService.findAllProductsByPageableSort(pageable,menuNumber));
        model.addAttribute("pageTotal",pageTotal);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("category", categoryService.findById(menuNumber));
        return "catalog/list";
    }

    @GetMapping("/{menuNum}/{productNum}")
    public String productPage(Model model, @PathVariable("menuNum") int categoryId, @PathVariable("productNum") int productId){
        Category category = categoryService.findById(categoryId);
        Product product = category.getProducts().stream().filter(p -> p.getId() == productId).findFirst().orElse(null);
        model.addAttribute("product",product);
        model.addAttribute("lastPage",lastPage);
        return "catalog/product";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Catalog Page";
    }
}
