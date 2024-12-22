package sn.fhunHospital.patient_api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.responses.ContactResponse;
import sn.fhunHospital.patient_api.mapper.ContactMapper;
import sn.fhunHospital.patient_api.repository.ContactRepository;
import sn.fhunHospital.patient_api.service.ContactService;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Override
    public ContactResponse saveContact(ContactRequest contactRequest) {
        return ContactMapper.mapEntityToResponse(contactRepository.save(ContactMapper.mapRequestToEntity(contactRequest)));
    }
}
