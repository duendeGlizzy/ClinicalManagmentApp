package com.ClinicManagment.demo.Repository;

import com.ClinicManagment.demo.Entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    List<MedicalRecord> findMedicalRecordsByAppointment_Patient_PatientId(int patientId);
}
