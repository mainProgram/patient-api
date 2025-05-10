package sn.fhunHospital.patient_api.service.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.fhunHospital.patient_api.model.Role;
import sn.fhunHospital.patient_api.model.User;
import sn.fhunHospital.patient_api.repository.RoleRepository;
import sn.fhunHospital.patient_api.repository.UserRepository;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Méthode pour créer un nouvel utilisateur
    public User createUser(String username, String password, Set<String> roleNames) {
        User user = new User(
                username,
                passwordEncoder.encode(password)
        );

        // Assurez-vous que les rôles existent dans la collection des rôles
        roleNames.forEach(roleName -> {
            getOrCreateRole(roleName);
            user.addRole(roleName);
        });

        return userRepository.save(user);
    }

    // Récupérer un rôle existant ou en créer un nouveau
    private Role getOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

    // Vérifier si un username existe déjà
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}