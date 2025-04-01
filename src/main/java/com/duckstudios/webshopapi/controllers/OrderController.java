package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.dao.OrderDAO;
import com.duckstudios.webshopapi.dto.OrderDTO;
import com.duckstudios.webshopapi.models.OrderEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/orders")
public class OrderController {
    private final OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getOrders() {
        List<OrderEntity> orders = this.orderDAO.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderEntity>> getOrder(@PathVariable long id) {
        Optional<OrderEntity> order = this.orderDAO.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.createOrder(orderDTO);
        return ResponseEntity.ok("Order aangemaakt!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id){
        this.orderDAO.deleteOrderById(id);
        return ResponseEntity.ok("Order deleted");
    }
}
