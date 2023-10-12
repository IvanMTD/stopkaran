package ru.stopkran.controllers;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.stopkran.models.Category;
import ru.stopkran.models.Product;
import ru.stopkran.services.CategoryService;
import ru.stopkran.services.ProductService;
import ru.stopkran.utils.ImageEncryptUtil;

import java.io.IOException;
import java.util.List;

@Data
@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CategoryService categoryService;
    private final ProductService productService;
    List<Product> products;
    private int menuNumber;

    private int pageNumber;
    private int pageSize = 6;
    private int pageTotal;

    private int lastPage;
    private int lastCategoryId;

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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product/edit/{id}")
    public String productEditPage(Model model, @PathVariable("id") long id){
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("categoryList",categoryService.findAll());
        return "catalog/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/product/edit/{id}")
    public String productEdit(
            @PathVariable("id") long id,
            @ModelAttribute(name = "product") @Valid Product product,
            Errors errors,
            @RequestPart(name = "file")MultipartFile file
    ){
        if(errors.hasErrors()){
            return "catalog/edit";
        }

        Product origin = productService.findById(id);
        if(!file.isEmpty()){
            try {
                origin.setImage(ImageEncryptUtil.getImgData(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        origin.setName(product.getName());
        origin.setDescription(product.getDescription());
        origin.setCoast(product.getCoast());
        productService.save(origin);
        return "redirect:/catalog/menu";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") long id){
        productService.delete(id);
        return "redirect:/catalog/menu";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Catalog Page";
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
