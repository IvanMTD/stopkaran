package ru.stopkran.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.stopkran.models.News;
import ru.stopkran.services.NewsService;
import ru.stopkran.utils.ImageEncryptUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@Data
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NewsService newsService;

    private int pageNumber = 0;
    private int pageSize = 3;
    private int pageTotal;

    private int lastPage;

    @GetMapping()
    public String homePage(Model model){
        int diff = newsService.findAll().size() % pageSize;
        if(diff > 0){
            pageTotal = (newsService.findAll().size() / pageSize) + 1;
        }else{
            pageTotal = newsService.findAll().size() / pageSize;
        }
        Pageable pageable = PageRequest.of(0,pageSize);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageTotal",pageTotal);
        model.addAttribute("newsList", newsService.findAllNewsByPageableSort(pageable));
        return "home";
    }

    @GetMapping("/news/page/{pageNumber}")
    public String newsListPage(Model model, @PathVariable("pageNumber") int pageNumber){
        lastPage = pageNumber;
        setPageNumber(pageNumber);
        int diff = newsService.findAll().size() % pageSize;
        if(diff > 0){
            pageTotal = (newsService.findAll().size() / pageSize) + 1;
        }else{
            pageTotal = newsService.findAll().size() / pageSize;
        }
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        model.addAttribute("pageNumber",pageNumber);
        model.addAttribute("pageTotal",pageTotal);
        model.addAttribute("newsList", newsService.findAllNewsByPageableSort(pageable));
        return "home";
    }

    @GetMapping("/news/{id}")
    public String newsPage(Model model, @PathVariable("id") int id){
        model.addAttribute("news",newsService.findById(id));
        model.addAttribute("lastPage",lastPage);
        return "news/information";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/news/edit/{id}")
    public String editNewsPage(Model model, @PathVariable("id") long id){
        model.addAttribute("news", newsService.findById(id));
        return "news/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/news/edit/{id}")
    public String editNewsPage(@PathVariable("id") long id, @ModelAttribute(name = "news") @Valid News news, Errors errors, @RequestPart(name = "file") MultipartFile file){
        if(errors.hasErrors()){
            return "news/edit";
        }
        News original = newsService.findById(id);
        if(!file.isEmpty()){
            try {
                original.setImage(ImageEncryptUtil.getImgData(file.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        original.setName(news.getName());
        original.setAnnotation(news.getAnnotation());
        original.setContent(news.getContent());
        original.setPlacedAt(new Date());
        newsService.save(original);
        return "redirect:/news/" + id;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable("id") long id){
        newsService.delete(id);
        return "redirect:/news/page/0";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "admin/login";
    }

    @GetMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
        return "redirect:/";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Home Page";
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
