package sn.fhunHospital.patient_api.utils.enums;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TypeContactEnumValidator.class)
public @interface TypeContactEnumConstraint {
    String message() default "Le type doit être 'FIXE' ou 'MOBILE' ou 'EMAIL'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
