package sn.fhunHospital.patient_api.service;

import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.model.ContactEntity;

import java.util.List;

public interface ContactService {

    List<ContactEntity> saveContacts(List<ContactRequest> contactRequest);

    List<ContactEntity> saveContactsEntities(List<ContactEntity> contactEntity);

   void deleteContacts(List<ContactEntity> contactEntity);
}
