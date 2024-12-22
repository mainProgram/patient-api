package sn.fhunHospital.patient_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnum;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnumConstraint;

@Data
@Builder
@Document(collection = "contact")
public class ContactEntity {

    private ObjectId id;

    @NotNull
    private String contact;

    @NotNull
    @TypeContactEnumConstraint
    private TypeContactEnum type;
}
