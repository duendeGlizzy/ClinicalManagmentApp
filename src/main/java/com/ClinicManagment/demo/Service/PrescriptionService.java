package com.ClinicManagment.demo.Service;


import com.ClinicManagment.demo.Entity.Pharmacy;
import com.ClinicManagment.demo.Entity.Prescription;
import com.ClinicManagment.demo.Repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PharmacyService pharmacyService;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               PharmacyService pharmacyService) {
        this.prescriptionRepository = prescriptionRepository;
        this.pharmacyService = pharmacyService;
    }

    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    public Prescription findById(int id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    public Prescription save(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public void delete(int id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Prescription not found");
        }
        prescriptionRepository.deleteById(id);
    }

    public Prescription update(int id, int pharmacyId, Prescription prescription) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Prescription not found");
        }
        Prescription currentPrescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        Pharmacy currentPharmacy = pharmacyService.getPharmacyById(pharmacyId);

        currentPrescription.setDose(prescription.getDose());
        currentPrescription.setPatient(prescription.getPatient());
        currentPrescription.setName(prescription.getName());
        currentPrescription.setProvider(prescription.getProvider());
        currentPrescription.setRefills(prescription.getRefills());
        currentPrescription.setRxNumber(prescription.getRxNumber());
        currentPrescription.setDatePrescribed(prescription.getDatePrescribed());
        currentPrescription.setPharmacy(currentPharmacy);


        return prescriptionRepository.save(currentPrescription);

    }

    public List<Prescription> findByPatientId(int patientId) {

        return prescriptionRepository.findByPatient_PatientId(patientId);
    }





}

