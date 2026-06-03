package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.Appointment;
import com.ClinicManagment.demo.Entity.Patient;
import com.ClinicManagment.demo.Repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientService patientService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
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
        if(!patientService.existsById(patientId)) {
            throw new RuntimeException("No appointment found with id " + patientId);
        }
        Patient currentPatient = patientService.getPatientById(patientId);

        return appointmentRepository.getAppointmentsByPatient(currentPatient);
    }

}
