package net.system.mk.commons.anno;


import net.system.mk.commons.enums.CtxScope;

import java.lang.annotation.*;

/**
 * 强制注入MerchantId
 *
 * @author USER
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MerchantInject {

    int value() default -1;

    boolean platformPassed() default true;

    boolean fromContext() default true;

    CtxScope scope() default CtxScope.backend;

}
