package com.ClinicManagment.demo.Repository;

import com.ClinicManagment.demo.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    boolean existsByEmail(String email);
    List<Patient> findByLastNameContainingIgnoreCase(String lastName);
    List<Patient> findByDateOfBirth(Date dateOfBirth);
}
