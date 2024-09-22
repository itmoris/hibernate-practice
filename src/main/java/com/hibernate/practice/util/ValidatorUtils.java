package com.hibernate.practice.util;

import jakarta.validation.*;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.Set;

@UtilityClass
public class ValidatorUtils {
    private static final Validator validator;

    static {
        @Cleanup ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public static void validate(Object object, Class<?>... groups) throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(object, groups);
        Optional<ConstraintViolation<Object>> constraintViolation = constraintViolationSet.stream().findFirst();

        if (constraintViolation.isPresent()) {
            throw new ConstraintViolationException(Set.of(constraintViolation.get()));
        }
    }
}
