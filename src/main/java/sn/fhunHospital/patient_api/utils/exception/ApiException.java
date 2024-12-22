package sn.fhunHospital.patient_api.utils.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ApiException {
    private String message;
    private int code;
    private LocalDateTime timestamp;
    public ApiException(String message, int code) {
        this.message = message;
        this.code = code;
        this.timestamp = LocalDateTime.now(); // Génère l'heure actuelle
    }
}
