package sn.fhunHospital.patient_api.utils.enums;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SexeEnumValidator.class)
public @interface SexeEnumConstraint {
    String message() default "Le sexe doit être 'FEMME' ou 'HOMME'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
