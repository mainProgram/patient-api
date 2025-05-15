package sn.fhunHospital.patient_api.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sn.fhunHospital.patient_api.utils.enums.SexeEnum;
import sn.fhunHospital.patient_api.utils.enums.SexeEnumConstraint;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patient")
public class PatientEntity {

    @Id
    private String id;

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

    @DBRef
    private List<ContactEntity> contacts;
}
