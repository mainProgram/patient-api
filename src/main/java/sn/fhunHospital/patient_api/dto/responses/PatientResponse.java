package sn.fhunHospital.patient_api.dto.responses;

import lombok.*;
import sn.fhunHospital.patient_api.utils.enums.SexeEnum;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private String id;

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private SexeEnum sexe;
}
