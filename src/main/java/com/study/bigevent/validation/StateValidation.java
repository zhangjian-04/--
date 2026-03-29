package com.study.bigevent.validation;

import com.study.bigevent.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {
    /**
     *
     * @param s 将要校验的数据
     * @param constraintValidatorContext
     * @return true表示校验通过 ，false表示校验不通过
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
       if(s == null){
           return false;
       }
       if(s.equals("已发布") || s.equals("草稿")){
           return true;
       }
        return false;
    }
}
