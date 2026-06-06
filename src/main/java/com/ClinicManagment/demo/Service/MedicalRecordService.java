package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.MedicalRecord;
import com.ClinicManagment.demo.Entity.Patient;
import com.ClinicManagment.demo.Repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientService patientService;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                PatientService patientService) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientService = patientService;
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecordById(int id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medical Record Not Found"));
    }

    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public List<MedicalRecord> getMedicalRecordsByPatientId(int patientId) {
        if(!patientService.existsById(patientId)){
            throw new IllegalArgumentException("Patient Not Found");
        }

       return medicalRecordRepository.findMedicalRecordsByAppointment_Patient_PatientId(patientId);
    }

    public MedicalRecord updateMedicalRecord(int id, MedicalRecord medicalRecord) {
        if(!medicalRecordRepository.existsById(id)){
            throw new IllegalArgumentException("Medical Record Not Found");
        }
        MedicalRecord currentMedicalRecord  = getMedicalRecordById(id);

        currentMedicalRecord.setDate(medicalRecord.getDate());
        currentMedicalRecord.setDiagnosis(medicalRecord.getDiagnosis());
        currentMedicalRecord.setNotes(medicalRecord.getNotes());
        currentMedicalRecord.setSymptoms(medicalRecord.getSymptoms());
        currentMedicalRecord.setVitals(medicalRecord.getVitals());

        return medicalRecordRepository.save(currentMedicalRecord);
    }

    public void deleteMedicalRecord(int id) {
        if(!medicalRecordRepository.existsById(id)){
            throw new IllegalArgumentException("Medical Record Not Found");
        }
        medicalRecordRepository.deleteById(id);
    }



}
