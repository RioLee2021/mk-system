package net.system.mk.commons.anno.menu;

import java.lang.annotation.*;

/**
 * 平台菜单
 * @author USER
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PlatformOnly {
}
