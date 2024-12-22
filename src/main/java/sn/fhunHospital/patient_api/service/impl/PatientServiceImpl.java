package sn.fhunHospital.patient_api.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.dto.responses.PatientWithContactResponse;
import sn.fhunHospital.patient_api.mapper.PatientMapper;
import sn.fhunHospital.patient_api.model.ContactEntity;
import sn.fhunHospital.patient_api.model.PatientEntity;
import sn.fhunHospital.patient_api.repository.PatientRepository;
import sn.fhunHospital.patient_api.service.ContactService;
import sn.fhunHospital.patient_api.service.PatientService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ContactService contactService;

    @Override
    public PatientResponse savePatient(PatientRequest patientRequest) {
        List<ContactEntity> contactEntities = contactService.saveContacts(patientRequest.getContacts());
        PatientEntity patientEntity = PatientMapper.mapRequestToEntity(patientRequest);
        patientEntity.setContacts(contactEntities);
        return PatientMapper.mapEntityToResponse(patientRepository.save(patientEntity));
    }

    @Override
    public List<PatientResponse> getAllPatients() {
        return PatientMapper.mapEntitiesToResponses(patientRepository.findAll());
    }

    @Override
    public Optional<PatientWithContactResponse> getPatientById(String id) {
        return Optional.ofNullable(patientRepository.findById(id)
                .map(PatientMapper::mapEntityToResponseWithContacts)
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
    public Optional<PatientWithContactResponse> updatePatient(String id, PatientRequest patientRequest) {
        return Optional.ofNullable(patientRepository.findById(id)
                .map(Patient -> {
                    Patient.setNom(patientRequest.getNom());
                    Patient.setPrenom(patientRequest.getPrenom());
                    Patient.setDateNaissance(patientRequest.getDateNaissance());
                    Patient.setSexe(patientRequest.getSexe());
                    Patient.setTaille(patientRequest.getTaille());
                    Patient.setPoids(patientRequest.getPoids());
                    Patient.setContacts(contactService.saveContacts(patientRequest.getContacts()));
                    PatientEntity updatedPatient = patientRepository.save(Patient);
                    return PatientMapper.mapEntityToResponseWithContacts(updatedPatient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Le patient avec l'ID " + id + " n'existe pas.")));
    }

}
