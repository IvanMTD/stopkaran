package ru.stopkran.stopkaran.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.stopkran.stopkaran.models.News;
import ru.stopkran.stopkaran.utils.ImageEncryptUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class NewsService {

    private List<News> newsList;

    public NewsService(){
        newsList = new ArrayList<>();
        init();
    }

    private void init(){
        String image = ImageEncryptUtil.loadImage("./src/main/resources/static/images/news_default.png");
        for(int i=0; i<100; i++){
            News news = new News();
            news.setId(i);
            news.setName("NAME"+i);
            news.setAnnotation("ANNOTATION"+i);
            news.setContent("CONTENT"+i);
            news.setImage(image);
            newsList.add(news);
        }
    }

    public List<News> findAllNewsByPageableSort(Pageable pageable) {
        int pn = pageable.getPageNumber();
        int ps = pageable.getPageSize();
        int start = ps * pn;
        int end = start + ps;
        List<News> sortedNewsList = new ArrayList<>();
        for(int i=start; i<end; i++){
            if(i < newsList.size()) {
                sortedNewsList.add(newsList.get(i));
            }
        }
        return sortedNewsList;
    }

    public List<News> findAll() {
        return newsList;
    }

    public News findById(long id) {
        for(News news : newsList){
            if(news.getId() == id){
                return news;
            }
        }
        return null;
    }
}
