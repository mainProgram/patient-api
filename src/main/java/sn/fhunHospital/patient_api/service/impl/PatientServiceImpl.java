package sn.fhunHospital.patient_api.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.dto.responses.PatientWithContactResponse;
import sn.fhunHospital.patient_api.mapper.PatientMapper;
import sn.fhunHospital.patient_api.model.ContactEntity;
import sn.fhunHospital.patient_api.model.PatientEntity;
import sn.fhunHospital.patient_api.repository.PatientRepository;
import sn.fhunHospital.patient_api.service.ContactService;
import sn.fhunHospital.patient_api.service.PatientService;

import java.util.ArrayList;
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
        Optional<PatientEntity> patientEntity;
        try {
            patientEntity = patientRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Le patient avec l'ID " + id + " n'existe pas.");
        }
        if (patientEntity.isPresent()){
            List<ContactEntity> contacts = patientEntity.get().getContacts();
            if (contacts != null && !contacts.isEmpty()) {
                contactService.deleteContacts(contacts);
            }
            patientRepository.deleteById(id);
            return true;
        }
        return false;
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

    @Override
    public Optional<PatientWithContactResponse> addOrUpdateContacts(String patientId, List<ContactRequest> contactRequests) {
        return Optional.ofNullable(patientRepository.findById(patientId)
                .map(patient -> {
                    List<ContactEntity> contacts = patient.getContacts();
                    if (contacts == null) {
                        contacts = new ArrayList<>();
                    }

                    for (final ContactRequest contactRequest : contactRequests) {
                        Optional<ContactEntity> existingContact = contacts.stream()
                                .filter(contact -> contact.getId() != null && contact.getId().equals(contactRequest.getId()))
                                .findFirst();

                        if (existingContact.isPresent()) {
                            ContactEntity contact = existingContact.get();
                            contact.setType(contactRequest.getType());
                            contact.setContact(contactRequest.getContact());
                        } else {
                            ContactEntity newContact = ContactEntity
                                    .builder()
                                    .contact(contactRequest.getContact())
                                    .type(contactRequest.getType())
                                    .build();
                            contacts.add(newContact);
                        }
                    }

                    contactService.saveContactsEntities(contacts);
                    patient.setContacts(contacts);
                    PatientEntity updatedPatient = patientRepository.save(patient);
                    return PatientMapper.mapEntityToResponseWithContacts(updatedPatient);
                })
                .orElseThrow(() -> new EntityNotFoundException("Le patient avec l'ID " + patientId + " n'existe pas.")));
    }

    @Override
    public Optional<PatientWithContactResponse> deleteContact(String patientId, String contactId) {
        return Optional.ofNullable(patientRepository.findById(patientId)
                .map(patient -> {
                    List<ContactEntity> contacts = patient.getContacts();
                    if (contacts != null) {
                        boolean removed = contacts.removeIf(contact -> contact.getId().equals(contactId));
                        if (!removed) {
                            throw new EntityNotFoundException("Le contact avec l'ID " + contactId + " n'existe pas.");
                        }

                        patient.setContacts(contacts);
                        PatientEntity updatedPatient = patientRepository.save(patient);
                        return PatientMapper.mapEntityToResponseWithContacts(updatedPatient);
                    } else {
                        throw new EntityNotFoundException("Le patient avec l'ID " + patientId + " n'a pas aucun contact.");
                    }
                })
                .orElseThrow(() -> new EntityNotFoundException("Le patient avec l'ID " + patientId + " n'existe pas.")));
    }

}
