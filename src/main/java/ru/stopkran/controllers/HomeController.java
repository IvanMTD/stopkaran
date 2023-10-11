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
import ru.stopkran.services.NewsService;

@Data
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NewsService newsService;

    private int pageNumber = 0;
    private int pageSize = 6;
    private int pageTotal;

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
        return "news/information";
    }

    @ModelAttribute(name = "title")
    public String title(){
        return "Home Page";
    }
}
