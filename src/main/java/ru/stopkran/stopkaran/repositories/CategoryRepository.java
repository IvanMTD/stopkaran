package ru.stopkran.stopkaran.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.stopkran.stopkaran.models.Category;
import ru.stopkran.stopkaran.models.Product;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    List<Category> findAll();
}
