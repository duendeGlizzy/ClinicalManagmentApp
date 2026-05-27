package com.ClinicManagment.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicalrecord")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicalRecordId;

    private Date date;

    private String symptoms;

    private String diagnosis;

    private String notes;

    @OneToOne(mappedBy = "medicalRecord")
    private Vitals vitals;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

}
