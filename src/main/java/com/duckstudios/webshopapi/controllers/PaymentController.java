package com.duckstudios.webshopapi.controllers;

import com.duckstudios.webshopapi.dao.PaymentDAO;
import com.duckstudios.webshopapi.dto.PaymentDTO;
import com.duckstudios.webshopapi.models.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentDAO paymentDAO;

    public PaymentController(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(this.paymentDAO.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPaymentById(@PathVariable long id) {
        return ResponseEntity.ok(this.paymentDAO.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentDTO paymentDTO) {
//        this.paymentDAO.createPayment(paymentDTO);
        return ResponseEntity.ok("Payment created!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable long id) {
        this.paymentDAO.deletePayment(id);
        return ResponseEntity.ok("Payment deleted!");
    }

}
