package ru.stopkran.stopkaran.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.stopkran.stopkaran.models.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
    List<Product> findByIdIn(List<Long> ids, Pageable pageable);
}
