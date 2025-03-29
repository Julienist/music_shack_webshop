package com.duckstudios.webshopapi.dao;

import com.duckstudios.webshopapi.models.Payment;
import com.duckstudios.webshopapi.services.EntityValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PaymentDAO {

    private final PaymentRepository paymentRepository;
    private final EntityValidator entityValidator;

    public PaymentDAO(PaymentRepository paymentRepository, EntityValidator entityValidator) {
        this.paymentRepository = paymentRepository;
        this.entityValidator = entityValidator;
    }

    public List<Payment> getAllPayments() {
        return this.paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(long id) {
        entityValidator.checkIfIdExists(id, paymentRepository, "Payment");
        return this.paymentRepository.findById(id);
    }

//    public void createPayment(Payment payment) {
//        this.paymentRepository.save(payment);
//    }

//    public void deletePayment(long id) {
//        Payment payment = entityValidator.checkIfIdExists(id, paymentRepository, "Payment");
//        this.paymentRepository.delete(payment);
//    }

}
