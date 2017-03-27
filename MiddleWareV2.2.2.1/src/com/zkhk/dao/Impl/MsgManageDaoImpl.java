package com.zkhk.dao.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.zkhk.dao.MsgManageDao;
import com.zkhk.entity.MsgManage;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.util.TimeUtil;

@Repository(value="msgManageDao")
public class MsgManageDaoImpl implements MsgManageDao {
	
	 @Resource
	 private JdbcService jdbcService;

	/**
	 * @Description 根据(会员id,接收时间，最近次数)来查找会员接收的消息
	 * @author liuxiaoqin
	 * @2015年6月19日
	 */
	public List<MsgManage> findMsgByMemIdAndDate(int memberId,String beginDate,String endDate, int recentTimes)  throws Exception
	{
		String sql = "  SELECT * FROM message m WHERE m.Tag = 'T' and m.Receiveid = ? and m.SendTime >= ? and m.SendTime <= ? ORDER BY m.SendTime DESC LIMIT ? ";
		SqlRowSet rowSet;
		if(endDate == null || "".equals(endDate))
		{
			//限制查询消息的条数
			if(recentTimes > 0)
			{
				sql = "  SELECT * FROM message m WHERE m.Tag = 'T' and m.Receiveid = ? and m.SendTime >= ? and m.SendTime <= curdate() ORDER BY m.SendTime DESC LIMIT ? ";
				rowSet=jdbcService.query(sql, new Object[]{memberId,beginDate,recentTimes});
			}
			else
			{
				sql = "  SELECT * FROM message m WHERE m.Tag = 'T' and m.Receiveid = ? and m.SendTime >= ? and m.SendTime <= curdate() ORDER BY m.SendTime DESC  ";
				rowSet=jdbcService.query(sql, new Object[]{memberId,beginDate});
			}
		}
		else
		{
			//限制查询消息的条数
			if(recentTimes > 0)
			{
				rowSet=jdbcService.query(sql, new Object[]{memberId,beginDate,endDate,recentTimes});
			}
			else
			{
				sql = "  SELECT * FROM message m WHERE m.Tag = 'T' and m.Receiveid = ? and m.SendTime >= ? and m.SendTime <= ? ORDER BY m.SendTime DESC  ";
				rowSet=jdbcService.query(sql, new Object[]{memberId,beginDate,endDate});
			}
		}
		List<MsgManage> list = new ArrayList<MsgManage>();
	    while(rowSet.next()){
	    	MsgManage msg = new MsgManage();
	    	msg.setMessageId(rowSet.getInt("Messageid"));;
	    	msg.setSendId(rowSet.getInt("Sendid"));
	    	msg.setOrgId(rowSet.getInt("OrgId"));
	    	msg.setReceiveId(rowSet.getInt("Receiveid"));
	    	msg.setContent(rowSet.getString("Content"));
	    	msg.setSendTime(TimeUtil.paserDatetime2(rowSet.getString("SendTime")));
	    	msg.setTag(rowSet.getString("Tag"));
	    	msg.setHasRead(rowSet.getString("has_read"));
	    	list.add(msg);
	    }
		return list;
	}
	
	/**
	 * @Description 删除消息
	 * @author liuxiaoqin
	 * @2015年6月19日
	 */
	public void updateMsgIsInvalid(int msgId) throws Exception
	{
		String sql=" update message set Tag = 'F' where Messageid = ? ";
		jdbcService.doExecuteSQL(sql, new Object[]{msgId});
	
	}

	/**
	 * @Description 根据id来查询某条消息的具体内容
	 * @author liuxiaoqin
	 * @2015年6月25日
	 */
	public MsgManage findMsgById(int msgId) throws Exception 
	{
		String sql=" select * from message where Tag = 'T' and Messageid = ? ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[]{msgId});
		while(rowSet.next()){
	    	MsgManage msg = new MsgManage();
	    	msg.setMessageId(rowSet.getInt("Messageid"));;
	    	msg.setSendId(rowSet.getInt("Sendid"));
	    	msg.setOrgId(rowSet.getInt("OrgId"));
	    	msg.setReceiveId(rowSet.getInt("Receiveid"));
	    	msg.setContent(rowSet.getString("Content"));
	    	msg.setSendTime(TimeUtil.paserDatetime2(rowSet.getString("SendTime")));
	    	msg.setTag(rowSet.getString("Tag"));
	    	msg.setHasRead(rowSet.getString("has_read"));
	    	return msg;
	    }
		return null;
	}
	
	/**
     * @Description 根据会员id来查找所有未读取的新信息。
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
   */
    public List<MsgManage> findNewMsgList(int memberId)  throws Exception
    {
        List<MsgManage> list = new ArrayList<MsgManage>();
        String sql = "  SELECT * FROM message m WHERE m.Tag = 'T' and m.Receiveid = ? and m.has_read = 'F' ORDER BY m.SendTime DESC ";
        SqlRowSet rowSet;
        rowSet = jdbcService.query(sql, new Object[]{memberId});
        while(rowSet.next()){
            MsgManage msg = new MsgManage();
            msg.setMessageId(rowSet.getInt("Messageid"));;
            msg.setSendId(rowSet.getInt("Sendid"));
            msg.setOrgId(rowSet.getInt("OrgId"));
            msg.setReceiveId(rowSet.getInt("Receiveid"));
            msg.setContent(rowSet.getString("Content"));
            msg.setSendTime(TimeUtil.paserDatetime2(rowSet.getString("SendTime")));
            msg.setTag(rowSet.getString("Tag"));
            msg.setHasRead(rowSet.getString("has_read"));
            list.add(msg);
        }
        return list;
    }
    
    /**
      * @Description 根据某msgId来读取某条未读信息
      * @author liuxiaoqin
      * @CreateDate 2015年9月16日
    */
    public MsgManage findNewMsgByMsgId(int msgId) throws Exception
    {
        String sql=" select * from message where Tag = 'T' and Messageid = ? and has_read = 'F' ";
        SqlRowSet rowSet = jdbcService.query(sql, new Object[]{msgId});
        while(rowSet.next()){
            MsgManage msg = new MsgManage();
            msg.setMessageId(rowSet.getInt("Messageid"));;
            msg.setSendId(rowSet.getInt("Sendid"));
            msg.setOrgId(rowSet.getInt("OrgId"));
            msg.setReceiveId(rowSet.getInt("Receiveid"));
            msg.setContent(rowSet.getString("Content"));
            msg.setSendTime(TimeUtil.paserDatetime2(rowSet.getString("SendTime")));
            msg.setTag(rowSet.getString("Tag"));
            msg.setHasRead(rowSet.getString("has_read"));
            return msg;
        }
        return null;
    }
    
    /**
     * @Description 修改该条信息为已读
     * @author liuxiaoqin
     * @2015年6月19日
     */
    public Integer updateMsgHasRead(int msgId) throws Exception
    {
        Integer count = 0;
        String sql=" update message set has_read = 'T' where Messageid = ? ";
        count = jdbcService.doExecuteSQL(sql, new Object[]{msgId});
        return count;
    }
	
    /**
     * @Description 根据参数获取消息列表
     * @author liuxiaoqin
     * @CreateDate 2015年10月15日
    */
    public List<MsgManage> findMsgListByParam(int memberId, int pageNo, int pageSize) throws Exception
    {
        List<MsgManage> list = new ArrayList<MsgManage>();
        String sql = "SELECT m.*,o.DocName  FROM message m LEFT JOIN odoc o on m.Sendid = o.Docid WHERE m.Tag = 'T' and m.Receiveid = ? ORDER BY m.has_read, m.SendTime DESC LIMIT ?,?";
        SqlRowSet rowSet;
        rowSet = jdbcService.query(sql, new Object[]{memberId,(pageNo - 1)*pageSize,pageSize});
        while(rowSet.next()){
            MsgManage msg = new MsgManage();
            msg.setMessageId(rowSet.getInt("Messageid"));;
            msg.setSendId(rowSet.getInt("Sendid"));
            msg.setOrgId(rowSet.getInt("OrgId"));
            msg.setReceiveId(rowSet.getInt("Receiveid"));
            msg.setContent(rowSet.getString("Content"));
            msg.setSendTime(TimeUtil.paserDatetime2(rowSet.getString("SendTime")));
            msg.setTag(rowSet.getString("Tag"));
            msg.setHasRead(rowSet.getString("has_read"));
            msg.setSendName(rowSet.getString("DocName"));
            list.add(msg);
        }
        return list;
    }
    
}
