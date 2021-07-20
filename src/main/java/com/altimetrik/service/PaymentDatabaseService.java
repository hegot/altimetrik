package com.altimetrik.service;

import com.altimetrik.domain.Payment;
import com.altimetrik.exception.PaymentNotFoundException;
import com.altimetrik.interfaces.PaymentService;
import com.altimetrik.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
public class PaymentDatabaseService implements PaymentService {
    @Autowired
    private PaymentRepository repository;

    public List<Payment> all() {
        return repository.findAll();
    }

    public Payment createPayment(Payment payment) {
        return repository.save(payment);
    }

    public Payment getPayment(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    public Payment replacePayment(Payment payment, Long id) {
        return repository.findById(id)
                .map(item -> {
                    payment.setAmount(item.getAmount());
                    payment.setCurrency(item.getCurrency());
                    payment.setUser(item.getUser());
                    payment.setTargetAccount(item.getTargetAccount());
                    return repository.save(payment);
                })
                .orElseGet(() -> {
                    payment.setId(id);
                    return repository.save(payment);
                });
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }
}
