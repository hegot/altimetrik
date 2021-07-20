package com.altimetrik.controller;

import com.altimetrik.domain.Payment;
import com.altimetrik.interfaces.PaymentService;
import com.altimetrik.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class PaymentController {
    @Autowired
    private PaymentRepository repository;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payments")
    List<Payment> all() {
        return paymentService.all();
    }

    @PostMapping("/payments")
    Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @GetMapping("/payments/{id}")
    Payment getPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

    @PutMapping("/payments/{id}")
    Payment replacePayment(@RequestBody Payment payment, @PathVariable Long id) {
        return paymentService.replacePayment(payment, id);
    }

    @DeleteMapping("/payments/{id}")
    void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}