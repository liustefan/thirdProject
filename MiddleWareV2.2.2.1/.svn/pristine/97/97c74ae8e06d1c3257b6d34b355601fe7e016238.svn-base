package com.zkhk.dao.Impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.zkhk.constants.Constants;
import com.zkhk.dao.MemDao;
import com.zkhk.entity.MemFile;
import com.zkhk.entity.MemLog;
import com.zkhk.entity.MemberStatus;
import com.zkhk.entity.Odoc;
import com.zkhk.entity.Omem;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.util.PasswordEncryption;
import com.zkhk.util.TimeUtil;

@Repository(value = "memDao")
public class MemDaoImpl implements MemDao {
	@Resource
	private JdbcService jdbcService;
	@Resource(name = "mongoEntityDao")
	private MongoEntityDao mongoEntityDao;

	public MemLog findUserbyNameAndPassWord(MemLog log) throws Exception {
		String sql = " SELECT o.Memberid,a.CURTAG,a.PassWord FROM omem_account b left join memlog a on b.Memberid = a.MemberId  left join  omem o "
                   + " on o.Memberid = a.MemberId WHERE b.Status = 2 AND o.useTag='T' AND b.Account = ? ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { log.getUserAccount() });
		while (rowSet.next()) {
			MemLog log2 = new MemLog();
			log2.setMemberId(rowSet.getInt("Memberid"));
			log2.setCurTag(rowSet.getString("CURTAG"));
			log2.setPassWord(rowSet.getString("PassWord"));
			return log2;
		}
		return null;
	}

	public void updateMemLogByMemberid(MemLog loginLog) throws Exception {
		String sql = "UPDATE memlog SET SESSION=?,LOGINTIME=?,DEVICE=? WHERE MEMBERID=?";
		jdbcService.doExecuteSQL(sql,new Object[] { loginLog.getSession(), loginLog.getLoginTime(), loginLog.getDevice(),
						loginLog.getMemberId() });

	}

	public boolean findUserBySessionAndId(String session, int memberId) throws Exception {
		String sql = "SELECT 1 FROM memlog WHERE SESSION=? AND MEMBERID=?";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { session, memberId });
		while (rowSet.next()) {
			return true;
		}

		return false;
	}

	/** 
	 * <p>Title: findOmembyId</p>  
	 * <p>Description: 根据会员id查询会员信息</p>  
	 * @author liuxiaoqin
     * @createDate 2016-04-14
	 * @param memberId
	 * @return
	 * @throws Exception 
	 * @see com.zkhk.dao.MemDao#findOmembyId(int)  
	*/
	public Omem findOmembyId(int memberId) throws Exception {
		String sql = " SELECT a.Memberid,a.MemName,a.Gender,a.BirthDate,a.NativeAddr,a.Tel,a.HeadAddress,a.LogName,a.OrgId,a.MemId, "
                   + " a.Email,a.IdCard,a.Address,a.MarryStatus,a.EducationStatus,a.Occupation,a.Docid,a.DocName,a.UseTag,a.CreateTime, "
                   + " b.ContactMobPhone,b.ContactName,c.BloodType,c.Height,c.Weight FROM omem a LEFT JOIN mem1 b ON "
                   + " a.Memberid = b.Memberid LEFT JOIN mem2 c ON a.Memberid = c.Memberid WHERE a.Memberid = ? order by b.ContactName limit 1 ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId });
		while (rowSet.next()) {
			Omem omem = new Omem();
			omem.setMemberId(rowSet.getInt("Memberid"));
			omem.setAddress(rowSet.getString("Address"));
			String birthDate = rowSet.getString("BirthDate");
			if (!StringUtils.isEmpty(birthDate)) {
				omem.setBirthDate(rowSet.getString("BirthDate"));
			}
			omem.setEducationStatus(rowSet.getInt("EducationStatus"));
			omem.setEmail(rowSet.getString("Email"));
			omem.setGender(rowSet.getString("Gender"));
			omem.setIdCard(rowSet.getString("IdCard"));
			omem.setMarryStatus(rowSet.getString("MarryStatus"));
			omem.setMemberId(memberId);
			omem.setMemId(rowSet.getInt("MemId"));
			omem.setMemName(rowSet.getString("MemName"));
			omem.setNativeAddr(rowSet.getString("NativeAddr"));
			omem.setOccupation(rowSet.getString("Occupation"));
			omem.setOrgId(rowSet.getInt("OrgId"));
			omem.setTel(rowSet.getString("Tel"));
			omem.setDocId(rowSet.getInt("DocId"));
			omem.setDocName(rowSet.getString("DocName"));
			omem.setCreateTime(TimeUtil.paserDatetime2(rowSet.getString("CreateTime")));
			omem.setHeadAddress(rowSet.getString("HeadAddress"));
			// jdbcService.getHeadImg(omem);
			omem.setVitality(findMemActiveIndexByMemberId(memberId));
			// omem.setHeadImg(ImageUtils.encodeImgageToBase64(ImageUtils.class.getResource("").getPath()
			// + "12.jpg"));
			omem.setContactMobPhone(rowSet.getString("ContactMobPhone"));
			omem.setContactName(rowSet.getString("ContactName"));
			omem.setBloodType(rowSet.getString("BloodType"));
			omem.setHeight(rowSet.getString("Height"));
			omem.setWeight(rowSet.getString("Weight"));

			return omem;
		}
		return null;
	}

	/**
	 *    * 
	 * <p>
	 * Title: findMemFilebyId
	 * </p>
	 *     
	 * <p>
	 * Description:根据memberId来查询会员健康档案信息 
	 * </p>
	 *   
	 * 
	 * @author liuxiaoqin
	 * @createDate 2016-03-08  @param memberId  @return  @throws Exception 
	 *              * @see com.zkhk.dao.MemDao#findMemFilebyId(int)  
	 */
	public MemFile findMemFilebyId(int memberId) throws Exception {
		String sql = " select distinct a.BloodType,a.AllergicHis,a.AllergicHisName,a.Height,a.Weight,a.Waist,a.Hip,a.Pulse,a.HeartRate,a.BloodH,a.BloodL,a.FastingGlucose, "
				+ " a.UricAcid,a.TotalCholesterol,a.Triglyceride,a.DensityLipop,a.LDLip from mem2 a where a.Memberid = ? ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId });
		while (rowSet.next()) {
			MemFile file = new MemFile();
			String hasAllergicHis = rowSet.getString("AllergicHis");
			if(!StringUtils.isEmpty(hasAllergicHis)){
				String allergic = "";
				if(hasAllergicHis.equals("Y")){
					String allergicHisName = rowSet.getString("AllergicHisName");
					if(!StringUtils.isEmpty(allergicHisName)){
						allergic = "有" + Constants.LEFT_BRACKET + allergicHisName + Constants.RIGHT_BRACKET;
					}else{
						allergic = "有";
					}
				}else{
					allergic = "无";
				}
				file.setAllergic(allergic);
			}
			file.setBloodH(rowSet.getInt("BloodH"));
			file.setBloodL(rowSet.getInt("BloodL"));
			String bloodType = rowSet.getString("BloodType");
            if(StringUtils.isEmpty(bloodType)){
            	bloodType = Constants.BLOOD_TYPE_UNKNOWN;
            }
			file.setBloodType(bloodType);
			file.setDensityLipop(rowSet.getFloat("DensityLipop"));
			file.setFastingGlucose(rowSet.getFloat("FastingGlucose"));
			file.setHeartRate(rowSet.getInt("HeartRate"));
			file.setHeight(rowSet.getInt("Height"));
			file.setHip(rowSet.getInt("Hip"));
			file.setLdLip(rowSet.getFloat("LDLip"));
			file.setMemberId(memberId);
			file.setPulse(rowSet.getInt("Pulse"));
			file.setTotalCholesterol(rowSet.getFloat("TotalCholesterol"));
			file.setTriglyceride(rowSet.getFloat("Triglyceride"));
			file.setUricAcid(rowSet.getInt("UricAcid"));
			file.setWaist(rowSet.getInt("Waist"));
			file.setWeight(rowSet.getFloat("Weight"));
			return file;
		}
		return null;
	}

	/** 
     * <p>Title: updateOmembyId</p>  
     * <p>Description: 更新会员基本信息</p>
     * @author liuxiaoqin
     * @createDate 2016-04-12  
     * @param omem
     * @throws Exception 
     * @see com.zkhk.dao.MemDao#updateOmembyId(com.zkhk.entity.Omem)  
    */
    public Integer updateOmembyId(Omem omem) throws Exception {
        int count = 0;
        StringBuffer str = new StringBuffer();
        String sql = "";
        str.append(" update omem set ");
        if(omem != null){
            String gender = omem.getGender();
            if(!StringUtils.isEmpty(gender)){
                str.append(" Gender = '" + gender + "'," );
            }
            String birthDate = omem.getBirthDate();
            if(!StringUtils.isEmpty(birthDate)){
                str.append(" BirthDate = '" + birthDate + "'," );
            }
            String idcard = omem.getIdCard();
            if(!StringUtils.isEmpty(idcard)){
                str.append(" IdCard = '" + idcard + "'," );
            }
            String nativeAddr = omem.getNativeAddr();
            if(!StringUtils.isEmpty(nativeAddr)){
                str.append(" NativeAddr = '" + nativeAddr + "'," );
            }
            String address = omem.getAddress();
            if(!StringUtils.isEmpty(address)){
                str.append(" Address = '" + address + "'," );
            }
            String marryStatus = omem.getMarryStatus();
            if(!StringUtils.isEmpty(marryStatus)){
                str.append(" MarryStatus = '" + marryStatus + "'," );
            }
            Integer educationStatus = omem.getEducationStatus();
            if(!StringUtils.isEmpty(educationStatus)){
                str.append(" EducationStatus = " + educationStatus + "," );
            }
            String occupation = omem.getOccupation();
            if(!StringUtils.isEmpty(occupation)){
                str.append(" Occupation = '" + occupation + "'," );
            }
            String tel = omem.getTel();
            if(!StringUtils.isEmpty(tel)){
                str.append(" Tel = '" + tel + "'," );
            }
            String headAddress = omem.getHeadAddress();
            if(!StringUtils.isEmpty(headAddress)){
                str.append(" headAddress = '" + headAddress + "'," );
            }
            String email = omem.getEmail();
            if(!StringUtils.isEmpty(email)){
                str.append(" Email = '" + email + "'," );
            }
            String memName = omem.getMemName();
            if(!StringUtils.isEmpty(memName)){
                str.append(" MemName = '" + memName + "' ");
            }
            str.append(" where memberId = ? ");
        }
        sql = str.toString();
        count = jdbcService.doExecuteSQL(sql, new Object[]{omem.getMemberId()});
        return count;
    }

	public int updateOmemPassWordByParam(int memberId, String oldPW, String newPW) throws Exception {
		String sql = "update memlog set passWord=? where memberId=? and passWord=? ";
		// System.out.println(PasswordEncryption.getMD5Password(oldPW+"zkhk")+"----------");
		int i = jdbcService.doExecuteSQL(sql, new Object[] { PasswordEncryption.getMD5Password(newPW + "zkhk"),
				memberId, PasswordEncryption.getMD5Password(oldPW + "zkhk") });
		return i;
	}

	public void updateOmemHeadImgbyId(MultipartFile headImg, int memberId) throws Exception {
		String sql = "update omem set headImg=? where memberId=" + memberId;
		jdbcService.updatOmemHeadImg(sql, headImg);

	}

	/**
	 * 获取会员活力指数
	 * 
	 * @author liuxiaoqin
	 * @param memberId
	 */
	public float findMemActiveIndexByMemberId(int memberId) throws Exception {
		// 获取会员最近30 天活力指数activeIndex
		float activeIndex = 0;
		String sql = "SELECT sum(score) FROM mem8 WHERE memberId = ? AND uploadTime > DATE_ADD(curdate(),INTERVAL -30 DAY) ";
		// 会员最近30 天活力累计分数(monthActiveIndex)
		int monthActiveIndex = jdbcService.queryForInt(sql, new Object[] { memberId });
		// 取所有用户
		int allMem = getAllMem();

		// 取所有用户前1/4的用户数量为
		int quarterMem = Math.round((float) allMem / 4);

		String sqlQuarter = " SELECT avg(mm.totalScore) avgTotal FROM ( SELECT sum(score)totalScore FROM mem8 WHERE uploadTime > DATE_ADD(curdate(), INTERVAL -30 DAY) GROUP BY memberId ORDER BY totalScore DESC LIMIT ? )mm ";
		// 所有会员累计积分从高到低排列，取前1/4所有用户的总积分平均值(avgActiveIndex)
		int avgActiveIndex = jdbcService.queryForInt(sqlQuarter, new Object[] { quarterMem });
		activeIndex = Math.round((float) monthActiveIndex * 100 / (float) avgActiveIndex);
		if (activeIndex > 100) {
			activeIndex = 100;
		}
		return activeIndex;
	}

	/**
	 * 获取最近30 天所有会员
	 * 
	 * @author liuxiaoqin
	 * @return
	 * @throws Exception
	 */
	public int getAllMem() throws Exception {
		int allMem = 0;
		String sqlAll = " ( SELECT 1 FROM mem8 WHERE uploadTime > DATE_ADD(curdate(), INTERVAL -30 DAY) GROUP BY memberId ) mm ";
		// 所有会员的最近30 天活力积分由高到低进行排序的总会员数
		allMem = jdbcService.getRecordCount(sqlAll);
		return allMem;
	}

	public String findNameByMemberId(int memberId) throws Exception {
		String sql = "SELECT MemName FROM omem where memberId=?";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId });
		if (rowSet.next()) {
			return rowSet.getString("MemName");
		}
		return null;
	}

	public void insertMemLogByMemberid(MemLog loginLog) throws Exception {
		String sql = "insert into memlog(memberid,password,curTag,`Session`,LoginTime,Device) values(?,?,?,?,?,?)";
		jdbcService.doExecuteSQL(
				sql,
				new Object[] { loginLog.getMemberId(), loginLog.getPassWord(), loginLog.getCurTag(),
						loginLog.getSession(), loginLog.getLoginTime(), loginLog.getDevice() });
	}

	public MemberStatus queryMemberStatus(String account) throws Exception {
		MemberStatus memberStatus = new MemberStatus();

		String sql = "SELECT o.`status` FROM omem  o WHERE (o.Memberid =? or o.TEL=? or o.Email=? or o.IdCard = ?)  and  o.useTag='T' ";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { account, account, account, account });
		while (rowSet.next()) {
			memberStatus.setStatus(rowSet.getString("status"));
			return memberStatus;
		}
		return memberStatus;
	}
	
	 /** 
     * @Title: vaildIdCard 
     * @Description: 验证身份证是否被其他人占用
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-04-23
     * @throws Exception    
     * @retrun String
     */
	public Integer vaildIdCard(String idcard) throws Exception{
		int count = 0;
		String sql = " SELECT COUNT(Memberid) FROM omem where IdCard = ? AND UseTag = 'T' ";
        count = jdbcService.queryForInt(sql, new Object[]{idcard});
		return count;
	}
	
	 /** 
     * @Title: vaildTel 
     * @Description: 验证手机号是否被其他人占用
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-04-23
     * @throws Exception    
     * @retrun String
     */
	public Integer vaildTel(String tel) throws Exception{
		int count = 0;
		String sql = " SELECT COUNT(Memberid) FROM omem where Tel = ? AND UseTag = 'T' ";
        count = jdbcService.queryForInt(sql, new Object[]{tel});
		return count;
	}
	
	 /** 
     * @Title: vaildEmail 
     * @Description: 验证邮箱是否被其他人占用
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-04-23
     * @throws Exception    
     * @retrun String
     */
	public Integer vaildEmail(String email) throws Exception{
		int count = 0;
		String sql = " SELECT COUNT(Memberid) FROM omem where Email = ? AND UseTag = 'T' ";
        count = jdbcService.queryForInt(sql, new Object[]{email});
		return count;
	}
	
	/** 
     * @Title: findSameOrgDoctors 
     * @Description: 会员查询同组织下的所有医生。
     * @param request
     * @author liuxiaoqin
     * @createDate 2016-06-30
     * @throws Exception    
     * @retrun doctorList
     */
    public List<Odoc> findSameOrgDoctors(Integer memberId,String searchName,Integer pageNow,Integer pageSize)throws Exception{
    	List<Odoc> doctorList = new ArrayList<Odoc>();
    	StringBuffer sqlAll = new StringBuffer();
	    String sql = "";
	    sqlAll.append(" SELECT d.Docid,d.Gender,d.RoleId,d.OrgId,d.DocName,d.Title,d.BirthDate,d.Tel,d.Email,d.HeadAddress ");
	    sqlAll.append(" FROM omem m LEFT JOIN odoc d ON d.OrgId = m.OrgId WHERE m.memberid = ? AND d.Tag = 'T' ");
	    if(!StringUtils.isEmpty(searchName)){
	        sqlAll.append(" AND  (d.DocName like '%"+searchName+"%' or d.Tel like '%"+searchName+"%' or d.Email like '%"+searchName+"%' ) ");
	    }
	    sqlAll.append(" ORDER BY d.DocName DESC LIMIT ?,?  ");
	    sql = sqlAll.toString();
	    int endRecord = pageSize;
        int beginRecord = (pageNow-1)*endRecord;
	    SqlRowSet rowSet = jdbcService.query(sql, new Object[]{memberId,beginRecord,endRecord}); 
	    while(rowSet.next()){
	    	Odoc info = new Odoc();
	    	info.setDoctorId(rowSet.getInt("Docid"));
	    	info.setDoctorName(rowSet.getString("DocName"));
	    	info.setOrgId(rowSet.getInt("OrgId"));
	    	info.setRoleId(rowSet.getInt("RoleId"));
	    	info.setTel(rowSet.getString("Tel"));
	    	info.setEmail(rowSet.getString("Email"));
	    	info.setTitle(rowSet.getString("Title"));
	    	String birthDate = rowSet.getString("BirthDate");
			if (!StringUtils.isEmpty(birthDate)) {
				info.setBirthDate(birthDate);
			}
	    	info.setGender(rowSet.getString("Gender"));
	    	doctorList.add(info);
	    }
    	return doctorList;
    }
    
    /** 
     * @Title: findMemberHeadImg 
     * @Description: 获取会员头像 
     * @author liuxiaoqin
     * @createDate 2016-04-08
     * @throws Exception    
     * @retrun Integer
     */
    public String findMemberHeadImg(Integer memberId) throws Exception{
        String headImg = "";
        String sql = " SELECT Memberid,HeadAddress FROM omem WHERE Memberid = ?  ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId});
        while(rowSet.next()){
            headImg = rowSet.getString("HeadAddress");
        }
        return headImg;
    }

}
