package com.ClinicManagment.demo.Repository;

import com.ClinicManagment.demo.Entity.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabTestRepository  extends JpaRepository<LabTest, Integer> {
}
