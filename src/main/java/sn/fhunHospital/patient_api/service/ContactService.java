package sn.fhunHospital.patient_api.service;

import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.responses.ContactResponse;

public interface ContactService {

    ContactResponse saveContact(ContactRequest contactRequest);
}
