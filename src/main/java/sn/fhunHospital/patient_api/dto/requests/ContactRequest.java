package sn.fhunHospital.patient_api.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnum;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnumConstraint;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    private String id;

    @NotNull
    private String contact;

    @NotNull
    @TypeContactEnumConstraint
    private TypeContactEnum type;
}
