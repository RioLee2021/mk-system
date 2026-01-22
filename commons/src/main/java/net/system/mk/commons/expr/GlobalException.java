package net.system.mk.commons.expr;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * webs全局异常类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private String code;


    public GlobalException(GlobalErrorCode gec){
        this(gec.getCode(),gec.getDescribe());
    }

    public GlobalException(GlobalErrorCode gec, String msg) {
        this(gec.getCode(),msg);
    }



    public GlobalException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    
    public GlobalException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

}