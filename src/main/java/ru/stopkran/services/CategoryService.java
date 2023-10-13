package ru.stopkran.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.stopkran.models.Category;
import ru.stopkran.models.Product;
import ru.stopkran.repositories.CategoryRepository;
import ru.stopkran.repositories.ProductRepository;

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

    public Category findById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void save(Category category){
        categoryRepository.save(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
