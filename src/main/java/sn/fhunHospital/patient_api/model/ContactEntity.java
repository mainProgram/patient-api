package sn.fhunHospital.patient_api.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnum;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnumConstraint;

@Data
@Builder
@Document(collection = "contact")
public class ContactEntity {

    @Id
    private String id;

    @NotNull
    private String contact;

    @NotNull
    @TypeContactEnumConstraint
    private TypeContactEnum type;
}
