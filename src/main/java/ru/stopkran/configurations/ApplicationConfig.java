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
import java.util.List;

@Configuration
public class ApplicationConfig {
    @Bean
    public CommandLineRunner dataLoader(NewsRepository newsRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //newsRepository.save(getTestNews());
                /*for(int i=0; i<3; i++){
                    List<Product> productList = new ArrayList<>();
                    for(int j=0; j<30; j++){
                        productList.add(getTestProduct((i+1) * (j+1)));
                    }
                    productRepository.saveAll(productList.stream().toList());
                    categoryRepository.save(getTestCategory(i,new ArrayList<>()));
                }*/
                /*List<Category> categoryList = categoryRepository.findAll();
                for(Category category : categoryList){
                    List<Product> products = new ArrayList<>();
                    for(int i=0; i<30; i++){
                        productRepository.save(getTestProduct(i));
                    }
                    category.setProducts(productRepository.findAll());
                    categoryRepository.save(category);
                }*/
            }
        };
    }

    private News getTestNews(){
        News news = new News();
        news.setName("Имя новости");
        news.setAnnotation("Это простая аннотация для новостей");
        news.setContent("Некий контент для новостей из разных источников. Для вашего удовольствия!");
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
