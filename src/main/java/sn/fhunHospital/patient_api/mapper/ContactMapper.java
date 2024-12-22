package sn.fhunHospital.patient_api.mapper;

import org.springframework.stereotype.Component;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.responses.ContactResponse;
import sn.fhunHospital.patient_api.model.ContactEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactMapper {

    private ContactMapper() {
    }

    public static ContactEntity mapRequestToEntity(ContactRequest contactRequest) {

        ContactEntity contactEntity = ContactEntity
                .builder()
                .contact(contactRequest.getContact())
                .type(contactRequest.getType())
                .build();
        return contactEntity;
    }

    public static List<ContactEntity> mapRequestToEntities(List<ContactRequest> contactRequests) {
        return contactRequests.stream()
                .map(ContactMapper::mapRequestToEntity)
                .collect(Collectors.toList());
    }

    public static ContactResponse mapEntityToResponse(ContactEntity contactEntity) {
        ContactResponse contactResponse = ContactResponse
                .builder()
                .id(contactEntity.getId())
                .contact(contactEntity.getContact())
                .type(contactEntity.getType().toString())
                .build();
        return contactResponse;
    }

    public static List<ContactResponse> mapEntitiesToResponses(List<ContactEntity> contactEntities) {
        return contactEntities.stream()
                .map(ContactMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }
}
