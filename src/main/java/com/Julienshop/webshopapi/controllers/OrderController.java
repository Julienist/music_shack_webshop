package com.Julienshop.webshopapi.controllers;

import com.Julienshop.webshopapi.dao.OrderDAO;
import com.Julienshop.webshopapi.dto.OrderDTO;
import com.Julienshop.webshopapi.models.CustomUser;
import com.Julienshop.webshopapi.models.OrderEntity;
import com.Julienshop.webshopapi.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/orders")
public class OrderController {
    private final OrderDAO orderDAO;
    private final AuthenticationService authenticationService;

    public OrderController(OrderDAO orderDAO,
                           AuthenticationService authenticationService) {
        this.orderDAO = orderDAO;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/my_orders")
    public ResponseEntity<List<OrderEntity>> getOrdersForCurrentUser() {
        CustomUser customUser = authenticationService.getAuthenticatedUser();
        List<OrderEntity> orders = this.orderDAO.getOrdersForCurrentUser(customUser);
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderEntity>> getOrders() {
        List<OrderEntity> orders = this.orderDAO.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderEntity>> getOrder(@PathVariable long id) {
        Optional<OrderEntity> order = this.orderDAO.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        CustomUser customUser = authenticationService.getAuthenticatedUser();
        orderDAO.createOrder(customUser, orderDTO);
        return ResponseEntity.ok("Order aangemaakt!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id) {
        this.orderDAO.deleteOrderById(id);
        return ResponseEntity.ok("Order deleted");
    }
}
