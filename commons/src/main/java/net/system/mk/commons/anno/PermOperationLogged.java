package net.system.mk.commons.anno;

import java.lang.annotation.*;

/**
 * @author USER
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermOperationLogged {

    boolean detail() default false;

}
