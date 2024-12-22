package sn.fhunHospital.patient_api.service;

import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    PatientResponse savePatient(PatientRequest productDto);
    List<PatientResponse> getAllPatients();
    Optional<PatientResponse> getPatientById(String ref);
    boolean deletePatient(String ref);
    Optional<PatientResponse> updatePatient(String id, PatientRequest productDto);
}
