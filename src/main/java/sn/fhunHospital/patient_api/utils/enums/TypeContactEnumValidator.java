package sn.fhunHospital.patient_api.utils.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TypeContactEnumValidator implements ConstraintValidator<TypeContactEnumConstraint, TypeContactEnum> {

    @Override
    public boolean isValid(TypeContactEnum sexeEnum, ConstraintValidatorContext context) {
        return sexeEnum != null && (sexeEnum == TypeContactEnum.FIXE || sexeEnum == TypeContactEnum.MOBILE || sexeEnum == TypeContactEnum.EMAIL);
    }
}
