package com.ClinicManagment.demo.Repository;

import com.ClinicManagment.demo.Entity.Appointment;
import com.ClinicManagment.demo.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
   Optional<List<Appointment>> getAppointmentsByPatient(Patient patient);
}
