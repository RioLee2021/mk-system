package net.system.mk.commons.anno.menu;


import net.system.mk.commons.enums.MenuScope;
import net.system.mk.commons.enums.PermMenuGroup;

import java.lang.annotation.*;

/**
 * @author USER
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PermMenuScan {

    PermMenuGroup group();

    MenuScope scope()default MenuScope.both;
}
