package com.duckstudios.webshopapi.utils;

import com.duckstudios.webshopapi.dao.CategoryRepository;
import com.duckstudios.webshopapi.dao.ProductRepository;
import com.duckstudios.webshopapi.dao.TaskRepository;
import com.duckstudios.webshopapi.models.Category;
import com.duckstudios.webshopapi.models.Product;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Seeder {

//    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Seeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
//        Category category = new Category("Werk taken");
//        this.categoryRepository.save(category);

//        Task task1 = new Task("Videos maken springboot","maken van videos", category);
//        Task task2 = new Task("Videos editen springboot","editen van videos", category);

//        this.taskRepository.save(task1);
//        this.taskRepository.save(task2);

        Category lpCategory = new Category("LPs");
        this.categoryRepository.save(lpCategory);

        // LP's aanmaken en opslaan
        String[][] lpData = {
                {"The Dark Side of the Moon", "Pink Floyd", "45.99", "true", "https://example.com/darkside.jpg", "20"},
                {"Abbey Road", "The Beatles", "39.99", "true", "https://example.com/abbeyroad.jpg", "15"},
                {"Back in Black", "AC/DC", "42.50", "true", "https://example.com/backinblack.jpg", "10"},
                {"Rumours", "Fleetwood Mac", "38.75", "true", "https://example.com/rumours.jpg", "25"},
                {"Nevermind", "Nirvana", "41.20", "true", "https://example.com/nevermind.jpg", "18"}
        };

        for (String[] lp : lpData) {
            BigDecimal price = new BigDecimal(lp[2]);
            createAndSaveProduct(lp[0], lp[1], price, Boolean.parseBoolean(lp[3]), lp[4], Integer.parseInt(lp[5]), lpCategory);
        }

        //        Product product1 = new Product();
    //        Product product2 = new Product();
    //        Product product3 = new Product();
    //        Product product4 = new Product();
    //        Product product5 = new Product();
    //        this.productRepository.save(product1);
    //        this.productRepository.save(product2);
    //        this.productRepository.save(product3);
    //        this.productRepository.save(product4);
    //        this.productRepository.save(product5);
    }

    private void createAndSaveProduct(String name, String artist, BigDecimal price, boolean isAvailable, String imageUrl, long stock, Category category) {
        Product product = new Product(name + " - " + artist, "Een klassiek album van " + artist, price, isAvailable, imageUrl, stock, category);
        productRepository.save(product);
    }
}
