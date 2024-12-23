package sn.fhunHospital.patient_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.responses.ContactResponse;
import sn.fhunHospital.patient_api.mapper.ContactMapper;
import sn.fhunHospital.patient_api.model.ContactEntity;
import sn.fhunHospital.patient_api.repository.ContactRepository;
import sn.fhunHospital.patient_api.service.ContactService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public ContactResponse saveContact(ContactRequest contactRequest) {
        return ContactMapper.mapEntityToResponse(contactRepository.save(ContactMapper.mapRequestToEntity(contactRequest)));
    }

    @Override
    public List<ContactEntity> saveContacts(List<ContactRequest> contactRequest) {
        return contactRepository.saveAll(ContactMapper.mapRequestToEntities(contactRequest));
    }

    @Override
    public List<ContactEntity> saveContactsEntities(List<ContactEntity> contactEntity) {
        return contactRepository.saveAll(contactEntity);
    }

    @Override
    public void deleteContacts(List<ContactEntity> contactEntity) {
        contactRepository.deleteAll(contactEntity);
    }
}
