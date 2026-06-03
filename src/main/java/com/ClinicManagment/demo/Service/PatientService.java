package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.*;
import com.ClinicManagment.demo.Repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final InsuranceService insuranceService;
    private final PrescriptionService prescriptionService;
    private final PharmacyService pharmacyService;
    private final AppointmentService appointmentService;

    public PatientService(PatientRepository patientRepository,
                          InsuranceService insuranceService,
                          PrescriptionService prescriptionService,
                          PharmacyService pharmacyService,
                          AppointmentService appointmentService) {

        this.pharmacyService = pharmacyService;
        this.patientRepository = patientRepository;
        this.insuranceService=  insuranceService;
        this.prescriptionService = prescriptionService;
        this.appointmentService = appointmentService;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(int id) {
        return patientRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Patient not found"));
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(int id, Patient updatedPatient) {
        if(updatedPatient == null){
            throw new IllegalArgumentException("Patient details cannot be null");
        }
        Patient currentPatient = patientRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Patient not found"));

        currentPatient.setFirstName(updatedPatient.getFirstName());
        currentPatient.setLastName(updatedPatient.getLastName());
        currentPatient.setGender(updatedPatient.getGender());
        currentPatient.setAddress(updatedPatient.getAddress());
        currentPatient.setDateOfBirth(updatedPatient.getDateOfBirth());
        currentPatient.setInsurance(updatedPatient.getInsurance());

        return patientRepository.save(currentPatient);

    }

    public void deletePatient(int id) {
        if(!patientRepository.existsById(id)){
            throw new IllegalArgumentException("Patient not found");
        }
        patientRepository.deleteById(id);
    }


    //Register New Patient
    @Transactional
    public Patient registerNewPatient(Patient patient) {
        if(patientRepository.existsByEmail(patient.getEmail())){
            throw new IllegalArgumentException("Patient already exists");
        }


        return patientRepository.save(patient);
    }


    //Search Patients
    @Transactional(readOnly = true)
    public List<Patient> searchPatientByLastName(String lastName) {
        if(lastName == null || lastName.isEmpty()){
            return patientRepository.findAll();
        }
        return patientRepository.findByLastNameContainingIgnoreCase(lastName.trim());
    }

    @Transactional(readOnly = true)
    public List<Patient> findPatientByBirthDate(Date dob){
        return patientRepository.findByDateOfBirth(dob);
    }


    //INSURANCE LOGIC

    @Transactional(readOnly = true)
    public Insurance getPatientInsurance(int id) {

        Patient currentPatient = patientRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Patient not found"));

        return currentPatient.getInsurance();
    }

    @Transactional
    public Patient assignInsuranceToPatient(int patientId, int insuranceId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found"));

        Insurance insurance = insuranceService.getInsuranceById(insuranceId);

        patient.setInsurance(insurance);
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient removeInsuranceFromPatient(int patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found"));

        if(patient.getInsurance() == null){
            throw new IllegalArgumentException("Patient does not have insurance");
        }
        patient.setInsurance(null);
        return patientRepository.save(patient);
    }

    //Prescription Logic

    @Transactional(readOnly = true)
    public List<Prescription> getPatientPrescriptions(int patientId) {
        if(!patientRepository.existsById(patientId)){
            throw new IllegalArgumentException("Patient not found");
        }
        return prescriptionService.findByPatientId(patientId);
    }

    @Transactional
    public Prescription assignPrescriptionToPatient(int patientId, int pharmacyId, Prescription newPrescription) {
        if(!patientRepository.existsById(patientId)&& prescriptionService.findByPatientId(patientId) == null){
            throw new IllegalArgumentException("Patient not found or Prescription not found");
        }
        Patient currentPatient = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found"));

        Pharmacy currentPharmacy = pharmacyService.getPharmacyById(pharmacyId);

        newPrescription.setPatient(currentPatient);
        newPrescription.setPharmacy(currentPharmacy);

        return prescriptionService.save(newPrescription);

    }

    @Transactional
    public void removePrescriptionFromPatient(int patientId, int prescriptionId) {

        if(!patientRepository.existsById(patientId) || prescriptionService.findById(prescriptionId) == null){
            throw new IllegalArgumentException("Patient not found or Prescription not found");
        }
        Patient currentPatient = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found"));
        Prescription currentPrescription = prescriptionService.findById(prescriptionId);

        currentPrescription.setPatient(null);
        prescriptionService.save(currentPrescription);

    }

    public Prescription fulfillPrescriptionRefill(int patientId, int prescriptionId) {

        if(!patientRepository.existsById(patientId)){
            throw new IllegalArgumentException("Patient not found");
        }
        Prescription currentPrescription = prescriptionService.findById(prescriptionId);

        if(currentPrescription.getPatient() == null || currentPrescription.getPatient().getPatientId() != patientId){
            throw new IllegalArgumentException("Patient does not have prescription");
        }

        if(currentPrescription.getRefills() <= 0){
            throw new IllegalArgumentException("No refills remaining");
        }

        currentPrescription.setRefills(currentPrescription.getRefills() - 1);
        return prescriptionService.save(currentPrescription);
    }

    //Appointment Logic

    public Optional<List<Appointment>> getPatientAppointments(int patientId) {
        if(!patientRepository.existsById(patientId)){
            throw new IllegalArgumentException("Patient not found");
        }
        return appointmentService.getAppointmentsByPatientId(patientId);
    }


    public boolean existsById(int patientId) {
        return patientRepository.existsById(patientId);
    }
}
