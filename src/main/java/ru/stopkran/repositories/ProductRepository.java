package ru.stopkran.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.stopkran.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
    List<Product> findByIdIn(List<Long> ids, Pageable pageable);
    List<Product> findAll();
}
