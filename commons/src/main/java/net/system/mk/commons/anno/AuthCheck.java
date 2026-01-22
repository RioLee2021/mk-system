package net.system.mk.commons.anno;



import net.system.mk.commons.enums.CtxScope;

import java.lang.annotation.*;

/**
 * @author USER
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthCheck {

    boolean root() default false;

    CtxScope ctxScope() default CtxScope.backend;
}
