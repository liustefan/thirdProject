package com.zkhk.dao.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.entity.Message;
import com.zkhk.jdbc.JdbcService;
@Repository(value="pushMessageDao")
public class PushMessageDaoImpl implements PushMessageDao {
	 @Resource
	 private JdbcService jdbcService;
	public void add(Message m) throws Exception {
		String sql="insert into m_push_message(memberId,notifier,createTime,type,markTag) values(?,?,?,?,?)";
		jdbcService.doExecuteSQL(sql, new Object[]{m.getMemberId(),m.getNotifier(),m.getCreateTime(),m.getType(),""});

	}
	public Message getPushMessageByMemberId(int memberId,int type) {
		//答卷部分需要特殊处理
		int tag=type;
		if(tag==4){
			tag=3;
		}else if(tag>5){
			//答卷部分引起的
			tag=tag-1;
		}
		String sql="select memberId from m_focus_info where focusId=? and focusStatus=2 and Tag='N' and newsLetter=1 and focusPed is not null and focusPed like '%"+tag+"%'";
		SqlRowSet rowSet=jdbcService.query(sql, new Object[]{memberId});
		Message message=new Message();
		StringBuffer sb=new StringBuffer("");
		while(rowSet.next()){
			sb.append(rowSet.getInt("memberId")).append(",");
		}
		if(sb.length()>0){
			message.setNotifier(sb.substring(0, sb.length()-1));
		}
		message.setMemberId(memberId);
		message.setType(type);
		return message;
	}
	public Message getPushMessageByMemberId(int memberId, int notifier, int type) {
		Message message=new Message();
		message.setMemberId(memberId);
		message.setNotifier(notifier+"");
		message.setType(type);
		return message;
	}
	
	public Map<Integer, Map<Integer, List<Long>>> getFocusMessage(int memberId)throws Exception {
		Map<Integer, Map<Integer, List<Long>>> map=new HashMap<Integer, Map<Integer, List<Long>>>();
		String sql="select id,memberId,type from m_push_message WHERE notifier is not null and notifier like '%"+memberId+"%' and  (markTag not like '%"+memberId+"%' or markTag is null)" ;
		//System.out.println(sql);
		SqlRowSet rowSet=jdbcService.query(sql);
		while(rowSet.next()){
			int createId=rowSet.getInt("memberId");
			int type=rowSet.getInt("type");
			long id=rowSet.getLong("id");
			if(map.containsKey(createId)){
				Map<Integer, List<Long>> map2=map.get(createId);
				if(map2.containsKey(type)){
					List<Long> integers=map2.get(type);
					integers.add(id);
				}else {
					List<Long> list=new ArrayList<Long>();
					list.add(id);
					map2.put(type, list);
				}
			}else {
				Map<Integer, List<Long>> map2=new HashMap<Integer,List<Long>>();
				List<Long> list=new ArrayList<Long>();
				list.add(id);
				map2.put(type, list);
				map.put(createId, map2);
				
			}
		}
		
		return map;
	}
	public void updateMarkTagByCreateId(int createId, int memberId)throws Exception {
		  String sql="update m_push_message set markTag=concat(markTag,?) where memberId=? and notifier is not null and notifier like '%"+memberId+"%' and  (markTag not like '%"+memberId+"%' or markTag is null)";
		  jdbcService.doExecuteSQL(sql, new Object[]{","+memberId,createId});
	}
	public void updateMarkTagByCreateId(int createId, int memberId, int type) throws Exception{
		  String sql="update m_push_message set markTag=concat(markTag,?) where memberId=? and type=? and notifier is not null and  notifier like '%"+memberId+"%' and  (markTag not like '%"+memberId+"%' or markTag is null)";
		  jdbcService.doExecuteSQL(sql, new Object[]{","+memberId,createId,type});
	}
	public void updateMarkTagByNotifierAndType(int notifierId, int type)throws Exception {
		  String sql="update m_push_message set markTag=concat(markTag,?) where type=? and notifier is not null and notifier like '%"+notifierId+"%' and  (markTag not like '%"+notifierId+"%' or markTag is null)";
		  jdbcService.doExecuteSQL(sql, new Object[]{","+notifierId,type});
		
	}
	public void deletePushMessageByTagTime(String tagTime) throws Exception {
		String sql="delete from m_push_message where createTime<?";
		jdbcService.doExecuteSQL(sql, new Object[]{tagTime});
	}
	public void updateMarkTagByCreateId(int createId, int notifierId,String focusPed) throws Exception {
		 String sql="update m_push_message set markTag=concat(markTag,?) where memberId=? and type not in ("+focusPed+") and notifier is not null and notifier like '%"+notifierId+"%' and  (markTag not like '%"+notifierId+"%' or markTag is null)";
		 jdbcService.doExecuteSQL(sql, new Object[]{","+notifierId,createId});
	}
	
}
