package ru.stopkran.stopkaran.configurations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.stopkran.stopkaran.models.News;
import ru.stopkran.stopkaran.services.NewsService;
import ru.stopkran.stopkaran.utils.ImageEncryptUtil;

@Configuration
public class ApplicationConfig {
    @Bean
    public CommandLineRunner dataLoader(NewsService newsService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                /*News news = new News();
                news.setName("Test News");
                news.setAnnotation("Simple annotation for test news");
                news.setContent("This is some news for the testing a application! Have fun!");
                news.setImage(ImageEncryptUtil.loadImage("./src/main/resources/static/images/news_default.png"));
                newsService.save(news);*/
            }
        };
    }
}
