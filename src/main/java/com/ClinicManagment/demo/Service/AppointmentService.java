package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.Appointment;
import com.ClinicManagment.demo.Entity.Patient;
import com.ClinicManagment.demo.Repository.AppointmentRepository;
import com.ClinicManagment.demo.Repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(int id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No appointment found with id " + id));
    }

    public Appointment updateAppointment(int id, Appointment appointment) {

        if(!appointmentRepository.existsById(id)) {
            throw new RuntimeException("No appointment found with id " + id);
        }
        Appointment currentAppointment = getAppointmentById(id);

        currentAppointment.setDate(appointment.getDate());
        currentAppointment.setTime(appointment.getTime());
        return appointmentRepository.save(currentAppointment);

    }

    public void cancelAppointment(int id) {
        if(!appointmentRepository.existsById(id)) {
            throw new RuntimeException("No appointment found with id " + id);
        }
        appointmentRepository.deleteById(id);
    }

    public Optional<List<Appointment>> getAppointmentsByPatientId(int patientId) {
        if(!patientRepository.existsById(patientId)) {
            throw new RuntimeException("No appointment found with id " + patientId);
        }
        if(patientRepository.findById(patientId).isEmpty()) {
            throw new RuntimeException("No appointment found with id " + patientId);
        }
        Patient currentPatient = patientRepository.findById(patientId).get();

        return appointmentRepository.getAppointmentsByPatient(currentPatient);
    }

}
