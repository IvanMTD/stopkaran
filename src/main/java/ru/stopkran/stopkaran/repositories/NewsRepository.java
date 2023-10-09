package ru.stopkran.stopkaran.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.stopkran.stopkaran.models.News;

import java.util.List;

public interface NewsRepository extends CrudRepository<News,Long> {
    List<News> findAll();
    List<News> findAll(Pageable pageable);
}
