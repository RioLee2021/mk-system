package net.system.mk.commons.meta;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @date 时间：2022年4月22日 下午8:36:28
 */
@Data
@ApiModel(value = "分页数据返回格式")
public class PagerResult<T> {

	@ApiModelProperty(value = "分页，从1开始")
	private int page = 1;

	@ApiModelProperty(value = "每页显示数量")
	private int pageSize;

	@ApiModelProperty(value = "总数据量")
	private long total;

	@ApiModelProperty(value = "总页数")
	private long totalPage;

	@ApiModelProperty(value = "数据列表")
	private List<T> list;

	public PagerResult(IPage<T> page) {
		this.page = (int) page.getCurrent();
		this.pageSize = (int) page.getSize();
		this.total = page.getTotal();
		this.totalPage = page.getPages();
		this.list = page.getRecords();
	}

	public PagerResult(Page<T> page){
		this.page = page.getNumber();
		this.pageSize = page.getSize();
		this.total = page.getTotalElements();
		this.totalPage = page.getTotalPages();
		this.list = page.getContent();
	}

	public static <T>PagerResult<T> of(Page<T> page){
		return new PagerResult<>(page);
	}

	public static <T>PagerResult<T> of(IPage<T> page) {
		return new PagerResult<>(page);
	}

	public PagerResult(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}
	
	
	public PagerResult(int page, int pageSize, long total) {
		this.page = page;
		this.pageSize = pageSize;
		this.total = total;
		if (total > 0) {
			this.totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize + 1);
		}
	}
	
	
	public PagerResult(int page, int pageSize, long total, List<T> list) {
		this.page = page;
		this.pageSize = pageSize;
		this.total = total;
		if (total > 0) {
			this.totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize + 1);
		}
		this.list = list;
	}
	
	
	/**
	 * 设置总条数，并求出总页数
	 */
	public void setRows(long total) {
		this.total = total;
		// 页数根据传入的总行数以及每页显示的行数，求出总页数
		if (total > 0) {
			this.totalPage = total % pageSize == 0 ? total / pageSize : (total / pageSize + 1);
		} else {
			totalPage = 0;
		}
	}

}