package net.system.mk.commons.anno;



import net.system.mk.commons.meta.QOP;

import java.lang.annotation.*;

/**
 * 查询条件
 *
 * @author USER
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryCon {

    /**
     * 是否为必须
     *
     * @return bool
     */
    boolean require() default false;

    /**
     * 字段名，空字符串则和属性同名
     *
     * @return str
     */
    String name() default "";

    /**
     * 操作符
     *
     * @return QOP
     */
    QOP op() default QOP.eq;

    /**
     * 是否排序字段
     *
     * @return bool
     */
    boolean order() default false;

    /**
     * 是否正序
     *
     * @return bool
     */
    boolean asc() default false;
}
