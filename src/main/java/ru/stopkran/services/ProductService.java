package ru.stopkran.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stopkran.models.Product;
import ru.stopkran.repositories.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
