package com.zkhk.dao.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;


import org.bson.types.ObjectId;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.mongodb.gridfs.GridFSDBFile;
import com.zkhk.dao.FocusDao;
import com.zkhk.entity.AnomalyEcg;
import com.zkhk.entity.Ecg2;
import com.zkhk.entity.Focus;
import com.zkhk.entity.FocusMem;
import com.zkhk.entity.FocusRemark;
import com.zkhk.entity.Message;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Odoc;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Omem;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.util.ByteToInputStream;
import com.zkhk.util.ImageUtils;
import com.zkhk.util.TimeUtil;


@Repository(value = "focusDao")
public class FocusDaoImpl implements FocusDao {
	@Resource
	private JdbcService jdbcService;
	@Resource(name = "mongoEntityDao")
	private MongoEntityDao mongoEntityDao;
	public List<Focus> findMyFocusByMemberId(int memberId) throws Exception {
		String sql = " SELECT a.newsLetter,a.id,a.focusType,a.memberId,a.focusId,a.focusStatus,a.focusP,a.focusPed,a.tag,b.MemName,b.Tel,b.Address,b.idCard,b.Gender,b.MarryStatus,b.EducationStatus,b.Occupation,b.Email,b.HeadAddress FROM m_focus_info a LEFT JOIN omem b ON a.focusId = b.Memberid WHERE b.UseTag = 'T' AND a.Tag != 'Y' AND a.Memberid = ? ORDER BY a.createTime DESC ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId});
		List<Focus> list = new ArrayList<Focus>();
		while (rowSet.next()) {
			Focus mem = new Focus();
			mem.setFocusId(rowSet.getInt("focusId"));
			mem.setFocusP(rowSet.getString("focusP"));
			mem.setFocusStatus(rowSet.getInt("focusStatus"));
			mem.setFocusType(rowSet.getInt("focusType"));
			mem.setId(rowSet.getInt("id"));
			mem.setMemberId(rowSet.getInt("memberId"));
			
			if (mem.getMemberId() == memberId) {
				// 获取备注与关注信息
				getRemark(memberId, mem.getFocusId(), mem);
				//System.out.println(memberId+"======"+mem.getFocusId());
//				Omem omem=new Omem();
//				omem.setMemberId(mem.getFocusId());
//			   jdbcService.getHeadImg(omem);
//			   mem.setHeadImg(omem.getHeadImg());
			} else {
//				Omem omem=new Omem();
//				omem.setMemberId(mem.getMemberId());
//			    jdbcService.getHeadImg(omem);
//			    mem.setHeadImg(omem.getHeadImg());
				getRemark(memberId, mem.getMemberId(), mem);
			}
			mem.setHeadAddress(rowSet.getString("HeadAddress"));
			if(mem.getHeadAddress()!=null&&!mem.getHeadAddress().equals("")){
				try {
					GridFSDBFile file=mongoEntityDao.retrieveFileOne("headImage", new ObjectId(mem.getHeadAddress()));
					mem.setHeadImg(ImageUtils.encodeImgageToBase64(ByteToInputStream.input2byte(file.getInputStream())));
				} catch (Exception e) {
				}
			}
		
			// mem.setRelation(rowSet.getString("relation"));
			// mem.setRemark(rowSet.getString("remark"));
			mem.setTag(rowSet.getString("tag"));
			mem.setAddress(rowSet.getString("address"));
			mem.setName(rowSet.getString("MemName"));
			mem.setTel(rowSet.getString("tel"));
			mem.setFocusPed(rowSet.getString("focusPed"));
			mem.setIdCard(rowSet.getString("idCard"));
			
			// System.out.println(mem.getHeadImg());
			String educationStatus = rowSet.getString("EducationStatus");
            //默认设置教育状况为：0：未知
            int educationStatusNew = 0;
            if(educationStatus != null && !"".equals(educationStatus))
            {
                educationStatusNew = Integer.valueOf(educationStatus);
            }
            mem.setEducationStatus(educationStatusNew);
			mem.setEmail(rowSet.getString("Email"));
			mem.setGender(rowSet.getString("Gender"));
			mem.setMarryStatus(rowSet.getString("MarryStatus"));
			mem.setOccupation(rowSet.getString("Occupation"));
			mem.setNewsLetter(rowSet.getInt("newsLetter"));
			list.add(mem);
			
		}
		return list;
	}

	/**
	 * 获取备注信息
	 * 
	 * @param memberId
	 * @param focusId
	 */
	private void getRemark(int memberId, int remarkId, Focus mem) {
		String sql = "select remark,relation from m_focus_remark where memberId=? and remarkId=? ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId,
				remarkId });
		if (rowSet.next()) {
			mem.setRelation(rowSet.getString("relation"));
			mem.setRemark(rowSet.getString("remark"));
		}

	}

	public void updateFocusByParam(Focus mem) throws Exception {
		String sql = "update m_focus_info set focusStatus = ?,focusP = ?,focusPed = ?,tag =?,createTime=?,newsLetter=? where id=?";
		jdbcService.doExecuteSQL(sql,new Object[] { mem.getFocusStatus(), mem.getFocusP(),
						mem.getFocusPed(), mem.getTag(),mem.getCreateTime(), mem.getNewsLetter(),mem.getId() });

	}

	public List<FocusMem> getFocusMemsByParam(String ifWhere, int memberId,
			int page) throws Exception {
		String sql = "select Memberid,MemName,Address, tel,idCard ,Gender,MarryStatus,EducationStatus,Occupation,Email ,HeadAddress from omem where UseTag='T' and Memberid not in(SELECT focusId from m_focus_info where Memberid=? and focusStatus<>3  and Tag='N') and Memberid<> ? and (MemName like '%"
				+ ifWhere
				+ "%' or tel like '%"
				+ ifWhere
				+ "%' or  Email like '%"
				+ ifWhere
				+ "%' or  idCard like '%"
				+ ifWhere + "%') order by  memberId desc limit ?,?";
		List<FocusMem> focusMems = new ArrayList<FocusMem>();
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId,
				memberId, (page - 1) * 20, 20 });
		while (rowSet.next()) {
			FocusMem mem = new FocusMem();
			mem.setAddress(rowSet.getString("address"));
			mem.setMemberId(rowSet.getInt("MemberId"));
			mem.setName(rowSet.getString("MemName"));
			mem.setTel(rowSet.getString("tel"));
			mem.setIdCard(rowSet.getString("idCard"));
			Omem omem=new Omem();
			omem.setMemberId(mem.getMemberId());
			omem.setHeadAddress(rowSet.getString("HeadAddress"));
		    //jdbcService.getHeadImg(omem);
			// mem.setHeadImg(omem.getHeadImg());
			if(omem.getHeadAddress()!=null && !omem.getHeadAddress().equals("")){
				try {
					GridFSDBFile file=mongoEntityDao.retrieveFileOne("headImage", new ObjectId(omem.getHeadAddress()));
					mem.setHeadImg(ImageUtils.encodeImgageToBase64(ByteToInputStream.input2byte(file.getInputStream())));
				} catch (Exception e) {
				}
			}
	        String educationStatus = rowSet.getString("EducationStatus");
	        //默认设置教育状况为：0：未知
	        int educationStatusNew = 0;
	        if(educationStatus != null && !"".equals(educationStatus))
	        {
	            educationStatusNew = Integer.valueOf(educationStatus);
	        }
			mem.setEducationStatus(educationStatusNew);
			mem.setEmail(rowSet.getString("Email"));
			mem.setGender(rowSet.getString("Gender"));
			mem.setMarryStatus(rowSet.getString("MarryStatus"));
			mem.setOccupation(rowSet.getString("Occupation"));
			focusMems.add(mem);

		}
		return focusMems;
	}

	public List<FocusMem> getInviteFocuseMem(String ifWhere, int memberId,
			int page) throws Exception {
		String sql = "select  Memberid,MemName,Address, tel,idCard ,Gender,MarryStatus,EducationStatus,Occupation,Email,HeadAddress from omem where UseTag='T' and Memberid not in(SELECT memberId from m_focus_info where focusid=? and focusStatus<>3 and Tag='N') and Memberid<> ? and (MemName like '%"
				+ ifWhere
				+ "%' or tel like '%"
				+ ifWhere
				+ "%' or  Email like '%"
				+ ifWhere
				+ "%' or  idCard like '%" 
				+ifWhere+ "%') order by  memberId desc limit ?,?";
		List<FocusMem> focusMems = new ArrayList<FocusMem>();
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId,
				memberId, (page - 1) * 20, 20 });
		while (rowSet.next()) {
			FocusMem mem = new FocusMem();
			mem.setAddress(rowSet.getString("address"));
			mem.setMemberId(rowSet.getInt("MemberId"));
			mem.setName(rowSet.getString("MemName"));
			mem.setTel(rowSet.getString("tel"));
			mem.setIdCard(rowSet.getString("idCard"));
			Omem omem=new Omem();
			omem.setMemberId(mem.getMemberId());
			// jdbcService.getHeadImg(omem);
			  // mem.setHeadImg(omem.getHeadImg());
			omem.setHeadAddress(rowSet.getString("HeadAddress"));
			if(omem.getHeadAddress()!=null&&!omem.getHeadAddress().equals("")){
				try {
					GridFSDBFile file=mongoEntityDao.retrieveFileOne("headImage", new ObjectId(omem.getHeadAddress()));
					mem.setHeadImg(ImageUtils.encodeImgageToBase64(ByteToInputStream.input2byte(file.getInputStream())));
				} catch (Exception e) {
				}
			}
			String educationStatus = rowSet.getString("EducationStatus");
            //默认设置教育状况为：0：未知
            int educationStatusNew = 0;
            if(educationStatus != null && !"".equals(educationStatus))
            {
                educationStatusNew = Integer.valueOf(educationStatus);
            }
            mem.setEducationStatus(educationStatusNew);
			mem.setEmail(rowSet.getString("Email"));
			mem.setGender(rowSet.getString("Gender"));
			mem.setMarryStatus(rowSet.getString("MarryStatus"));
			mem.setOccupation(rowSet.getString("Occupation"));
			focusMems.add(mem);

		}
		return focusMems;
	}

	public List<Focus> getMyFocusedByParam(int memberId) throws Exception {
		String sql = "select a.id,a.focusType,a.memberId,a.focusId,a.focusP ,a.focusPed ,a.focusStatus,b.MemName,b.Tel,b.Address,b.idCard, b.Gender,b.MarryStatus,b.EducationStatus,b.Occupation,b.Email,b.HeadAddress from m_focus_info  a left join omem b on( a.focusId=b.Memberid and a.memberId=?) where  a.memberId=? and a.focusStatus != 3 and a.tag='N' ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId,
				memberId });
		List<Focus> list = new ArrayList<Focus>();
		while (rowSet.next()) {
			Focus mem = new Focus();
			mem.setFocusId(rowSet.getInt("focusId"));
			mem.setFocusP(rowSet.getString("focusP"));
			mem.setFocusStatus(rowSet.getInt("focusStatus"));
			mem.setFocusType(rowSet.getInt("focusType"));
			mem.setId(rowSet.getInt("id"));
			mem.setMemberId(rowSet.getInt("memberId"));
			// 获取备注信息
			getRemark(memberId, mem.getFocusId(), mem);

			// mem.setRelation(rowSet.getString("relation"));
			// mem.setRemark(rowSet.getString("remark"));
			mem.setAddress(rowSet.getString("address"));
			mem.setName(rowSet.getString("MemName"));
			mem.setTel(rowSet.getString("tel"));
			mem.setFocusPed(rowSet.getString("focusPed"));
			mem.setIdCard(rowSet.getString("idCard"));
			String educationStatus = rowSet.getString("EducationStatus");
            //默认设置教育状况为：0：未知
            int educationStatusNew = 0;
            if(educationStatus != null && !"".equals(educationStatus))
            {
                educationStatusNew = Integer.valueOf(educationStatus);
            }
            mem.setEducationStatus(educationStatusNew);
			mem.setEmail(rowSet.getString("Email"));
			mem.setGender(rowSet.getString("Gender"));
			mem.setMarryStatus(rowSet.getString("MarryStatus"));
			mem.setOccupation(rowSet.getString("Occupation"));
			Omem omem=new Omem();
			omem.setMemberId(mem.getMemberId());
//		   jdbcService.getHeadImg(omem);
//		   mem.setHeadImg(omem.getHeadImg());
			
			omem.setHeadAddress(rowSet.getString("HeadAddress"));
			if(mem.getHeadAddress()!=null&&!mem.getHeadAddress().equals("")){
				try {
					GridFSDBFile file=mongoEntityDao.retrieveFileOne("headImage", new ObjectId(mem.getHeadAddress()));
					mem.setHeadImg(ImageUtils.encodeImgageToBase64(ByteToInputStream.input2byte(file.getInputStream())));
				} catch (Exception e) {
				}
			}
			list.add(mem);
		}
		return list;
	}

	public List<Focus> getFocusedMeByParam(int memberId) throws Exception {
		String sql = "select a.id,a.focusType,a.focusStatus,a.memberId,a.focusId,a.focusP ,a.focusPed ,b.MemName,b.Tel,b.Address,b.idCard ,b.Gender,b.MarryStatus,b.EducationStatus,b.Occupation,b.Email ,b.HeadAddress from m_focus_info  a left join omem b on( a.memberId=b.Memberid and a.focusId=?) where  a.focusId=? and b.UseTag = 'T' and a.tag='N' ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId,
				memberId });
		List<Focus> list = new ArrayList<Focus>();
		while (rowSet.next()) {
			Focus mem = new Focus();
			mem.setFocusId(rowSet.getInt("focusId"));
			mem.setFocusP(rowSet.getString("focusP"));
			mem.setFocusStatus(rowSet.getInt("focusStatus"));
			mem.setFocusType(rowSet.getInt("focusType"));
			mem.setId(rowSet.getInt("id"));
			mem.setMemberId(rowSet.getInt("memberId"));
			getRemark(mem.getFocusId(), mem.getMemberId(), mem);
			mem.setAddress(rowSet.getString("address"));
			mem.setName(rowSet.getString("MemName"));
			mem.setTel(rowSet.getString("tel"));
			mem.setFocusPed(rowSet.getString("focusPed"));
			mem.setIdCard(rowSet.getString("idCard"));
			String educationStatus = rowSet.getString("EducationStatus");
            //默认设置教育状况为：0：未知
            int educationStatusNew = 0;
            if(educationStatus != null && !"".equals(educationStatus))
            {
                educationStatusNew = Integer.valueOf(educationStatus);
            }
            mem.setEducationStatus(educationStatusNew);
			mem.setEmail(rowSet.getString("Email"));
			mem.setGender(rowSet.getString("Gender"));
			mem.setMarryStatus(rowSet.getString("MarryStatus"));
			mem.setOccupation(rowSet.getString("Occupation"));
//			Omem omem=new Omem();
//			omem.setMemberId(mem.getMemberId());
//		   jdbcService.getHeadImg(omem);
//		   mem.setHeadImg(omem.getHeadImg());
			mem.setHeadAddress(rowSet.getString("HeadAddress"));
			if(mem.getHeadAddress()!=null&&!mem.getHeadAddress().equals("")){
			try {
				GridFSDBFile file=mongoEntityDao.retrieveFileOne("headImage", new ObjectId(mem.getHeadAddress()));
				mem.setHeadImg(ImageUtils.encodeImgageToBase64(ByteToInputStream.input2byte(file.getInputStream())));
			} catch (Exception e) {
			}
			}
			list.add(mem);
		}
		return list;
	}

	public int addFocusByParam(Focus mem) throws Exception {
		int tag = isExitFocus(mem);
		if (tag == 0) {
			String sql = "INSERT INTO m_focus_info ( focusType ,memberId ,focusId ,focusStatus ,focusP  ,tag ,createTime,newsLetter) VALUES (?,?,?,1,?,'N',?,?)";
			jdbcService.doExecuteSQL(sql,new Object[] { mem.getFocusType(), mem.getMemberId(),
							mem.getFocusId(), mem.getFocusP(),
							mem.getCreateTime(),mem.getNewsLetter() });
			return jdbcService.getMaxId("m_focus_info", "id");
		} else {
			return tag;
		}
	}

	/**
	 * 判断该关注信息是否是已经存在并取消
	 * 
	 * @param mem
	 * @return
	 */
	private int isExitFocus(Focus mem) {
		String sql = "select id from m_focus_info where memberId=? and focusId=?";
		SqlRowSet rowSet = jdbcService.query(sql,new Object[]{mem.getMemberId(),mem.getFocusId()});
		if (rowSet.next()) {
			// 修改该取消的关注记录
			int id = rowSet.getInt("id");
			sql = "update m_focus_info set focusType=?, focusStatus = ?,focusP = ?,focusPed = ?,tag =?,	createTime=? where id=?";
			jdbcService.doExecuteSQL(sql, new Object[] { mem.getFocusType(),
					mem.getFocusStatus(), mem.getFocusP(), mem.getFocusPed(),
					"N", mem.getCreateTime(),id });
			return id;
		}
		return 0;
	}

	/**
	 * 通过会员id查询医生信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Set<Odoc> getCustodyDoc(int memberId) throws Exception {
		// String
		// sql="select d.Docid,d.BirthDate,d.CertiType,d.Email,d.Gender,d.DocName,d.RoleId,d.Tag,d.OrgId,d.Tel from odoc d,omem m where m.Docid = d.Docid and m.MemberId=? ";
		String sql = "SELECT d.Docid,d.BirthDate,d.CertiType,d.Email,d.Gender,d.DocName,d.RoleId,d.Tag,d.OrgId,d.Tel,d.title from odoc d where Docid  in ( SELECT DISTINCT docid from dgp1 where OrgId in (select OrgId from  odmt where MemGrpid in   (Select MemGrpid from ompb where Memberid=?)))";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId });
		Set<Odoc> odocs = new HashSet<Odoc>();
		while (rowSet.next()) {
			Odoc doctor = new Odoc();
			doctor.setDoctorId(rowSet.getInt("Docid"));
			doctor.setBirthDate(rowSet.getString("BirthDate"));
			doctor.setCertiType(rowSet.getInt("CertiType"));
			doctor.setEmail(rowSet.getString("Email"));
			doctor.setGender(rowSet.getString("Gender"));
			doctor.setDoctorName(rowSet.getString("DocName"));
			doctor.setRoleId(rowSet.getInt("RoleId"));
			doctor.setTag(rowSet.getString("Tag"));
			doctor.setTitle(rowSet.getString("Title"));
			doctor.setOrgId(rowSet.getInt("OrgId"));
			doctor.setTel(rowSet.getString("Tel"));
			// return doctor;
			odocs.add(doctor);
		}
		return odocs;
	}

	public void updateFocusRemarkByParam(FocusRemark remark)throws Exception {
		String sql = "select 1 from m_focus_remark where memberId=? and remarkId=?";
		SqlRowSet rowSet = jdbcService.query(sql,
				new Object[] { remark.getMemberId(), remark.getRemarkId() });
		if (rowSet.next()) {
			sql = "update m_focus_remark set relation=?,remark=? where memberId=? and remarkId=?";
			jdbcService.doExecuteSQL(sql,
					new Object[] { remark.getRelation(), remark.getRemark(),
							remark.getMemberId(), remark.getRemarkId() });
		} else {
			sql = "INSERT INTO m_focus_remark ( memberId ,remarkId ,relation ,remark ) VALUES (?,?,?,?)";
			jdbcService.doExecuteSQL(sql,
					new Object[] { remark.getMemberId(), remark.getRemarkId(),
							remark.getRelation(), remark.getRemark() });
		}

	}

	public byte[] getHeadImgByMemberId(int memberId)throws Exception {
		
	return null;
		
	}

	public List<Oecg> findEcgbyMemberIdAndTime( String eventIds
		) throws Exception {
		String sql = "select b.Docentry, b.sdnn,b.pnn50,b.hrvi, b.BluetoothMacAddr, b.DeviceCode, a.eventType ,b.fs, b.memberId, b.measTime,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
				+ " from omds a left join oecg b on a.eventId=b.eventid where  b.eventId in ("+eventIds+") and b.delTag='0' order by  b.measTime desc  ";
		List<Oecg> list = new ArrayList<Oecg>();
		SqlRowSet rowSet = jdbcService.query(sql);
		while (rowSet.next()) {
			Oecg oecg = new Oecg();
			oecg.setId(rowSet.getLong("Docentry"));    
			oecg.setFs(rowSet.getInt("fs"));
			oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
			oecg.setEcgResult(rowSet.getInt("ECGResult"));
			//如果存在异常，查询异常像
			if(oecg.getEcgResult()>0){
				List<Ecg2> ecg2s = findEcg2ById(oecg.getId() + "");
				oecg.setEcg2s(JSON.toJSONString(ecg2s));
			}
			oecg.setEventId(rowSet.getLong("EventId"));
			oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
			oecg.setFastestTime(rowSet.getInt("FastestTime"));
			oecg.setFrepPsd(rowSet.getString("FreqPSD"));
			oecg.setHeartNum(rowSet.getInt("HeartNum"));
			oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("measTime")));
			oecg.setRawEcg(rowSet.getString("RawECG"));
			oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
			oecg.setRrInterval(rowSet.getString("RRInterval"));
			oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
			oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
			oecg.setStatusTag(rowSet.getInt("StatusTag"));
			oecg.setTimeLength(rowSet.getInt("TimeLength"));
			oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
			oecg.setMemberId(rowSet.getInt("memberId"));
			oecg.setPnn50(rowSet.getFloat("pnn50"));
			oecg.setSdnn(rowSet.getFloat("sdnn"));
			oecg.setHrvi(rowSet.getFloat("hrvi"));
			oecg.setDeviceCode(rowSet.getString("DeviceCode"));
			oecg.setSdnnLevel(rowSet.getInt("sdnnLevel"));
			oecg.setPnn50Level(rowSet.getInt("pnn50Level"));
			oecg.setHrviLevel(rowSet.getInt("hrviLevel"));
			oecg.setRmssdLevel(rowSet.getInt("rmssdLevel"));
			oecg.setTpLevel(rowSet.getInt("tplevel"));
			oecg.setVlfLevel(rowSet.getInt("vlfLevel"));
			oecg.setLfLevel(rowSet.getInt("lfLevel"));
			oecg.setHfLevel(rowSet.getInt("hfLevel"));
			oecg.setLhrLevel(rowSet.getInt("lhrLevel"));
			oecg.setHrLevel(rowSet.getInt("hrLevel"));
			list.add(oecg);
		}
		return list;
	}

	private List<Ecg2> findEcg2ById(String ids) throws Exception {
		List<Ecg2> ecgs = new ArrayList<Ecg2>();
		String sql = "select Docentry, abnName ,abnNum from ecg2 where Docentry in ("
				+ ids + ")";
		SqlRowSet rowSet = jdbcService.query(sql);
		while (rowSet.next()) {
			Ecg2 ecg = new Ecg2();
			ecg.setAbnName(rowSet.getString("abnName"));
			ecg.setAbnNum(rowSet.getInt("abnNum"));
			ecg.setId(rowSet.getLong("Docentry"));
			ecg.setEcgs(findAnomalyEcgbyId(ecg.getId(), ecg.getAbnName()));
			ecgs.add(ecg);
		}
		return ecgs;
	}
	public List<AnomalyEcg> findAnomalyEcgbyId(long id, String name)
			throws Exception {
		List<AnomalyEcg> ecgs = new ArrayList<AnomalyEcg>();
		String useNameString = getName(name);
		String sql = "select ObjectId,AbECGType,AbECGTime from ecg1 where Docentry=? and AbECGType like '%"+ useNameString+ "%'";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { id });
		while (rowSet.next()) {
			AnomalyEcg ecg = new AnomalyEcg();
			ecg.setAbEcgTime(rowSet.getString("AbECGTime"));
			ecg.setAbEcgType(rowSet.getString("AbECGType"));
			ecg.setObjectId(rowSet.getString("ObjectId"));
			ecgs.add(ecg);
		}
		return ecgs;
	}
	
	private String getName(String name) {
		if ("Polycardia".equals(name)) {
               name="ST";
		} else if ("Bradycardia".equals(name)) {
               name="SB";
		} else if ("Arrest".equals(name)) {
			 name="SA";
		} else if ("Missed".equals(name)) {
			 name="MB";
		} else if ("Wide".equals(name)) {
			 name="WS";
		} else if ("PVB".equals(name)) {
			 name="VPB";
		}
		else if ("APB".equals(name)) {
			 name="APB";
		}
		else if ("Insert_PVB".equals(name)) {
			 name="IVBP";
		}
		else if ("VT".equals(name)) {
			 name="VT";
		}
		else if ("Bigeminy".equals(name)) {
			 name="BG";
		}
		else if ("Trigeminy".equals(name)) {
			 name="TRG";
		}else if("Arrhythmia".equals(name)) {
			 name="AR";
		}
		return name;
	}

	
	public List<Oppg> findPpgbyMemberIdAndTime(String eventIds) throws Exception {
		String sql = "select b.memberId ,b.BluetoothMacAddr, b.DeviceCode, b.fs, b.Docentry,b.EventId,b.TimeLength,b.UploadTime,b.MeasureTime,b.PulsebeatCount,b.SlowPulse,b.SlowTime,b.QuickPulse,b.QuickTime,b.PulseRate,b.Spo,b.SPO1,b.CO,b.SI,b.SV,b.CI,b.SPI,b.K,b.K1,b.V,b.TPR,b.PWTT,b.Pm,b.AC,"
				+ "  b.ImageNum,b.AbnormalData,b.Ppgrr,b.RawPPG,b.PPGResult,b.StatusTag ,b.kLevel,b.svLevel,b.coLevel,b.siLevel,b.vLevel,b.tprLevel,b.spoLevel,b.ciLevel,b.spiLevel,b.pwttLevel,b.acLevel,b.prLevel"
				+ " from omds a left join oppg b on a.eventId=b.eventid where b.eventId in ("+eventIds+") and b.delTag='0' order by b.MeasureTime desc  ";
		List<Oppg> list = new ArrayList<Oppg>();
		SqlRowSet rowSet = jdbcService.query(sql);
		while (rowSet.next()) {
			Oppg oppg = new Oppg();
			oppg.setFs(rowSet.getInt("fs"));
			oppg.setAbnormalData(rowSet.getInt("AbnormalData"));
			oppg.setAc(rowSet.getFloat("AC"));
			oppg.setCi(rowSet.getFloat("CI"));
			oppg.setCo(rowSet.getFloat("CO"));
			oppg.setEventId(rowSet.getLong("EventId"));
			oppg.setId(rowSet.getLong("Docentry"));
			oppg.setImageNum(rowSet.getInt("ImageNum"));
			oppg.setK(rowSet.getFloat("K"));
			oppg.setK1(rowSet.getFloat("K1"));
			oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("MeasureTime")));
			oppg.setPm(rowSet.getFloat("Pm"));
			oppg.setPpgResult(rowSet.getInt("PPGResult"));
			oppg.setPpgrr(rowSet.getString("Ppgrr"));
			oppg.setPulsebeatCount(rowSet.getInt("PulsebeatCount"));
			oppg.setPulseRate(rowSet.getInt("PulseRate"));
			oppg.setPwtt(rowSet.getFloat("PWTT"));
			oppg.setQuickPulse(rowSet.getInt("QuickPulse"));
			oppg.setQuickTime(rowSet.getInt("QuickTime"));
			oppg.setRawPpg(rowSet.getString("RawPPG"));
			oppg.setSi(rowSet.getFloat("SI"));
			oppg.setSlowPulse(rowSet.getInt("SlowPulse"));
			oppg.setSlowTime(rowSet.getInt("SlowTime"));
			oppg.setSpi(rowSet.getFloat("SPI"));
			oppg.setSpo(rowSet.getInt("Spo"));
			oppg.setSpo1(rowSet.getInt("SPO1"));
			oppg.setStatusTag(rowSet.getInt("StatusTag"));
			oppg.setSv(rowSet.getFloat("SV"));
			oppg.setTimeLength(rowSet.getInt("TimeLength"));
			oppg.setTpr(rowSet.getFloat("TPR"));
			oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
			oppg.setV(rowSet.getFloat("V"));
			oppg.setMemberId(rowSet.getInt("memberId"));
			oppg.setDeviceCode(rowSet.getString("DeviceCode"));
			oppg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			oppg.setK1Level(rowSet.getInt("kLevel"));
			oppg.setSvLevel(rowSet.getInt("svLevel"));
			oppg.setCoLevel(rowSet.getInt("coLevel"));
			oppg.setAcLevel(rowSet.getInt("acLevel"));
			oppg.setSiLevel(rowSet.getInt("siLevel"));
			oppg.setV1Level(rowSet.getInt("vLevel"));
			oppg.setTprLevel(rowSet.getInt("tprLevel"));
			oppg.setSpoLevel(rowSet.getInt("spoLevel"));
			oppg.setCiLevel(rowSet.getInt("ciLevel"));
			oppg.setSpiLevel(rowSet.getInt("spiLevel"));
			oppg.setPwttLevel(rowSet.getInt("pwttLevel"));

			list.add(oppg);
		}
		return list;
	}

	public List<Obsr> findBsrbyMemberIdAndTime(String eventIds) throws Exception {
	String sql = "select b.memberId, b.Docentry,b.EventId,b.Memberid,b.UploadTime,b.TestTime,b.BsValue,b.timePeriod,b.AnalysisResult"
			+ " from omds a left join obsr b on a.eventId=b.eventId where b.eventId in ("+eventIds+") and  b.delTag='0' order by b.TestTime desc";
	List<Obsr> list = new ArrayList<Obsr>();
	SqlRowSet rowSet = jdbcService.query(sql);
	while (rowSet.next()) {
		Obsr osbr = new Obsr();
		osbr.setAnalysisResult(rowSet.getInt("AnalysisResult"));
		osbr.setBsValue(rowSet.getFloat("BsValue"));
		osbr.setEventId(rowSet.getLong("EventId"));
		osbr.setId(rowSet.getLong("Docentry"));
		osbr.setMemberId(rowSet.getInt("memberId"));
		osbr.setTestTime(TimeUtil.paserDatetime2(rowSet.getString("TestTime")));
		osbr.setTimePeriod(rowSet.getInt("timePeriod"));
		osbr.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
		list.add(osbr);
	}
	return list;
	}

	public List<Osbp> findSbpbyMemberIdAndTime(
			String evenIds) throws Exception {

	String sql ="select b.memberId, b.Docentry,b.EventId,b.UploadTime,b.TestTime,b.Abnormal,b.timePeriod,b.SBP,b.DBP,b.PulseRate "
			+ "from omds a left join osbp b on a.eventId=b.eventid where b.eventId in ("+evenIds+") and b.delTag='0' order by b.TestTime desc";
	List<Osbp> list = new ArrayList<Osbp>();
	SqlRowSet rowSet = jdbcService.query(sql);
	while (rowSet.next()) {
		Osbp osbp = new Osbp();
		osbp.setAbnormal(rowSet.getString("Abnormal"));
		osbp.setDbp(rowSet.getInt("DBP"));
		osbp.setEventId(rowSet.getLong("EventId"));
		osbp.setId(rowSet.getLong("Docentry"));
		osbp.setPulseRate(rowSet.getInt("PulseRate"));
		osbp.setSbp(rowSet.getInt("SBP"));
		osbp.setTestTime(TimeUtil.paserDatetime2(rowSet.getString("TestTime")));
		osbp.setTimePeriod(rowSet.getInt("timePeriod"));
		osbp.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
		osbp.setMemberId(rowSet.getInt("memberId"));
		list.add(osbp);
	}
	return list;
 }

	public String getEventsByParam(int focusId, String startTime,
			String endTime, int page) throws Exception {
		String sql="select eventId from  omds where memberId=? and  uploadTime>? and uploadTime<? order by eventId desc limit ?,? ";
		SqlRowSet rowSet=jdbcService.query(sql, new Object[]{focusId,startTime,endTime,(page-1)*20,20});
		StringBuffer eventIds=new StringBuffer("");
		while(rowSet.next()){
			eventIds.append(rowSet.getInt("eventId")).append(",");
		}
		return eventIds.toString();
	}

	public int getFocusCount(int memberId) throws Exception {
	     String sql="select  tagTime from m_focus_tag where memberId=?";
	     SqlRowSet rowSet=jdbcService.query(sql, new Object[]{memberId});
	     if(rowSet.next()){
	    	 String tagTime=rowSet.getString("tagTime");
	    	 sql="SELECT count(1) num from m_focus_info where (memberId=? or focusId=?) and createTime>=?";
	    	return jdbcService.queryForInt(sql,new Object[]{memberId,memberId,tagTime});	  
	     }else {
	    	sql="SELECT count(1) num from m_focus_info where memberId=? or focusId=?";
	    	return jdbcService.queryForInt(sql,new Object[]{memberId,memberId});
		}
	
	}

	public synchronized  void   saveUpdateTag(int memberId, String tagTime)throws Exception {
		int tag = isExitFocusTag(memberId);
		String sql;
		if (tag == 0) {
			 sql = "INSERT INTO m_focus_tag ( tagTime ,memberId) VALUES (?,?)";
		} else {
			 sql="update m_focus_tag set tagTime=? where memberId=?";
		}
		  jdbcService.doExecuteSQL(sql,new Object[] { tagTime ,memberId });
	}

	private int isExitFocusTag(int memberId) {
		String sql="SELECT count(1) from m_focus_tag where memberId=?";
		return jdbcService.queryForInt(sql, new Object[]{memberId});
	}

}


