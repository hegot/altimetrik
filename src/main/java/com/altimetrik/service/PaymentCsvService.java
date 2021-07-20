package com.altimetrik.service;

import com.altimetrik.domain.Payment;
import com.altimetrik.exception.PaymentNotFoundException;
import com.altimetrik.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentCsvService implements PaymentService {
    @Autowired
    private CsvWriter csvWriter;

    @Autowired
    private CsvReader csvReader;

    public Payment createPayment(Payment payment) {
        List<Payment> payments = csvReader.loadObjectList(Payment.class);
        if(!payments.isEmpty()){
            payment.setId((long) payments.size());
            payments.add(payment);
        }else{
            payments = new ArrayList<>();
            payment.setId(0L);
            payments.add(payment);
        }

        csvWriter.writePayments(payments);
        return payment;
    }

    @Override
    public Payment getPayment(Long id) {
        List<Payment> payments = csvReader.loadObjectList(Payment.class);
        return payments.stream().filter(p -> p.getId().equals(id)).findFirst().orElseThrow(() -> new PaymentNotFoundException(id));
    }

    public List<Payment> all() {
        return csvReader.loadObjectList(Payment.class);
    }

    public void deletePayment(Long id) {
        List<Payment> payments = csvReader.loadObjectList(Payment.class);
        payments.removeIf(p -> p.getId().equals(id));
        csvWriter.writePayments(payments);
    }

    public Payment replacePayment(Payment payment, Long id) {
        List<Payment> payments = csvReader.loadObjectList(Payment.class);
        List<Payment> paymentsFiltered = payments.stream()
                .filter(p -> p.getId().equals(id)).collect(Collectors.toList());
        if (!paymentsFiltered.isEmpty()) {
            payments = payments.stream()
                    .map(p -> {
                        if (p.getId().equals(id)) {
                            p.setAmount(payment.getAmount());
                            p.setCurrency(payment.getCurrency());
                            p.setUser(payment.getUser());
                            p.setTargetAccount(payment.getTargetAccount());
                        }
                        return p;
                    }).collect(Collectors.toList());
        } else {
            payment.setId(id);
            payments.add(payment);
        }

        csvWriter.writePayments(payments);
        return payment;
    }
}
