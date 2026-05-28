package com.ClinicManagment.demo.Entity;

import com.ClinicManagment.demo.Enum.PatientGender;
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
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private PatientGender gender;

    private String email;

    private String password;

    private Date dateOfBirth;

    private String address;


    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @OneToMany(mappedBy = "patient")
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "patient")
    private List<LabRequest> labRequests;



}
