package ru.stopkran.configurations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.stopkran.models.Category;
import ru.stopkran.models.News;
import ru.stopkran.models.Product;
import ru.stopkran.repositories.CategoryRepository;
import ru.stopkran.repositories.NewsRepository;
import ru.stopkran.repositories.ProductRepository;
import ru.stopkran.utils.ImageEncryptUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationConfig {
    @Bean
    public CommandLineRunner dataLoader(NewsRepository newsRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                /*newsRepository.save(getTestNews());
                for(int i=0; i<3; i++){
                    categoryRepository.save(getTestCategory(i,new ArrayList<>()));
                }
                List<Category> categoryList = categoryRepository.findAll();
                for(Category category : categoryList){
                    int random = 5 + (int)(Math.round(Math.random() * 20.0f));
                    List<Product> products = new ArrayList<>();
                    for(int i=0; i< random; i++){
                        products.add(getTestProduct(i));
                    }
                    productRepository.saveAll(products);
                    category.setProducts(products);
                    categoryRepository.save(category);
                }*/
            }
        };
    }

    private News getTestNews(){
        News news = new News();
        news.setName("Открытие Бара");
        news.setAnnotation("Ждем Вас в гости. Бар работает до 00:00");
        news.setContent("Просто хорошая новость об открытии Бара. Ждём вас в гости.");
        news.setImage(ImageEncryptUtil.loadImage("./src/main/resources/static/images/news_default.png"));
        return news;
    }

    private Product getTestProduct(int n){
        Product product = new Product();
        product.setName("Имя продукта " + n);
        product.setDescription("Описание продукта " + n);
        product.setCoast(new BigDecimal(100 + n));
        product.setImage(ImageEncryptUtil.loadImage("./src/main/resources/static/images/craft_beer.png"));
        return product;
    }

    private Category getTestCategory(int n, List<Product> products){
        Category category = new Category();
        category.setName("Имя категории " + n);
        category.setDescription("Описание категории " + n);
        category.setProducts(products);
        category.setImage(ImageEncryptUtil.loadImage("./src/main/resources/static/images/base_menu.png"));
        return category;
    }
}
