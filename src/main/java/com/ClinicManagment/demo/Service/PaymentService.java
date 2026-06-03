package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.Bill;
import com.ClinicManagment.demo.Entity.Payment;
import com.ClinicManagment.demo.Repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillingService billingService;

    public PaymentService(PaymentRepository paymentRepository,
                          BillingService billingService) {
        this.paymentRepository = paymentRepository;
        this.billingService = billingService;
    }

    public Payment addPayment(int id, Payment payment) {
        if(payment == null) {
            throw new EntityNotFoundException("Payment is null");
        }

        Bill updatedBill = billingService.processPayment(payment);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
    }



}
