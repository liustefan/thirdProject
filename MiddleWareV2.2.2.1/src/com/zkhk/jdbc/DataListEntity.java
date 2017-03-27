package com.zkhk.jdbc;

import java.io.Serializable;
import java.util.Map;

/**
 * 带sql查询列名和结果集VO类
 * @author rjm
 *
 */
public class DataListEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2906025047264880920L;

	/**
	 * sql列名
	 */
	private String[] columnNames;
	
	/**
	 * 字段数据类型
	 */
	private String[] columnNameTypes;
	
	/**
	 * sql结果集
	 */
	private java.util.List<Map<String, String>> dataList;

	/**
	 * 返回所有列名
	 * @return
	 */
	public String[] getColumnNames() {
		return columnNames;
	}
	
	/**
	 * 设置列名
	 * @param columnNames
	 */
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * 返回结果集
	 * @return
	 */
	public java.util.List<Map<String, String>> getDataList() {
		return dataList;
	}

	/**
	 * 设置结果集
	 * @param dataList
	 */
	public void setDataList(java.util.List<Map<String, String>> dataList) {
		this.dataList = dataList;
	}

	/**
	 * 统计结果集
	 */
	private Map<String, String> sumMap = null;

	/**
	 * 返回统计结果集
	 * @return
	 */
	public Map<String, String> getSumMap() {
		return sumMap;
	}
	
	/**
	 * 设置统计结果集
	 * @param sumMap
	 */
	public void setSumMap(Map<String, String> sumMap) {
		this.sumMap = sumMap;
	}
	
	/**
	 * 返回字段数据类型
	 * @return
	 */
	public String[] getColumnNameTypes() {
		return columnNameTypes;
	}
	
	/**
	 * 设置字段数据类型
	 * @param columnNameTypes
	 */
	public void setColumnNameTypes(String[] columnNameTypes) {
		this.columnNameTypes = columnNameTypes;
	}
	
}
