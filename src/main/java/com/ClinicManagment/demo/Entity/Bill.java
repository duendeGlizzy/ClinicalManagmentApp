package com.ClinicManagment.demo.Entity;

import com.ClinicManagment.demo.Enum.BillStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billId;

    private BigDecimal totalAmount;

    private BigDecimal insuranceCoveredAmount;

    private BigDecimal patientCoveredAmount;

    private Date dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BillStatus billStatus;

    @OneToMany(mappedBy = "bill")
    private List<LabRequest> labRequests;

    @OneToMany(mappedBy = "bill")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "bill")
    private List<Payment> payments;

}
