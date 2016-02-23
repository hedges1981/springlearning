package com.hedges.jpalearning.advancedchapters.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by rowland-hall on 23/02/16
 */
public class EvenNumberValidator implements ConstraintValidator<Even,Integer>
{
    boolean includeZero;

    @Override
    public void initialize( Even even )
    {
        //NOTE: how this is initialised by fetching one of the properties of the annotation
        this.includeZero = even.includeZero();
    }

    @Override
    public boolean isValid( Integer value, ConstraintValidatorContext constraintValidatorContext )
    {
        if (value == null)
            return true;
        if (value == 0)
            return includeZero;
        return value % 2 == 0;
    }
}
