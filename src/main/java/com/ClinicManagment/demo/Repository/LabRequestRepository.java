package com.ClinicManagment.demo.Repository;

import com.ClinicManagment.demo.Entity.LabRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRequestRepository extends JpaRepository<LabRequest, Integer> {
}
