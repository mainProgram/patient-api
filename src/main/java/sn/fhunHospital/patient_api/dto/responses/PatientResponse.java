package sn.fhunHospital.patient_api.dto.responses;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.utils.enums.SexeEnum;
import sn.fhunHospital.patient_api.utils.enums.SexeEnumConstraint;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private ObjectId id;

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private SexeEnum sexe;

    private Double taille;

    private Double poids;

    private List<ContactResponse> contacts;
}
