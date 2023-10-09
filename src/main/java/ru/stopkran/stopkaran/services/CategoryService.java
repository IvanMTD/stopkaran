package ru.stopkran.stopkaran.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.stopkran.stopkaran.models.Category;
import ru.stopkran.stopkaran.models.Product;
import ru.stopkran.stopkaran.repositories.CategoryRepository;
import ru.stopkran.stopkaran.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Product> findAllProductsByPageableSort(Pageable pageable, long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        return productRepository.findByIdIn(category.getProducts().stream().map(Product::getId).toList(), pageable);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
