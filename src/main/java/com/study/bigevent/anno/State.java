package com.study.bigevent.anno;

import com.study.bigevent.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(
        validatedBy = {StateValidation.class}//指定校验规则
)
@Target({FIELD})
@Retention(RUNTIME)
public @interface State {

    String message() default "只能填写已发布或者是草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
