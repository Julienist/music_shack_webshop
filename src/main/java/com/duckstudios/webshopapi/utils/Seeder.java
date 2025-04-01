package com.duckstudios.webshopapi.utils;

import com.duckstudios.webshopapi.dao.*;
import com.duckstudios.webshopapi.models.*;
import com.duckstudios.webshopapi.models.enums.*;
import jakarta.transaction.Transactional;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Seeder {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
//    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Seeder(CategoryRepository categoryRepository, ProductRepository productRepository,
                  CartRepository cartRepository, CartProductRepository cartProductRepository,
                  OrderRepository orderRepository, OrderProductRepository orderProductRepository,
                  PaymentRepository paymentRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
//        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedCategoriesAndProducts();
        seedUsersAndOrders();
    }

    private void seedCategoriesAndProducts() {
        Category lpCategory = new Category("LPs");
        Category cassetteCategory = new Category("Cassettes");
        Category platenspelersCategory = new Category("Platenspelers");
        Category cassettespelersCategory = new Category("Cassettespelers");

        categoryRepository.saveAll(List.of(lpCategory, cassetteCategory, platenspelersCategory, cassettespelersCategory));

        // LP's
        createProducts(lpCategory, new String[][]{
                {"The Dark Side of the Moon", "Pink Floyd", "45.99", "true", "https://example.com/darkside.jpg", "20"},
                {"Abbey Road", "The Beatles", "39.99", "true", "https://example.com/abbeyroad.jpg", "15"},
                {"Back in Black", "AC/DC", "42.50", "true", "https://example.com/backinblack.jpg", "10"},
                {"Rumours", "Fleetwood Mac", "38.75", "true", "https://example.com/rumours.jpg", "25"},
                {"Nevermind", "Nirvana", "41.20", "true", "https://example.com/nevermind.jpg", "18"}
        });

        // Cassettes
        createProducts(cassetteCategory, new String[][]{
                {"Master of Puppets", "Metallica", "35.99", "true", "https://example.com/master.jpg", "10"},
                {"Purple Rain", "Prince", "33.50", "true", "https://example.com/purplerain.jpg", "12"},
                {"Thriller", "Michael Jackson", "37.75", "true", "https://example.com/thriller.jpg", "14"},
                {"Born to Run", "Bruce Springsteen", "31.99", "true", "https://example.com/borntorun.jpg", "9"},
                {"Led Zeppelin IV", "Led Zeppelin", "36.20", "true", "https://example.com/zeppelin4.jpg", "11"}
        });

        // Platenspelers
        createProducts(platenspelersCategory, new String[][]{
                {"Technics SL-1210", "Technics", "899.99", "true", "https://example.com/technics.jpg", "5"},
                {"Audio-Technica AT-LP60", "Audio-Technica", "149.99", "true", "https://example.com/audiotech.jpg", "8"},
                {"Sony PS-LX310BT", "Sony", "199.99", "true", "https://example.com/sony.jpg", "7"},
                {"Pioneer PLX-500", "Pioneer", "299.99", "true", "https://example.com/pioneer.jpg", "4"},
                {"Rega Planar 1", "Rega", "275.50", "true", "https://example.com/rega.jpg", "6"}
        });

        // Cassettespelers
        createProducts(cassettespelersCategory, new String[][]{
                {"Sony TC-WE475", "Sony", "119.99", "true", "https://example.com/sony_tc.jpg", "6"},
                {"Pioneer CT-W208R", "Pioneer", "134.99", "true", "https://example.com/pioneer_ct.jpg", "4"},
                {"JVC TD-W354", "JVC", "109.99", "true", "https://example.com/jvc_td.jpg", "5"},
                {"Marantz PMD-300CP", "Marantz", "159.99", "true", "https://example.com/marantz_pmd.jpg", "3"},
                {"Denon DRW-695", "Denon", "129.99", "true", "https://example.com/denon_drw.jpg", "7"}
        });
    }

    private void createProducts(Category category, String[][] productsData) {
        for (String[] data : productsData) {
            BigDecimal price = new BigDecimal(data[2]);
            Product product = new Product(data[0] + " - " + data[1], "Een klassiek product van " + data[1], price, Boolean.parseBoolean(data[3]), data[4], Integer.parseInt(data[5]), category);
            productRepository.save(product);
        }
    }

    private void seedUsersAndOrders() {

        CustomUser dummyUser = createUser("dummy@example.com", "Password123#", Role.CUSTOMER);
        CustomUser cartUser = createUser("cartuser@example.com", "Password123#", Role.CUSTOMER);
        CustomUser orderUser = createUser("orderuser@example.com", "Password123#", Role.CUSTOMER);
        CustomUser adminUser = createUser("admin@example.com", "Adminpassword1#", Role.ADMIN);

        // Winkelmandje maken en opslaan
        Cart cart = new Cart( new ArrayList<>(), cartUser, true);
        cartRepository.save(cart);

        List<Product> products = productRepository.findAll();
        addProductsToCart(cart, products.subList(0, 3)); // Voeg 3 producten toe

        // Order aanmaken met betaling
        createOrderWithPayment(orderUser, products.subList(3, 5)); // Voeg 2 producten toe aan de order
    }

    private CustomUser createUser(String email, String rawPassword, Role role) {
        CustomUser user = new CustomUser();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword)); // Hash password before storing
        user.setRole(role);
        return userRepository.save(user);
    }

    private void addProductsToCart(Cart cart, List<Product> products) {
        for (Product product : products) {
            CartProduct cartProduct = new CartProduct(cart, product, 1, product.getPrice());
            cartProductRepository.save(cartProduct);
        }
    }

    @Transactional
    public void createOrderWithPayment(CustomUser user, List<Product> products) {
        // 1️⃣ Haal een persistente user op om detach errors te vermijden
        CustomUser persistentUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // 2️⃣ Maak de order aan
        OrderEntity order = new OrderEntity(persistentUser, LocalDateTime.now(), OrderStatus.PAID, BigDecimal.ZERO);

        // 3️⃣ Maak de OrderProducts aan en koppel ze aan de OrderEntity
        BigDecimal total = BigDecimal.ZERO;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Product product : products) {
            OrderProduct orderProduct = new OrderProduct(order, product, 1, product.getPrice());
            orderProducts.add(orderProduct);
            total = total.add(product.getPrice());
        }

        // 4️⃣ Zet de producten in de order en sla alles op in één keer
        order.setOrderProducts(orderProducts);
        order.setTotalPrice(total);

        orderRepository.saveAndFlush(order);

        // 5️⃣ Check of de producten zijn opgeslagen
        System.out.println("✅ Order opgeslagen met " + order.getOrderProducts().size() + " producten!");
    }

}
