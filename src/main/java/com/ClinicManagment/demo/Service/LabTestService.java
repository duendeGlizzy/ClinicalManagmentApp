package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.LabTest;
import com.ClinicManagment.demo.Repository.LabTestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabTestService {

    private final LabTestRepository labTestRepository;

    public LabTestService(LabTestRepository labTestRepository) {
        this.labTestRepository = labTestRepository;
    }

    public LabTest save(LabTest labTest) {
        return labTestRepository.save(labTest);
    }
    @Transactional(readOnly = true)
    public List<LabTest> findAll() {
        return labTestRepository.findAll();
    }
    @Transactional(readOnly = true)
    public LabTest findById(int id) {
        return labTestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("LabTest not found"));
    }


    public void delete(int id) {
        if(!labTestRepository.existsById(id)) {
            throw new IllegalArgumentException("LabTest not found");
        }
        labTestRepository.deleteById(id);
    }


}
