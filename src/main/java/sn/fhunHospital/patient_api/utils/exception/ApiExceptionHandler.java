package sn.fhunHospital.patient_api.utils.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleGeneralException(Exception ex) {
        return ResponseUtil.error(Arrays.asList(ex.getMessage()), "An unexpected error occurred", 1001);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiResponse<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseUtil.error(Arrays.asList(ex.getMessage()), "Resource not found", 404);
    }
}
