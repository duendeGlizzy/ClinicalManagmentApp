package com.ClinicManagment.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "provider")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int providerId;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    private String title;

    @OneToMany(mappedBy = "provider")
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "provider")
    private List<LabRequest> labRequests;

    @OneToMany(mappedBy = "provider")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules;
}
