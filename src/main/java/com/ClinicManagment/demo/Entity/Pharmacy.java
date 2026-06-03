package com.ClinicManagment.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pharmacy")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pharmacyId;

    private String name;

    private String address;

    private String phoneNumber;

    @OneToMany(mappedBy = "pharmacy")
    @JsonIgnoreProperties("pharmacy")
    private List<Prescription> prescriptions;

}
