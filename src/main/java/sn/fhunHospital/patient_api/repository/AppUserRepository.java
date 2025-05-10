package sn.fhunHospital.patient_api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.fhunHospital.patient_api.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findByUsername(String username);
    Boolean existsByUsername(String username);
}
