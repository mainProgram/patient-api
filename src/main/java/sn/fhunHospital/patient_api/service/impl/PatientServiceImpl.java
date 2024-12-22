package sn.fhunHospital.patient_api.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.mapper.PatientMapper;
import sn.fhunHospital.patient_api.model.PatientEntity;
import sn.fhunHospital.patient_api.repository.PatientRepository;
import sn.fhunHospital.patient_api.service.PatientService;
import sn.fhunHospital.patient_api.utils.exception.UpdateFailedException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponse savePatient(PatientRequest patientRequest) {
        return PatientMapper.mapEntityToResponse(patientRepository.save(PatientMapper.mapRequestToEntity(patientRequest)));
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return PatientMapper.mapEntitiesToResponses(patientRepository.findAll());
    }

    @Override
    public Optional<PatientResponse> getPatientById(String id) {
        return Optional.ofNullable(patientRepository.findById(id)
                .map(PatientMapper::mapEntityToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Le patient avec l'ID " + id + " n'existe pas.")));
    }

    @Override
    public boolean deletePatient(String id) {
        try {
            patientRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Le patient avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public Optional<PatientResponse> updatePatient(String id, PatientRequest patientRequest) {
        return Optional.ofNullable(patientRepository.findById(id)
                .map(patient -> {
                    try {
                        // Mettre à jour le patient
                        PatientEntity updatedPatient = patientRepository.save(patient);
                        return PatientMapper.mapEntityToResponse(updatedPatient);
                    } catch (Exception e) {
                        throw new UpdateFailedException("La mise à jour du patient a échoué : " + e.getMessage());
                    }
                })
                .orElseThrow(() -> new EntityNotFoundException("Le patient avec l'ID " + id + " n'existe pas.")));
    }
}
