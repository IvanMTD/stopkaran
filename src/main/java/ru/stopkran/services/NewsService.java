package ru.stopkran.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.stopkran.models.News;
import ru.stopkran.repositories.NewsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;


    public List<News> findAllNewsByPageableSort(Pageable pageable) {
        return newsRepository.findAllByOrderByPlacedAtDesc(pageable);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public News findById(long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        return optionalNews.orElse(null);
    }

    public void save(News news){
        newsRepository.save(news);
    }

    public void delete(long id) {
        newsRepository.deleteById(id);
    }
}
