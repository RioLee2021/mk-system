package net.system.mk.commons.redis.lock;


import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author USER
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DistributedLockAspect.class})
public @interface EnableDistributedLock {
}
