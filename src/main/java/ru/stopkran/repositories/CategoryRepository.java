package ru.stopkran.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.stopkran.models.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findAll();
}
