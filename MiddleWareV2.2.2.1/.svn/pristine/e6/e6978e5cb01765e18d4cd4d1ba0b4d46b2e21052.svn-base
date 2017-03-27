package com.zkhk.jdbc;

import java.io.Serializable;

/**
 * 批处理sql脚本实体类
 * @author Administrator
 *
 */
public class BatchEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5925131838895975990L;

	public BatchEntity(String sql,Object[] args){
		this.sql = sql;
		this.args = args;
	}
	
	/** sql 命令 */
	private String sql;
	
	/** 参数 */
	private Object[] args;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
}
