package com.ClinicManagment.demo.Repository;

import com.ClinicManagment.demo.Entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    List<Prescription> findByPatient_PatientId(int patientId);
}
