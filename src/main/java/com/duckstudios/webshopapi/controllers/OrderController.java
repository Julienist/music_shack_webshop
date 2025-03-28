package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.dao.OrderDAO;
import com.duckstudios.webshopapi.dto.OrderDTO;
import com.duckstudios.webshopapi.models.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/Orders")
public class OrderController {
    private final OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = this.orderDAO.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Order>> getOrder(@PathVariable long id) {
        Optional<Order> order = this.orderDAO.getOrder(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.createOrder(orderDTO);
        return ResponseEntity.ok("Order aangemaakt!");
    }
}
