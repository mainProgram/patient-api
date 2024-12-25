package sn.fhunHospital.patient_api.utils.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;

    private String message;

    private T data;

    private List<String> errors;

    private int errorCode;

    private long timestamp;
}
