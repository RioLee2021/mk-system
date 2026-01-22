package net.system.mk.commons.advice;

import cn.hutool.json.JSONUtil;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @date 时间：2022年4月12日 下午3:09:32
 * web项目全局处理异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 用来处理bean validation异常
     */
    @ExceptionHandler(value = BindException.class)
    public ResultBody<Void> resolveBindException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult != null) {
            if (bindingResult.getErrorCount() <= 0) {
                return ResultBody.error(GlobalErrorCode.BEAN_VALIDATION.getCode(), ex.getMessage());
            } else {
                return ResultBody.error(GlobalErrorCode.BEAN_VALIDATION.getCode(), bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
        } else {
            return ResultBody.error(GlobalErrorCode.BEAN_VALIDATION.getCode(), ex.getMessage());
        }
    }


    /**
     * 参数格式异常
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultBody<Void> httpMessageNotReadable(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return ResultBody.error(GlobalErrorCode.TYPE_NOT_MATCH.getCode(), "params.type.not.match");
    }


    /**
     * 方法错误异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBody<Void> resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            for (ObjectError error : objectErrors) {
                return ResultBody.error(GlobalErrorCode.VALID_PARAMS.getCode(), error.getDefaultMessage());
            }
        }
        return ResultBody.error(GlobalErrorCode.VALID_PARAMS.getCode(), ex.getMessage());
    }


    /**
     * 请求路径有误
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultBody<Void> noHandlerFoundException(NoHandlerFoundException ex) {
        logger.error("Request path is error:{}", ex.getMessage());
        return ResultBody.error(GlobalErrorCode.HANDLER_PATH.getCode(), "check.request.path.is.correct");
    }


    /**
     * 上传文件大小限制
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultBody<Void> maxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        logger.error("Request upload file is error:{}", ex.getMessage());
        return ResultBody.error(GlobalErrorCode.FILE_SIZE_LIMIT.getCode(), "file.upload.size.is.limited");
    }


    /**
     * 请求方式错误
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBody<Void> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error("Request method is error:{}", ex.getMessage());
        return ResultBody.error(GlobalErrorCode.METHOD_ERROR.getCode(), ex.getMessage());
    }


    /**
     * 部分参数转换错误
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResultBody<Void> illegalStateException(IllegalStateException ex) {
        logger.error("Request IllegalStateException is error:{}", ex.getMessage());
        return ResultBody.error(GlobalErrorCode.ILLEGAL_ERROR.getCode(), ex.getMessage());
    }

    @ExceptionHandler(GlobalException.class)
    public ResultBody<Void> globalException(GlobalException ex) {
        if (!"login.session.expired".equals(ex.getMessage())) {
            logger.error("Request GlobalException is error:{}", ex.getMessage());
        }
        return ResultBody.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(NoSuchMessageException.class)
    public ResultBody<Void> noSuchMessageException(NoSuchMessageException ex) {
        logger.error("Request NoSuchMessageException is error:{}", ex.getMessage());
        return ResultBody.error(GlobalErrorCode.EXCEPTION.getCode(), ex.getMessage());
    }


    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResultBody<Void> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            response.setStatus(HttpStatus.OK.value());
            return ResultBody.error("404", "Request.path.not.find");
        }
        logger.error("RuntimeException:{}", JSONUtil.toJsonStr(ex));
        ex.printStackTrace(); // 这里输出来， 未后面查找问题定位
        String msg = "prod".equals(active) ? "system.exception" : ex.getMessage();
        return ResultBody.error(GlobalErrorCode.EXCEPTION.getCode(), msg);
    }

    @Value("${spring.profiles.active}")
    private String active;

}