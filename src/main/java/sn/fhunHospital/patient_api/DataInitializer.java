package sn.fhunHospital.patient_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sn.fhunHospital.patient_api.model.Role;
import sn.fhunHospital.patient_api.repository.RoleRepository;
import sn.fhunHospital.patient_api.repository.UserRepository;
import sn.fhunHospital.patient_api.service.security.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        // Vérifier si des rôles existent déjà
        if (roleRepository.count() == 0) {
            logger.info("Initialisation des rôles...");
            roleRepository.saveAll(Arrays.asList(
                    new Role("ROLE_USER"),
                    new Role("ROLE_ADMIN")
            ));
        }

        // Vérifier si des utilisateurs existent déjà
        if (userRepository.count() == 0) {
            logger.info("Initialisation des utilisateurs...");

            // Créer un utilisateur admin
            userService.createUser(
                    "admin",
                    "password123",
                    new HashSet<>(Collections.singletonList("ROLE_ADMIN"))
            );

            // Créer un utilisateur standard
            userService.createUser(
                    "user",
                    "password123",
                    new HashSet<>(Collections.singletonList("ROLE_USER"))
            );

            logger.info("Données initialisées avec succès.");
        }
    }
}