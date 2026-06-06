package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.Vitals;
import com.ClinicManagment.demo.Repository.VitalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VitalsService {

    private final VitalsRepository vitalsRepository;

    public VitalsService(VitalsRepository vitalsRepository) {
        this.vitalsRepository = vitalsRepository;
    }

    public List<Vitals> getAllVitals() {
        return vitalsRepository.findAll();
    }

    public Vitals getVitalById(int id) {
        return vitalsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vitals not found"));
    }

    public Vitals addVitals(Vitals vitals) {
        return vitalsRepository.save(vitals);
    }

    public Vitals updateVitals(int id, Vitals vitals) {
        if(!vitalsRepository.existsById(id)) {
            throw new RuntimeException("Vitals not found");
        }
        Vitals currentVitals = getVitalById(id);
        currentVitals.setDiastolicBP(vitals.getDiastolicBP());
        currentVitals.setHeight(vitals.getHeight());
        currentVitals.setWeight(vitals.getWeight());
        currentVitals.setHeartRate(vitals.getHeartRate());
        currentVitals.setOxygenSaturation(vitals.getOxygenSaturation());
        currentVitals.setSystolicBP(vitals.getSystolicBP());

        return vitalsRepository.save(currentVitals);

    }



}
