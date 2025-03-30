package com.duckstudios.webshopapi.utils;

import com.duckstudios.webshopapi.dao.*;
import com.duckstudios.webshopapi.models.Category;
import com.duckstudios.webshopapi.models.Product;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Seeder {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

//    public Seeder(CategoryRepository categoryRepository, ProductRepository productRepository) {
//        this.categoryRepository = categoryRepository;
//        this.productRepository = productRepository;
//    }


    public Seeder(CategoryRepository categoryRepository, ProductRepository productRepository, CartRepository cartRepository, CartProductRepository cartProductRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository, PaymentRepository paymentRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {

        Category lpCategory = new Category("LPs");
        Category cassetteCategory = new Category("Cassettes");
        Category platenspelersCategory = new Category("Platenspelers");
        Category cassettespelersCategory = new Category("Cassettespelers");
//        this.categoryRepository.save(lpCategory);

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
    }

    private void createAndSaveProduct(String name, String artist, BigDecimal price, boolean isAvailable, String imageUrl, long stock, Category category) {
        Product product = new Product(name + " - " + artist, "Een klassiek album van " + artist, price, isAvailable, imageUrl, stock, category);
        productRepository.save(product);
    }
}
