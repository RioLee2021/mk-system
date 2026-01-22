package net.system.mk.commons.meta;



import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

/**
 * @author USER
 * @date 2025-04-2025/4/17/0017 0:57
 */
public abstract class BaseValidationBean {

    public final void validate() {
        Set<ConstraintViolation<BaseValidationBean>> set = Validation.buildDefaultValidatorFactory().getValidator().validate(this);
        if (!set.isEmpty()){
            String msg = set.stream().map(ConstraintViolation::getMessage).reduce((m1,m2)->m1+"; "+m2)
                    .orElse("未知错误");
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, msg);
        }
    }

}
