package net.system.mk.commons.meta;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

/**
 * @date 时间：2022年4月22日 下午8:36:28
 */
@Data
@ApiModel(value = "分页请求参数")
public class PagerRequest {

	@Min(value = 1, message = "request.params.min.value&page,1")
	private int page = 1;
	
	@Range(min = 1, max = 200, message = "request.params.range.value&pageSize,[1, 200]")
	private int pageSize;

	public <T>IPage<T> toPage(){
		return new Page<>(page, pageSize);
	}
	
}