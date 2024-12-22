package sn.fhunHospital.patient_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sn.fhunHospital.patient_api.model.ContactEntity;

@Repository
public interface ContactRepository  extends MongoRepository<ContactEntity, String> {
}
