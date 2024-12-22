package sn.fhunHospital.patient_api.mapper;

import org.springframework.stereotype.Component;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.model.PatientEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper {

    private PatientMapper() {

    }

    public static PatientEntity mapRequestToEntity(PatientRequest patientRequest) {

        PatientEntity patientEntity = PatientEntity
                .builder()
                .nom(patientRequest.getNom())
                .prenom(patientRequest.getPrenom())
                .sexe(patientRequest.getSexe())
                .dateNaissance(patientRequest.getDateNaissance())
                .taille(patientRequest.getTaille())
                .poids(patientRequest.getPoids())
                .contacts(ContactMapper.mapRequestToEntities(patientRequest.getContacts()))
                .build();

        return patientEntity;
    }

    public static PatientResponse mapEntityToResponse(PatientEntity patientEntity) {

        PatientResponse patientResponse = PatientResponse
                .builder()
                .prenom(patientEntity.getPrenom())
                .nom(patientEntity.getNom())
                .taille(patientEntity.getTaille())
                .poids(patientEntity.getPoids())
                .sexe(patientEntity.getSexe())
                .contacts(ContactMapper.mapEntitiesToResponses(patientEntity.getContacts()))
                .build();

        return patientResponse;
    }

    public static List<PatientResponse> mapEntitiesToResponses(List<PatientEntity> patientEntities) {
        return patientEntities.stream()
                .map(PatientMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public static List<PatientEntity> mapRequestToEntities(List<PatientRequest> patientRequests) {
        return patientRequests.stream()
                .map(PatientMapper::mapRequestToEntity)
                .collect(Collectors.toList());
    }
}
