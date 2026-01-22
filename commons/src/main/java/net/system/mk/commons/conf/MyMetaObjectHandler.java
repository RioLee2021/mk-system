package net.system.mk.commons.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.utils.DateTimeUtils;
import net.system.mk.commons.utils.SpringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author USER
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createAt", DateTimeUtils.nowDateTime(), metaObject);
        this.setFieldValByName("updateAt", DateTimeUtils.nowDateTime(), metaObject);
//        Object disable = this.getFieldValByName("disabled", metaObject);
//        if (disable == null){
//            this.setFieldValByName("disabled", false, metaObject);
//        }
        Object val = this.getFieldValByName("createBy", metaObject);
        if (RequestContextHolder.getRequestAttributes() == null && val == null) {
            this.setFieldValByName("createBy", "System", metaObject);
            return;
        }
        if (val == null) {
            ICtxHelper iCtxHelper = SpringUtils.getBean(ICtxHelper.class);
            IBaseContext backend = iCtxHelper.getBackendCtx();
            if (backend != null) {
                this.setFieldValByName("createBy", backend.account(), metaObject);
            } else {
                this.setFieldValByName("createBy", "System", metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateAt", DateTimeUtils.nowDateTime(), metaObject);
    }
}
