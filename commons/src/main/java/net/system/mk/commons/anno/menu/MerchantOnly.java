package net.system.mk.commons.anno.menu;

import java.lang.annotation.*;

/**
 * 商户菜单
 * @author USER
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MerchantOnly {
}
