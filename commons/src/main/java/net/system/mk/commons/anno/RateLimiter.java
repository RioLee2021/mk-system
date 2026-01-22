package net.system.mk.commons.anno;





import net.system.mk.commons.meta.LimitType;

import java.lang.annotation.*;

/**
 * @author USER
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    String key() default "global:rate:limit";

    int time() default 60;

    int count() default 100;

    LimitType type() default LimitType.DEFAULT;
}
