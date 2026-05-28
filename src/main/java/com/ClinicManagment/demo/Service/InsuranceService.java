package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.Insurance;
import com.ClinicManagment.demo.Repository.InsuranceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;

    public InsuranceService(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }

    public List<Insurance> getAllInsurance() {
        return insuranceRepository.findAll();
    }

    public Insurance getInsuranceById(int id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insurance not found"));
    }

    public Insurance saveInsurance(Insurance insurance) {
        return insuranceRepository.save(insurance);
    }

    public Insurance updateInsurance(int id,Insurance insurance) {
        Insurance currentInsurance = getInsuranceById(id);

        currentInsurance.setInsuranceId(insurance.getInsuranceId());
        currentInsurance.setPatients(insurance.getPatients());
        currentInsurance.setCompanyName(insurance.getCompanyName());
        currentInsurance.setPolicyNumber(insurance.getPolicyNumber());

        return insuranceRepository.save(currentInsurance);
    }

    public void deleteInsurance(int id) {
        if(!insuranceRepository.existsById(id)) {
            throw new RuntimeException("Insurance not found");
        }
        insuranceRepository.deleteById(id);
    }



}
