package com.Julienshop.webshopapi.dao;

import com.Julienshop.webshopapi.dto.OrderDTO;
import com.Julienshop.webshopapi.dto.OrderProductDTO;
import com.Julienshop.webshopapi.models.CustomUser;
import com.Julienshop.webshopapi.models.OrderEntity;
import com.Julienshop.webshopapi.models.OrderProduct;
import com.Julienshop.webshopapi.models.Product;
import com.Julienshop.webshopapi.services.EntityValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {

    private final OrderRepository orderRepository;
    private final EntityValidator entityValidator;
    private final ProductRepository productRepository;
    private final CustomUserDAO customUserDAO;

    public OrderDAO(OrderRepository orderRepository, EntityValidator entityValidator,
                    ProductRepository productRepository, CustomUserDAO customUserDAO) {
        this.orderRepository = orderRepository;
        this.entityValidator = entityValidator;
        this.productRepository = productRepository;
        this.customUserDAO = customUserDAO;
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
//        List<OrderEntity> orders = this.orderRepository.findAll();
//        return orders;
    }

    public Optional<OrderEntity> getOrderById(long id) {
        entityValidator.checkIfIdExists(id, orderRepository, "Order");
        return orderRepository.findById(id);
//        Optional<OrderEntity> order = this.orderRepository.findById(id);
        // Opgesplitst in 2 lines, voor leesbaarheid
//        return order;
    }

    public List<OrderEntity> getOrdersForCurrentUser(String email) {
        CustomUser customUser = customUserDAO.getCustomUserByEmail(email);
        return orderRepository.findOrderEntityByCustomUser(customUser);
    }

//    public void createOrder(OrderDTO orderDTO, Principal principal) {
//        CustomUser customuserId = entityValidator.checkIfIdExists(orderDTO.customUserId, userRepository, "CustomuserId");
//        OrderEntity order = new OrderEntity(orderDTO.orderDate, orderDTO.orderStatus, orderDTO.totalPrice, orderDTO.orderProducts, orderProductDTO.productId, ord);
//        this.orderRepository.save(order);
//    }

//    public void createOrder(OrderDTO orderDTO) {
//        // 1. Haal ingelogde gebruiker op via email
//        CustomUser currentUser = userRepository.findByEmail(orderDTO.email);
//
//        // 2. Maak lege order aan
//        OrderEntity order = new OrderEntity();
//        order.setCustomUser(currentUser);
//        order.setOrderDate(orderDTO.orderDate);
//        order.setOrderStatus(orderDTO.orderStatus);
//        order.setTotalPrice(orderDTO.totalPrice);
//
//        // 3. Order eerst saven zodat hij een ID heeft
////        order = orderRepository.save(order);
//
////        // 4. Voor elke orderProductDTO → maak OrderProduct aan
////        for (OrderProduct dto : orderDTO.orderProducts) {
////            Product product = productRepository.findById(dto.getId())
////                    .orElseThrow(() -> new RuntimeException("Product niet gevonden met id: " + dto.getId()));
////
////            OrderProduct orderProduct = new OrderProduct();
////            orderProduct.setOrder(order); // belangrijk voor FK
////            orderProduct.setProduct(product);
////            orderProduct.setQuantity(dto.getQuantity());
////            orderProduct.setTotalPrice(dto.getTotalPrice());
////
////            orderProductRepository.save(orderProduct);
////        }
//
//        List<OrderProduct> orderProducts = new ArrayList<>();
//
//        for (OrderProductDTO orderProductDTO : orderDTO.orderProducts) {
//            ProductDTO product = productRepository.findById(productDTO.id)
//                    .orElseThrow(() -> new RuntimeException("Product niet gevonden"));
//
//            OrderProductDTO oderProduct = new OrderProduct(order, product, orderProductDTO.quantity, orderProductDTO.totalPrice);
//            orderProducts.add(op);
//        }
//
//        order.setOrderProducts(orderProducts);
//
//        orderRepository.save(order);
//
//        // Klaar! (optional: return OrderEntity als je het nodig hebt)
//    }

    @Transactional
    public void createOrder(OrderDTO orderDTO) {
        // 1. Haal ingelogde gebruiker op via email
        CustomUser currentUser = customUserDAO.getCustomUserByEmail(orderDTO.email);

        // 2. Maak een nieuwe order aan
        OrderEntity order = new OrderEntity();
        order.setCustomUser(currentUser);
        order.setOrderDate(orderDTO.orderDate);
        order.setOrderStatus(orderDTO.orderStatus);
        order.setTotalPrice(orderDTO.totalPrice);

        // 3. Maak orderProducts aan
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (OrderProductDTO orderProductDTO : orderDTO.orderProducts) {
            Product product = productRepository.findById(orderProductDTO.productId)
                    .orElseThrow(() -> new RuntimeException("Product niet gevonden met ID: " + orderProductDTO.productId));

            OrderProduct orderProduct = new OrderProduct(order, product, orderProductDTO.quantity, orderProductDTO.totalPrice);
            orderProducts.add(orderProduct);
        }



        // 4. Koppel producten aan order
        order.setOrderProducts(orderProducts);

        // 5. Save alles in één keer (cascade!)
        orderRepository.save(order);
    }


    @Transactional
    public void deleteOrderById(long id) {
        OrderEntity order = entityValidator.checkIfIdExists(id, orderRepository, "OrderEntity");
        this.orderRepository.delete(order);
    }
}
