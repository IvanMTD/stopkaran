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

    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }
}
