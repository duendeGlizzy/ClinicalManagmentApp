package com.ClinicManagment.demo.Service;

import com.ClinicManagment.demo.Entity.LabRequest;
import com.ClinicManagment.demo.Enum.LabStatus;
import com.ClinicManagment.demo.Repository.LabRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabRequestService {

    private final LabRequestRepository labRequestRepository;
    private final LabTestService labTestService;

    public LabRequestService(LabRequestRepository labRequestRepository, LabTestService labTestService) {
        this.labRequestRepository = labRequestRepository;
        this.labTestService = labTestService;
    }

    public LabRequest saveLabRequest(LabRequest labRequest) {
        return labRequestRepository.save(labRequest);
    }
    @Transactional(readOnly = true)
    public List<LabRequest> getAllLabRequests() {
        return labRequestRepository.findAll();
    }
    @Transactional(readOnly = true)
    public LabRequest getLabRequestById(int id) {
        return labRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lab request not found"));
    }
    @Transactional
    public void deleteLabRequestById(int id) {
        labRequestRepository.deleteById(id);
    }

    @Transactional
    public LabRequest updateLabRequestStatus(int id, LabStatus status) {
        if(!labRequestRepository.existsById(id)) {
            throw new IllegalArgumentException("Lab request not found");
        }
        LabRequest currentLabRequest = labRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lab request not found"));

        currentLabRequest.setLabStatus(status);
        return labRequestRepository.save(currentLabRequest);
    }


}
