package net.system.mk.commons.ctx;



import net.system.mk.commons.enums.CtxScope;
import net.system.mk.commons.enums.RoleType;

import java.io.Serializable;

/**
 * @author USER
 */
public interface IBaseContext extends Serializable {

    /**
     * 获取上下文ID
     * @return the id of the context
     */
    Integer id();

    /**
     * 获取上下文名称
     * @return the name of the context
     */
    String name();

    /**
     * 获取上下文账号
     * @return the account of the context
     */
    String account();

    /**
     * 获取上下文token
     * @return the token of the context
     */
    String token();

    /**
     * 获取是否为超管
     * @return the root of the context
     */
    boolean isRoot();

    String otp();

    Integer merchantId();

    boolean isBanned();

    RoleType role();

    CtxScope scope();
}
