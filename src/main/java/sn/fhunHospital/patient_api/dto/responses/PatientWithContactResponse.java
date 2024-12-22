package sn.fhunHospital.patient_api.dto.responses;

import lombok.*;
import sn.fhunHospital.patient_api.utils.enums.SexeEnum;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientWithContactResponse {

    private String id;

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private SexeEnum sexe;

    private Double taille;

    private Double poids;

    private List<ContactResponse> contacts;
}
