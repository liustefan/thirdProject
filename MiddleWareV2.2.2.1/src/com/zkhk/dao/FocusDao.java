package com.zkhk.dao;

import java.util.List;

import java.util.Set;
import com.zkhk.entity.Focus;
import com.zkhk.entity.FocusMem;
import com.zkhk.entity.FocusRemark;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Odoc;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;


public interface FocusDao {
    /**
     * 查询所有我关注的信息
     * @param memberId
     * @return
     * @throws Exception
     */
	List<Focus> findMyFocusByMemberId(int memberId) throws Exception;
    /**
     * 修改关注信息所以的修改都在该接口中实现
     * @param mem
     */
	void updateFocusByParam(Focus mem) throws Exception;
	/**
	 * 获取查询到的会员信息
	 * @param ifWhere
	 * @param i 
	 * @return
	 * @throws Exception
	 */
	List<FocusMem> getFocusMemsByParam(String ifWhere, int i,int page)throws Exception;
	/**
	 * 查询所有符合条件想邀请的会员信息
	 * @param ifWhere
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	List<FocusMem> getInviteFocuseMem(String ifWhere, int memberId,int page) throws Exception;
	/**
	 * 查询我已经关注的会员信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	List<Focus> getMyFocusedByParam(int memberId) throws Exception;
	/**
	 * 查询以及关注我的会员信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	List<Focus> getFocusedMeByParam(int memberId) throws Exception;
	/**
	 * 添加关注信息记录
	 * @param mem
	 * @return
	 * @throws Exception
	 */
	int addFocusByParam(Focus mem) throws Exception;
	
	/**
     *查询会员的监护医生
     * @param memberId
     * @return
     * @throws Exception
     */
	Set<Odoc> getCustodyDoc(int memberId)throws Exception;
	/**
	 * 修改关注的备注信息
	 * @param remark
	 */
	void updateFocusRemarkByParam(FocusRemark remark)throws Exception;
	/**
	 * 获取会员头像
	 * @param memberId
	 * @param response
	 * @throws Exception
	 */
	byte[] getHeadImgByMemberId(int memberId)throws Exception;
	
	/**
	 * 查询关注会员的心电数据
	 * @return
	 */
	List<Oecg> findEcgbyMemberIdAndTime(String eventIds) throws Exception;
	/**
	 * 查询关注会员的脉搏数据
	 * @param focusId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Oppg> findPpgbyMemberIdAndTime(String eventIds)throws Exception;
	/**
	 * 查询关注会员的血糖数据
	 * @param focusId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Obsr> findBsrbyMemberIdAndTime(String eventIds)throws Exception;
	/**
	 * 查找关注会员的血压数据
	 * @param focusId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Osbp> findSbpbyMemberIdAndTime(String eventIds)throws Exception;
	/**
	 * 获取关注会员的
	 * @param focusId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return
	 */
	String getEventsByParam(int focusId, String startTime, String endTime,int page)throws Exception;
	/**
	 * 获取关注信息更新的条数
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	int getFocusCount(int memberId)throws Exception;
	/**
	 * 查入标记时间
	 * @param memberId
	 * @param formatDatetime2
	 * @throws Exception
	 */
	void saveUpdateTag(int memberId ,String formatDatetime2)throws Exception;



}
