package net.system.mk.commons.expr;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author USER
 */
@AllArgsConstructor
@Getter
public enum GlobalErrorCode {
    // 全局请求成功
    SUCCESS("200", "success"),

    // 全局安全错误
    LOGIN_INVALID("1000", "login.session.expired"),
    PERMISSION_DENIED("1001", "permission.denied"),
    IP_ACCESS_RESTRICTION("1002", "ip.denied"),
    TRAFFIC_LIMIT("1003", "access.flow.control"),
    SIGN_ERROR("1004", "failed.to.request.signature"),
    LOGIN_ERROR("1005", "login.account.or.password.error"),
    ACCOUNT_DISABLE("1006", "current.account.forbidden"),
    PASSWORD_ERROR("1007", "password.error"),
    ACCOUNT_IS_EXIST("1008", "account.is.exist"),
    REPEAT_SUBMIT("1009", "repeat.submit"),
    ROOT_PERMISSION_ONLY("1010", "root.permission.only"),
    BALANCE_NOT_ENOUGH("1011", "balance.not.enough"),

    // 全局Spring内部错误
    SPRINGBOOT_GLOBAL("5000", "internal.error.redefines.format"),

    //分布式锁相关
    TRANS_LOCKED("5001", "transaction.locked"),


    // 全局参数校验错误归集
    BEAN_VALIDATION("0001", "bean.binding.error"),
    TYPE_NOT_MATCH("0003", "params.type.not.match"),
    VALID_PARAMS("0004", "params.check.error"),
    HANDLER_PATH("0005", "check.request.path.is.correct"),
    FILE_SIZE_LIMIT("0006", "file.upload.size.is.limited"),
    METHOD_ERROR("0007", "check.method.path.is.correct"),
    ILLEGAL_ERROR("0008", "params.conversion.error"),
    EXCEPTION("0000", "system.exception"),
    PARAMS_EMPTY("0009", "request.body.not.null"),
    PARAMS_NOT_LEGAL("0009", "request.params.not.legal"),
    TIMESTAMP_EMPTY("0016", "timestamp.not.null"),
    TIMESTAMP_INVALID("0017", "timestamp.invalid.sync.system.time"),
    DATA_NOT_FOUND("0018", "data.not.found"),

    // 全局解密
    DECODE_NOT_EXIST("3001", "request.header.decode.key.is.null"),
    DECODE_NOT_PRIVATE("3002", "decode.secret.is.null"),
    DECODE_ERROR("3003", "data.decode.error"),
    DECODE_FORMAT_ERROR("3003", "request.header.decode.key.format.error"),

    BUSINESS_ERROR("4000", "system.business.error"),
    BUSINESS_BALANCE_NOT_ENOUGH("4001", "balance.not.enough"),

    MQ_SEND_ERROR("5001", "mq.send.error"),

    ;
    private String code;
    private String describe;
}
