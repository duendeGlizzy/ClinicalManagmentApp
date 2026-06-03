package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.*;
import com.ClinicManagment.demo.Enum.BillStatus;
import com.ClinicManagment.demo.Repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    private final BillRepository billRepository;

    public BillingService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(int id) {
        if(!billRepository.existsById(id)){
            throw new IllegalArgumentException("Bill does not exist");
        }
        return billRepository.findById(id);
    }

    @Transactional
    public Bill generateBill( Bill bill) {
        Patient patient = getPatient(bill);

        BigDecimal totalPrice = new BigDecimal("0.0");
        BigDecimal insuranceTotal = new BigDecimal("0.0");

        for (Appointment appointment : bill.getAppointments()) {
           if(appointment.getPrice() != null) {
               totalPrice = totalPrice.add(appointment.getPrice());
           }
        }

        if(bill.getLabRequests() != null) {
            for(LabRequest labRequest : bill.getLabRequests()) {
                if(labRequest.getLabTest() != null && labRequest.getLabTest().getPrice() != null) {
                    totalPrice = totalPrice.add(labRequest.getLabTest().getPrice());
                }
            }
        }

        bill.setTotalAmount(totalPrice);


        if(patient.getInsurance() != null) {
            insuranceTotal = totalPrice.multiply(new BigDecimal("0.80"))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        bill.setInsuranceCoveredAmount(insuranceTotal);

        BigDecimal patientTotal = totalPrice.subtract(insuranceTotal);
        bill.setPatientCoveredAmount(patientTotal);
        bill.setBillStatus(BillStatus.UNPAID);
        bill.setDueDate(LocalDateTime.now().plusMonths(3));

        return billRepository.save(bill);

    }



    private static Patient getPatient(Bill bill) {
        if(bill == null) {
            throw new IllegalArgumentException("Bill cannot be null");
        }
        if(bill.getAppointments() == null || bill.getAppointments().isEmpty()) {
            throw new IllegalArgumentException("Appointment list cannot be empty");
        }

        Appointment firstAppointment = bill.getAppointments().getFirst();

        if(firstAppointment.getPatient() == null) {
            throw new IllegalArgumentException("Patient cannot be null");
        }
        return firstAppointment.getPatient();
    }

    @Transactional
   public Bill updateBillStatus(int id, BillStatus status) {
        Bill currentBill = billRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bill does not exist"));

        currentBill.setBillStatus(status);
        return billRepository.save(currentBill);
   }

   public List<Payment> getPayments(Bill bill) {
        if(bill.getPayments() == null|| bill.getPayments().isEmpty()) {
            throw new IllegalArgumentException("Payment list cannot be empty");
        }
        return bill.getPayments();
   }

   public Bill processPayment(Payment payment) {
        Bill currentBill = billRepository.findById(payment.getBill().getBillId())
                .orElseThrow(() -> new IllegalArgumentException("Bill does not exist"));

        if(currentBill.getTotalAmount() == null) {
            throw new IllegalArgumentException("Total amount cannot be null");
        }

        BigDecimal totalAmount = currentBill.getTotalAmount();

       if (totalAmount.subtract(payment.getTotalAmount()).compareTo(BigDecimal.ZERO) < 0) {
           throw new IllegalArgumentException("Total amount cannot be negative");
        }

       totalAmount = totalAmount.subtract(payment.getTotalAmount());

       if(totalAmount.compareTo(BigDecimal.ZERO) > 0){
           currentBill.setBillStatus(BillStatus.PARTIALLY_PAID);
       }else if(totalAmount.compareTo(BigDecimal.ZERO) == 0){
           currentBill.setBillStatus(BillStatus.PAID);
       }

       return billRepository.save(currentBill);
   }





}
