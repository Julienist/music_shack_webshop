package com.Julienshop.webshopapi.controllers;

import com.Julienshop.webshopapi.dao.PaymentDAO;
import com.Julienshop.webshopapi.dto.PaymentDTO;
import com.Julienshop.webshopapi.models.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable long id) {
        this.paymentDAO.deletePayment(id);
        return ResponseEntity.ok("Payment deleted!");
    }

}
