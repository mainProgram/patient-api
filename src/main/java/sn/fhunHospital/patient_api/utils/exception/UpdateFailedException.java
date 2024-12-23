package sn.fhunHospital.patient_api.utils.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFailedException extends RuntimeException{
    public UpdateFailedException(String message) {
        super(message);
    }
}
