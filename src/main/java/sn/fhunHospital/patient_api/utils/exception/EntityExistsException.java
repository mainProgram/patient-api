package sn.fhunHospital.patient_api.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntityExistsException extends RuntimeException{
    private String message;
}
