package sn.fhunHospital.patient_api.service;

import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.dto.responses.PatientWithContactResponse;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    PatientResponse savePatient(PatientRequest productDto);
    List<PatientResponse> getAllPatients();
    Optional<PatientWithContactResponse> getPatientById(String id);
    boolean deletePatient(String id);
    Optional<PatientWithContactResponse> updatePatient(String id, PatientRequest patientRequest);
}
