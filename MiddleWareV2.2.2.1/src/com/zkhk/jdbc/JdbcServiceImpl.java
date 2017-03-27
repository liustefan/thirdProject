package com.zkhk.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zkhk.entity.Omem;
import com.zkhk.util.ImageUtils;

public class JdbcServiceImpl extends JdbcDaoSupport implements JdbcService {

	private static final Logger logger = Logger
			.getLogger(JdbcServiceImpl.class);

	@SuppressWarnings("unchecked")
	public SqlRowSet query(String sql, Object[] args)
	{

		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(args));
		return (SqlRowSet) new JdbcTemplate(this.getDataSource()).query(sql,args, new SqlRowSetSqlServerResultSetExtractor());
	}

	@SuppressWarnings("unchecked")
	public SqlRowSet query(String sql) 
	{
		return (SqlRowSet) new JdbcTemplate(this.getDataSource()).query(sql,new SqlRowSetSqlServerResultSetExtractor());

	}

	@SuppressWarnings("unchecked")
	public DataListEntity queryForDataListEntity(String sql, Object[] args,boolean calculateStatistics)
	{

		DataListEntity entity = new DataListEntity();
		
		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(args));

		SqlRowSet dataSet = (SqlRowSet) new JdbcTemplate(this.getDataSource()).query(sql, args, new SqlRowSetSqlServerResultSetExtractor());

		String[] columnNames = dataSet.getMetaData().getColumnNames();

		String[] nameTypes = new String[columnNames.length];

		for (int i = 1; i <= dataSet.getMetaData().getColumnCount(); i++) {
			nameTypes[i - 1] = dataSet.getMetaData().getColumnTypeName(i);
		}

		entity.setColumnNameTypes(nameTypes);

		// caculate statics
		if (calculateStatistics) {
			SqlRowSetMetaData meta = dataSet.getMetaData();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i <= meta.getColumnCount(); i++) {

				if ("NUMBER".equals(meta.getColumnTypeName(i))) {
					sb.append("sum(nvl(\"").append(meta.getColumnName(i))
							.append("\",0)) as \"")
							.append(meta.getColumnName(i)).append("\",");
				}
			}
			if (sb.length() > 0) {
				String sumSql = "select " + sb.substring(0, sb.length() - 1)
						+ " from ( " + sql + " )";
				SqlRowSet sumSet = (SqlRowSet) new JdbcTemplate(
						this.getDataSource()).query(sumSql, args,
						new SqlRowSetSqlServerResultSetExtractor());
				Map<String, String> sumMap = new HashMap<String, String>();
				String[] names = sumSet.getMetaData().getColumnNames();
				while (sumSet.next()) {
					for (String name : names) {
						sumMap.put(name, sumSet.getString(name));
					}
				}
				entity.setSumMap(sumMap);
			}
		}

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		while (dataSet.next()) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for (String element : columnNames) {
				String ds = dataSet.getString(element);
				if ("SQLSTR".equals(element)) {
					if (ds != null) {
						ds = ds.replace("return false;", "");
					}
				}
				dataMap.put(element, ds);
			}
			dataList.add(dataMap);
		}

		entity.setColumnNames(columnNames);
		entity.setDataList(dataList);

		return entity;
	}

	@SuppressWarnings("unchecked")
	public DataListEntity queryForDataListEntity(String sql)
	{

		SqlRowSet dataSet = (SqlRowSet) new JdbcTemplate(this.getDataSource())
				.query(sql, new SqlRowSetSqlServerResultSetExtractor());

		SqlRowSetMetaData meta = dataSet.getMetaData();

		String[] columnNames = new String[meta.getColumnCount()];

		for (int i = 1; i <= meta.getColumnCount(); i++) {
			columnNames[i - 1] = meta.getColumnName(i);
		}

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		while (dataSet.next()) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for (String element : columnNames) {
				dataMap.put(element, dataSet.getString(element));
			}
			dataList.add(dataMap);
		}

		DataListEntity entity = new DataListEntity();
		entity.setColumnNames(columnNames);
		entity.setDataList(dataList);

		return entity;
	}

	@SuppressWarnings("unchecked")
	public DataListEntity queryForDataListEntityByPage(String sql,int pageIndex, int pageSize)
	{

		JdbcTemplate template = new JdbcTemplate(this.getDataSource());
		template.setFetchSize(pageSize);
		template.setMaxRows(pageIndex * pageSize);
		
		SqlRowSet dataSet = (SqlRowSet) template.query(sql,
				new SqlRowSetSqlServerResultSetExtractor());

		SqlRowSetMetaData meta = dataSet.getMetaData();

		String[] columnNames = new String[meta.getColumnCount()];

		for (int i = 1; i <= meta.getColumnCount(); i++) {
			columnNames[i - 1] = meta.getColumnName(i);

		}

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		while (dataSet.next()) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for (String element : columnNames) {
				dataMap.put(element, dataSet.getString(element));
			}
			dataList.add(dataMap);
		}

		DataListEntity entity = new DataListEntity();
		entity.setColumnNames(columnNames);
		entity.setDataList(dataList);

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.harbor.spring.service.JdbcService#queryForDataListEntityByPage(java
	 * .lang.String, java.lang.Object[], int, int, boolean)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.harbor.spring.service.JdbcService#queryForDataListEntityByPage(java
	 * .lang.String, java.lang.Object[], int, int, boolean)
	 */
	@SuppressWarnings("unchecked")
	public DataListEntity queryForDataListEntityByPage(String sql,Object[] args, int pageIndex, int pageSize,boolean calculateStatistics)
	{

		DataListEntity entity = new DataListEntity();

		String sql_temp = sql;

		int startRow = (pageIndex - 1) * pageSize;
		int endRow = pageIndex * pageSize;

		sql = this.getPageSQL(sql);

		Object[] new_args;
		if (args == null) {
			new_args = new Object[] { endRow, startRow };
		} else {
			new_args = new Object[args.length + 2];
			for (int i = 0; i < args.length; i++) {
				new_args[i] = args[i];
			}
			new_args[new_args.length - 2] = endRow;
			new_args[new_args.length - 1] = startRow;
		}

		JdbcTemplate template = new JdbcTemplate(this.getDataSource());

		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(args));
		SqlRowSet dataSet = (SqlRowSet) template.query(sql, new_args,
				new SqlRowSetSqlServerResultSetExtractor());

		SqlRowSetMetaData meta = dataSet.getMetaData();

		String[] temp = meta.getColumnNames();

		List<String> nameTempList = new ArrayList<String>();

		for (String name : temp) {

			if ("ROWNO".equals(name)) {
				continue;
			}

			nameTempList.add(name);
		}

		String[] columnNames = new String[nameTempList.size()];

		for (int i = 0; i < nameTempList.size(); i++) {
			columnNames[i] = nameTempList.get(i);
		}

		// String[] nameTypes = new String[columnNames.length];
		List<String> nameTypeList = new ArrayList<String>(columnNames.length);
		for (int i = 1; i <= meta.getColumnCount(); i++) {
			// nameTypes[i-1] = meta.getColumnTypeName(i);
			if ("ROWNO".equals(meta.getColumnName(i))) {
				continue;
			}
			nameTypeList.add(meta.getColumnTypeName(i));
		}

		String[] nameTypes = new String[columnNames.length];

		for (int i = 0; i < nameTypeList.size(); i++) {
			nameTypes[i] = nameTypeList.get(i);
		}

		entity.setColumnNameTypes(nameTypes);

		// cacluate static
		if (calculateStatistics) {
			meta = dataSet.getMetaData();
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i <= meta.getColumnCount(); i++) {

				if ("NUMBER".equals(meta.getColumnTypeName(i))) {

					if ("ROWNO".equals(meta.getColumnName(i))) {
						continue;
					}

					sb.append("sum(nvl(").append(meta.getColumnName(i))
							.append(",0)) as ").append(meta.getColumnName(i))
							.append(",");
				}

			}

			if (sb.length() > 0) {
				String sumSql = "select " + sb.substring(0, sb.length() - 1)
						+ " from ( " + sql_temp + " )";
				SqlRowSet sumSet = (SqlRowSet) new JdbcTemplate(
						this.getDataSource()).query(sumSql, args,
						new SqlRowSetSqlServerResultSetExtractor());
				Map<String, String> sumMap = new HashMap<String, String>();
				String[] names = sumSet.getMetaData().getColumnNames();
				while (sumSet.next()) {
					for (String name : names) {
						sumMap.put(name, sumSet.getString(name));
					}
				}
				entity.setSumMap(sumMap);
			}
		}

		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		while (dataSet.next()) {
			Map<String, String> dataMap = new HashMap<String, String>();
			for (String element : columnNames) {
				dataMap.put(element, dataSet.getString(element));
			}
			dataList.add(dataMap);
		}
		/*
		 * int count = dataList.size(); if (dataList.size() < 3) { for (int k =
		 * 0; k < 3 - count; k++) { dataList.add(new HashMap<String, String>());
		 * } }
		 */

		entity.setColumnNames(columnNames);
		entity.setDataList(dataList);

		return entity;
	}

	/**
	 * 生成分页查询语句
	 * 
	 * @param sql
	 * @return
	 */
	private String getPageSQL(String sql)
	{

		// sql = sql.toUpperCase();

		int whereIndex = sql.toUpperCase().lastIndexOf("WHERE");
		int subQueryIndex = sql.toUpperCase().lastIndexOf(")");
		int funInex = sql.toUpperCase().lastIndexOf("(");
		int fromIndex = sql.toUpperCase().indexOf("FROM");
		int orderIndex = sql.toUpperCase().lastIndexOf("ORDER");

		if (orderIndex > -1) {
			logger.debug("SQL 有排序");
			return "SELECT * FROM (SELECT TT.*, ROWNUM AS ROWNO FROM ("
					+ sql
					+ ") TT WHERE ROWNUM <= ? ) TABLE_ALIAS where TABLE_ALIAS.ROWNO > ? ";
		} else {

			logger.debug("SQL 没有排序");

			String new_sql = "";
			boolean hasCondiction = false;

			/**
			 * if(sql.indexOf("where")==-1){ hasCondiction = false; }else{
			 * if(sql.indexOf(")")==-1){ //不带子查询 hasCondiction = true; }else{
			 * //带子询 String temp =
			 * sql.toLowerCase().substring(sql.lastIndexOf(")"),
			 * sql.length()-1); hasCondiction = temp.indexOf("where")!=-1; } }
			 **/

			if ((subQueryIndex == -1) || (subQueryIndex < fromIndex)
					|| (funInex > whereIndex)) {
				// 无子查询
				if (whereIndex == -1) {
					// 无查询条件
				} else {
					// 有查询条件
					hasCondiction = true;
				}

			} else {
				// 有子查询
				if ((whereIndex == -1) || (subQueryIndex > whereIndex)) {
					// 无查询条件
				} else {
					// 有查询条件
					hasCondiction = true;
				}
			}

			logger.debug("hasCondiction:" + hasCondiction);

			new_sql = "SELECT * FROM ( "
					+ sql.replaceFirst("SELECT", "SELECT ROWNUM ROWNO,")
					+ (hasCondiction ? " and " : " where ")
					+ " ROWNUM <= ? ) TABLE_ALIAS WHERE TABLE_ALIAS.ROWNO > ? ";

			logger.debug("new_sql:\n" + new_sql);

			return new_sql;
		}
	}

	@SuppressWarnings("deprecation")
	public int getRecordCount(String sql, Object[] args)
	{
		
		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(args));
		return this.getJdbcTemplate().queryForInt(
				"select count(1) from (" + sql + ")", args);
	}

	@SuppressWarnings("deprecation")
	public int getRecordCount(String sql) 
	{

		return this.getJdbcTemplate().queryForInt(
				"select count(1) from (" + sql + ")");
	}

	@SuppressWarnings("deprecation")
	public int queryForInt(String sql, Object[] params)
	{

		
		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(params));
		return this.getJdbcTemplate().queryForInt(sql, params);
	}

	public Map<String, TableColumnEntity> getTableStructreInfo(String tableName)
	{

		// tableName = "MW_WORKITEM";

		String sql = "SELECT  COLUMN_NAME,DATA_TYPE,DATA_LENGTH,DATA_PRECISION,DATA_SCALE FROM USER_TAB_COLUMNS "
				+ "WHERE TABLE_NAME NOT IN (SELECT VIEW_NAME FROM USER_VIEWS) and table_name = upper( ? ) ";
		SqlRowSet set = this.query(sql, new Object[] { tableName });
		Map<String, TableColumnEntity> map = new HashMap<String, TableColumnEntity>();
		while (set.next()) {
			TableColumnEntity entity = new TableColumnEntity();
			entity.setColumnName(set.getString("COLUMN_NAME"));
			entity.setDataLength(set.getString("DATA_LENGTH"));
			entity.setDataPrecision(set.getString("DATA_PRECISION"));
			entity.setDataType(set.getString("DATA_TYPE"));
			entity.setDataScale(set.getString("DATA_SCALE"));
			map.put(entity.getColumnName(), entity);
		}
		return map;
	}

	@SuppressWarnings("deprecation")
	public Long getSequenceValue(String sequenceName)
	{

		String sql = "select  " + sequenceName + ".nextval from dual ";
		return new Long(this.getJdbcTemplate().queryForInt(sql));
	}

	public int doExecuteSQL(String sql, final Object[] params)
	{
		
		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(params));
		int i = this.getJdbcTemplate().update(sql, params);
		// int id=this.getJdbcTemplate().queryForInt("CALL IDENTITY()");
		// System.out.println(id+"===================================");
		return i;
		// return 0;
	}

	public String getSystemParamValue(String paramName)
	{

		String sql = " SELECT paras FROM mw_paras WHERE name= ? ";
		String value = "";
		SqlRowSet set = this.query(sql, new Object[] { paramName });
		if (set.next()) {
			value = set.getString("paras");
		}
		return value;
	}

	public void doExecute2(String sql) 
	{

		logger.debug("doExecute2 sql:\n" + sql);
		JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
		jdbcTemplate.execute(sql);
	}

	public int doExecute(String sql, Object[] params, int[] argTypes)
	{
		
		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(params));
		logger.debug("\n argTypes:"  +JSON.toJSONString(argTypes));
		logger.debug("exec sql:\n" + sql);
		return this.getJdbcTemplate().update(sql, params, argTypes);
	}

	public void doExecuteBatch(List<BatchEntity> list)
	{

		for (BatchEntity item : list) {
			this.getJdbcTemplate().update(item.getSql(), item.getArgs());
		}
	}

	public void batchUpdate(List<String> sqlList) 
	{

		if ((null != sqlList) && (sqlList.size() > 0)) {
			String sqls[] = new String[sqlList.size()];
			for (int i = 0; i < sqlList.size(); i++) {
				String sql = sqlList.get(i);
				sqls[i] = sql;
			}
			this.getJdbcTemplate().batchUpdate(sqls);
		}
	}

	@SuppressWarnings("deprecation")
	public Long getNextId(String column, String talbe)
	{
		Long id = 0L;
		StringBuffer sql = new StringBuffer("select max(").append(column)
				.append(")+1 from ").append(talbe);
		return new Long(this.getJdbcTemplate().queryForInt(sql.toString()));
	}

	@SuppressWarnings("deprecation")
	public int doExecuteSQLReturnId(String sql, Object[] params,String tableName, String idName)
	{
		
		logger.debug("\n SQL:" + sql);
		logger.debug("\n PARAMS:"  +JSON.toJSONString(params));
		
		this.getJdbcTemplate().update(sql, params);
		int id = this.getJdbcTemplate().queryForInt(
				"SELECT max( " + idName + " ) from  " + tableName);
		// System.out.println(id+"===================================");
		return id;
	}

	@SuppressWarnings("deprecation")
	public int getMaxId(String tableName, String idName)
	{
		int id = this.getJdbcTemplate().queryForInt(
				"SELECT max( " + idName + " ) from  " + tableName);
		// System.out.println(id+"===================================");
		return id;
	}

	public void updatOmemHeadImg(final String sql, final MultipartFile headImg) throws IOException
	{
		//System.out.println(sql+"=============================");
		final LobHandler lobHandler = new DefaultLobHandler();
		final InputStream inputStream = headImg.getInputStream();
	//	System.out.println(inputStream.read()+"------------");
		this.getJdbcTemplate().execute(sql,
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {

					@Override
					protected void setValues(PreparedStatement pstmt,
							LobCreator lobCreator) throws SQLException,
							DataAccessException {
						lobCreator.setBlobAsBinaryStream(pstmt, 1, inputStream,
								(int) headImg.getSize());
						//System.out.println(headImg.getSize()+"------------------------------------"+sql);

					}
				});
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void getHeadImg(final Omem omem) throws Exception
	{
		final LobHandler lobHandler = new DefaultLobHandler();
		
		this.getJdbcTemplate().query("select headImg from omem where memberid="+omem.getMemberId(),
				new AbstractLobStreamingResultSetExtractor() {
					protected void streamData(ResultSet rs)
							throws SQLException, IOException,
							DataAccessException {
						
					byte [] bs=	lobHandler.getBlobAsBytes(rs,1);
					if (bs!=null&&bs.length>0)
			        omem.setHeadImg(ImageUtils.encodeImgageToBase64(bs));
		              	//System.out.println(omem.getHeadImg());
					}
				});


	}
	
	public long addId(final String sql) 
	{
		// 创建主键执有者
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
}
