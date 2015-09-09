package com.hxb.global.common.tb;

import java.util.List;

public class TableDatas {

	private Long total;//总记录数
	private Object[] rows;//数据记录
	
	
	/**
	 * list 数据绑定
	 * @param lt
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void bindDatas(List lt){
		rows = new Object[lt.size()];
		if(lt != null){
			lt.toArray(rows);
		}
	}
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Object[] getRows() {
		return rows;
	}
	public void setRows(Object[] rows) {
		this.rows = rows;
	}
	
	
}
