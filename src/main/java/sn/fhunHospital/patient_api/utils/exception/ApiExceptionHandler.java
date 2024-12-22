package sn.fhunHospital.patient_api.utils.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException e) {
        ApiException exception = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {EntityExistsException.class})
    public ResponseEntity<ApiException> handleEntityExistException(EntityExistsException e) {
        ApiException exception = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

}
