package sn.fhunHospital.patient_api.dto.requests;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import sn.fhunHospital.patient_api.utils.enums.SexeEnum;
import sn.fhunHospital.patient_api.utils.enums.SexeEnumConstraint;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private LocalDate dateNaissance;

    @NotNull
    @SexeEnumConstraint
    private SexeEnum sexe;

    @NotNull
    private Double taille;

    @NotNull
    private Double poids;

    @NotEmpty
    private List<ContactRequest> contacts;
}
