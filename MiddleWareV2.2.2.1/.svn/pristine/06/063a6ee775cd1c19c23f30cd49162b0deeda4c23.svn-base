package com.zkhk.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.multipart.MultipartFile;

import com.zkhk.entity.Omem;

/**
 * 
 * @author rjm
 *
 */
public interface JdbcService {

	/**
	 * 根据参数查询,返回结果集
	 * @param sql
	 * @param args
	 * @return
	 */
	public SqlRowSet query(String sql, Object[] args);

	/**
	 * 不带参数的查询
	 * @param sql
	 * @return
	 */
	public SqlRowSet query(String sql);

	/**
	 * 不带参数的查询
	 * DataListEntity
	 * @param sql
	 * @return
	 */
	public DataListEntity queryForDataListEntity(String sql);

	/**
	 * 不带参数的分页查询
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public DataListEntity queryForDataListEntityByPage(String sql, int pageIndex, int pageSize);

	/**
	 * 带参数的查询
	 * @param sql
	 * @param args
	 * @param calculateStatistics 是否合计
	 * @return
	 */
	public DataListEntity queryForDataListEntity(String sql, Object[] args, boolean calculateStatistics);

	/**
	 * 带参数的分页查询
	 * @param sql
	 * @param args
	 * @param pageIndex 页码
	 * @param pageSize 小于1 表示不分页
	 * @param calculateStatistics 是否合计
	 * @return
	 */
	public DataListEntity queryForDataListEntityByPage(String sql, Object[] args, int pageIndex, int pageSize,
			boolean calculateStatistics);

	/**
	 * 取得记录总数，在分页查询中使用
	 * @param sql
	 * @param args
	 * @return
	 */
	public int getRecordCount(String sql, Object[] args);

	/**
	 * 取得记录总数，在分页查询中使用
	 * @param sql
	 * @return
	 */
	public int getRecordCount(String sql);

	/**
	 * 取得sql的统计数量
	 * @param sql (select count(1) from .....)
	 * @param params
	 * @return
	 */
	public int queryForInt(String sql, Object[] params);

	/**
	 * 读数据表结构信息
	 * @param tableName
	 * @return
	 */
	public Map<String, TableColumnEntity> getTableStructreInfo(String tableName);

	/**
	 * 取得序列的下一键值
	 * @param sequenceName
	 * @return
	 */
	public Long getSequenceValue(String sequenceName);

	/**
	 * 执行sql
	 * @param sql
	 * @param params
	 * @return
	 */
	public int doExecuteSQL(String sql, Object[] params);

	/**
	 * 读取系统参数配置
	 * @param paramName
	 * @return
	 */
	public String getSystemParamValue(String paramName);

	/**
	 * 执行sql
	 * @param sql
	 * @param params
	 * @return
	 */
	public void doExecute2(String sql);

	/**
	 * 执行sql
	 * @param sql
	 * @param params
	 * @param argTypes 参数数据类型
	 * @return
	 */
	public int doExecute(String sql, Object[] params, int[] argTypes);

	/**
	 * 批量执行sql
	 * @param list
	 */
	public void doExecuteBatch(List<BatchEntity> list);

	/**
	 * 批量执行sql
	 * @param List<String> sqlList
	 */
	public void batchUpdate(List<String> sqlList);
	
	/**
	 * 查询某表ID的下一个ID(max(id+1))
	 * @param searchCol 要查询的列
	 * @param table 要查询的表
	 * @return
	 */
	public Long getNextId(String searchCol,String table) ;
	
	
	/**
	 * 执行sql
	 * @param sql
	 * @param params
	 * @return 插入的ID
	 */
	public int doExecuteSQLReturnId(String sql, Object[] params,String tableName,String idName);
	/**
	 * 获取最大ID号
	 * @param tableName
	 * @param idName
	 * @return
	 */
	public int getMaxId(String tableName,String idName);
    /**
     * 修改会员的头像信息
     * @param omem
     */
	public void updatOmemHeadImg(String sql,MultipartFile headImg) throws Exception;
    /**
     * 获取会员头像
     * @param omem
     * @throws Exception
     */
	//public void getHeadImg(Omem omem)throws Exception;
	/**
	 * 获取自增id的方法
	 * @param id
	 * @param sql
	 */
	public long addId(String sql);
}
