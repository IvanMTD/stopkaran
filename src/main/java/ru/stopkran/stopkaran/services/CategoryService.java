package ru.stopkran.stopkaran.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.stopkran.stopkaran.models.Category;
import ru.stopkran.stopkaran.models.Product;
import ru.stopkran.stopkaran.utils.ImageEncryptUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private List<Category> categories;

    public CategoryService(){
        categories = new ArrayList<>();
        init();
    }

    private void init(){
        String menuImage = ImageEncryptUtil.loadImage("./src/main/resources/static/images/base_menu.png");
        String productImage = ImageEncryptUtil.loadImage("./src/main/resources/static/images/craft_beer.png");
        for(int i=0; i<5; i++){
            Category category = new Category();
            category.setId(i);
            category.setName("TEST"+i);
            category.setDescription("TESTING"+i);
            category.setImage(menuImage);
            List<Product> products = new ArrayList<>();
            for(int j=0; j<50; j++){
                Product product = new Product();
                product.setId(j);
                product.setName("Test name " + j);
                product.setDescription("This is a test description " + j);
                product.setCoast(new BigDecimal(100 + j));
                product.setImage(productImage);
                products.add(product);
            }
            category.setProducts(products);
            categories.add(category);
        }
    }

    public List<Product> findAllProductsByPageableSort(Pageable pageable, int category) {
        int pn = pageable.getPageNumber();
        int ps = pageable.getPageSize();
        int start = ps * pn;
        int end = start + ps;
        List<Product> sortedProductList = new ArrayList<>();
        for(int i=start; i<end; i++){
            if(i < categories.get(category).getProducts().size()) {
                sortedProductList.add(categories.get(category).getProducts().get(i));
            }
        }
        return sortedProductList;
    }

    public List<Category> findAll() {
        return categories;
    }
}
