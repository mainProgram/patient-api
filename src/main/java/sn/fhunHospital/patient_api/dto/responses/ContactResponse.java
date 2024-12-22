package sn.fhunHospital.patient_api.dto.responses;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    private String type;

    private String contact;
}
