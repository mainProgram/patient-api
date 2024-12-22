package sn.fhunHospital.patient_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sn.fhunHospital.patient_api.model.PatientEntity;

@Repository
public interface PatientRepository extends MongoRepository<PatientEntity, String> {
}
