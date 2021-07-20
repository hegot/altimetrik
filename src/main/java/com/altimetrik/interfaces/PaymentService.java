package com.altimetrik.interfaces;

import com.altimetrik.domain.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> all();

    Payment createPayment(Payment payment);

    Payment getPayment(Long id);

    Payment replacePayment(Payment payment, Long id);

    void deletePayment(Long id);
}
