package net.system.mk.commons.meta;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.system.mk.commons.expr.GlobalErrorCode;

/**
 * web接口返回数据对象
 */
@Data
@ApiModel(value = "统一返回数据格式")
public class ResultBody<T> {

	@ApiModelProperty(value = "统一返回code(200:成功，其他表示错误，根据具体接口业务处理)")
	private String code;
	
	@ApiModelProperty(value = "统一提示信息")
	private String msg;
	
	@ApiModelProperty(value = "返回业务数据对象")
	private T data;

	
	public ResultBody() {
		
	}
	
	
	public ResultBody(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}


	public ResultBody(String code, T data, String msg) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	
	public static <T> ResultBody<T> success() {
		return new ResultBody<>(GlobalErrorCode.SUCCESS.getCode(), GlobalErrorCode.SUCCESS.getDescribe());
	}


	public static <T> ResultBody<T> ok(T data, String msg) {
		return new ResultBody<>(GlobalErrorCode.SUCCESS.getCode(), data, msg);
	}


	public static <T> ResultBody<T> okMsg(String msg) {
		return new ResultBody<>(GlobalErrorCode.SUCCESS.getCode(), msg);
	}

	
	public static <T> ResultBody<T> okData(T data) {
		return new ResultBody<>(GlobalErrorCode.SUCCESS.getCode(), data, GlobalErrorCode.SUCCESS.getDescribe());
	}


	public static <T> ResultBody<T> error(String msg) {
		return new ResultBody<>(GlobalErrorCode.BUSINESS_ERROR.getCode(), msg);
	}
	
	
	public static <T> ResultBody<T> error(String code, String msg) {
		return new ResultBody<>(code, msg);
	}

}