package com.hibernate.practice.interceptor;

import com.hibernate.practice.annotation.ValidCandidate;
import com.hibernate.practice.util.ValidatorUtils;
import jakarta.validation.ConstraintViolationException;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class ValidateInterceptor {

    @RuntimeType
    public void intercept(@AllArguments Object[] args) throws ConstraintViolationException {
        for (Object arg : args) {
            Class<?> clazz = arg.getClass();
            if (clazz.isAnnotationPresent(ValidCandidate.class)) {
                ValidatorUtils.validate(arg);
            }
        }
    }
}
