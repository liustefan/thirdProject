package com.zkhk.services;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.zkhk.constants.Constants;
import com.zkhk.dao.MeasureDao;
import com.zkhk.dao.MemDao;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.entity.AllAnomaly;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.ChartEcg2;
import com.zkhk.entity.ChartObsr;
import com.zkhk.entity.ChartOppg;
import com.zkhk.entity.ChartOsbp;
import com.zkhk.entity.ChartParam;
import com.zkhk.entity.DeleteRecord;
import com.zkhk.entity.Ecg2;
import com.zkhk.entity.Mem8;
import com.zkhk.entity.MemFile;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Omds;
import com.zkhk.entity.OmdsParam;
import com.zkhk.entity.Omem;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.RecordParam;
import com.zkhk.entity.ReturnMeasureData;
import com.zkhk.entity.ReturnResult;
import com.zkhk.entity.ReturnValue;
import com.zkhk.entity.ThreeFeatuer;
import com.zkhk.entity.ThreeParam;
import com.zkhk.entity.ViewParam;
import com.zkhk.mongodao.MongoEntityDao;
import com.zkhk.rabBit.ClientSender;
import com.zkhk.util.FileOperateUtil;
import com.zkhk.util.TimeUtil;
import com.zkhk.util.Util;

@Service("measureService")
public class MeasureServiceImpl implements MeasureService {
	private Logger logger = Logger.getLogger(MeasureServiceImpl.class);
	@Resource(name = "measureDao")
	private MeasureDao measureDao;
      
	@Resource(name = "mongoEntityDao")
	private MongoEntityDao mongoEntityDao;

	@Resource(name = "memDao")
	private MemDao memDao;
	
	@Resource(name = "pushMessageDao")
	private PushMessageDao messageDao;

	public String findMemRecordbyParam(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			OmdsParam param = JSON.parseObject(callValue.getParam(),
					OmdsParam.class);
			// 1 血压 2 血糖 3 三合一 4 动态心电

			if (param.getDataType() == 1) {
				// 查询血压信息
				List<Osbp> osbps = measureDao.findOsbpByOmdsValue(param);
				if (osbps.size() > 0) {
					re.setState(0);
					re.setMessage("查询会员血压测量数据成功");
					String content = JSON.toJSONString(osbps);
					re.setContent(content);
				} else {
					re.setState(1);
					re.setMessage("该会员没有匹配的血压测量数据");
				}
			} else if (param.getDataType() == 2) {
				// 查询血糖信息
				List<Obsr> osbps = measureDao.findObsrByOmdsValue(param);
				if (osbps.size() > 0) {
					re.setState(0);
					re.setMessage("查询会员血糖测量数据成功");
					String content = JSON.toJSONString(osbps);
					re.setContent(content);
				} else {
					re.setState(1);
					re.setMessage("该会员没有匹配的血糖测量数据");
				}
			} else if (param.getDataType() == 3) {
				// 查询三合一信息
				List<Oecg> oecgs = measureDao.findOecgByOmdsValue(param);
				List<Oppg> oppgs = measureDao.findOppgByOmdsValue(param);
				if (oecgs.size() > 0 || oppgs.size() > 0) {
					ThreeFeatuer featuer = new ThreeFeatuer();
					featuer.setOecgs(oecgs);
					featuer.setOppgs(oppgs);

					re.setState(0);
					re.setMessage("查询会员三合一测量数据成功");
					String content = JSON.toJSONString(featuer);
					re.setContent(content);
				} else {
					re.setState(1);
					re.setMessage("该会员没有匹配的三合一测量数据");
				}
			} else if (param.getDataType() == 4) {
				// 查询动态心电信息
				List<Oecg> oecgs = measureDao.findOecgByOmdsValue(param);
				if (oecgs.size() > 0) {
					re.setState(0);
					re.setMessage("查询会员心电测量数据成功");
					String content = JSON.toJSONString(oecgs);
					re.setContent(content);
				} else {
					re.setState(1);
					re.setMessage("该会员没有匹配的心电测量数据");
				}
			} else {
				re.setState(3);
				re.setMessage("传递参数有错误");
			}
		return JSON.toJSONString(re);
	}

	public String findMemRecord2byParam(HttpServletRequest request)throws Exception {
		ReturnResult re = new ReturnResult();
		// ReturnValue re = new ReturnValue();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		RecordParam param = JSON.parseObject(callValue.getParam(),
				RecordParam.class);
		// 测量类型:ECG,PPG,BP,BS

		if ("BP".equals(param.getMeasureType())) {
			// 查询血压信息
			List<Osbp> osbps = measureDao.findOsbpByRecordValue(param);
			if (osbps.size() > 0) {
				re.setState(0);
				re.setMessage("查询会员血压测量数据成功");
				String content = JSON.toJSONString(osbps);
				re.setContent(content);
			} else {
				re.setState(1);
				re.setMessage("该会员没有匹配的血压测量数据");
			}
		} else if ("BS".equals(param.getMeasureType())) {
			// 查询血糖信息
			List<Obsr> osbps = measureDao.findObsrByRecordValue(param);
			if (osbps.size() > 0) {
				re.setState(0);
				re.setMessage("查询会员血糖测量数据成功");
				String content = JSON.toJSONString(osbps);
				re.setContent(content);
			} else {
				re.setState(1);
				re.setMessage("该会员没有匹配的血糖测量数据");
			}
		} else if ("PPG".equals(param.getMeasureType())) {
			// 脉搏
			List<Oppg> oppgs = measureDao.findOppgByRecordValue(param);
			if (oppgs.size() > 0) {
				re.setState(0);
				re.setMessage("查询会员脉搏测量数据成功");
				String content = JSON.toJSONString(oppgs);
				// System.out.println(content+"--------------");
				re.setContent(content);
			} else {
				re.setState(1);
				re.setMessage("该会员没有匹配的脉搏测量数据");
			}
		} else if ("ECG".equals(param.getMeasureType())) {
			List<Oecg> oecgs = measureDao.findOecgByRecordValue(param);
			// logger.info(oecgs.size()+"============================");
			if (oecgs.size() > 0) {
				re.setState(0);
				re.setMessage("查询会员心电测量数据成功");
				//String content = JSON.toJSONString(oecgs);
				re.setContent(oecgs);
			} else {
				re.setState(1);
				re.setMessage("该会员没有匹配的心电测量数据");
			}
		} else {
			re.setState(3);
			re.setMessage("传递参数有错误");
		}
		return JSON.toJSONString(re);
	}

	public String findAnomalyEcgbyParam(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
			String ids = jsonObject.getString("id");
			// 更新同行状态信息
			List<Ecg2> ecgs = measureDao.findEcg2ById(ids);
			if (ecgs.size() > 0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(ecgs));
				logger.info("查询会员异常心电信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员异常心电信息");
				logger.info("查询不到该会员异常心电信息");
			}
		return JSON.toJSONString(re);
	}

	public String saveObsrData(HttpServletRequest request) throws Exception{
		    ReturnValue re = new ReturnValue();
			String uploadTime = TimeUtil.currentDatetime();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			
			Obsr obsr = JSON.parseObject(callValue.getParam(), Obsr.class);
			/*验证血糖数据是否重复  begin */
			int countObsr = measureDao.checkHasObsrRecord(callValue.getMemberId(), obsr.getTestTime());
			if(countObsr > 0){
			    re.setState(11);
			    re.setMessage("该血糖数据已存在，请勿重复上传");
			    logger.info("该血糖数据已存在，请勿重复上传");
			    return JSON.toJSONString(re);
			}
			/*验证血糖数据是否重复  end */
			// 分析血糖数据
			int obsrResult = getOsbpResult(obsr);
			obsr.setAnalysisResult(obsrResult);
//			// 获取事件id
//			long event_id = measureDao.insertEventId();
//			obsr.setEventId(event_id);
//			obsr.setUploadTime(uploadTime);
//			obsr.setMemberId(callValue.getMemberId());
//			/** 保存omds表开始 **/
//			Omds omds = new Omds();
//			omds.setDataType(2);
//			omds.setEventId(event_id);
//			omds.setMemberId(callValue.getMemberId());
//			omds.setStatusTag(2);
//			omds.setUploadTime(obsr.getUploadTime());
//			// 结果不为零标示异常
//			if (obsrResult != 0) {
//				omds.setWheAbnTag(1);
//			}
//			measureDao.saveOmdsData(omds);
//			/** 保存omds表结束 **/
//			// 保存血糖数据
//			// 获取血糖id
//			long obsrid = measureDao.insertId("obsr_docentry");
//			obsr.setId(obsrid);
//			measureDao.saveObsrData(obsr);
			
			/* V3.0版本开始去掉分表，主键自动生成  begin */
			/* 保存omds表  begin*/
			Omds omds = new Omds();
            omds.setDataType(2);
            omds.setMemberId(callValue.getMemberId());
            omds.setStatusTag(2);
            omds.setUploadTime(obsr.getUploadTime());
            // 结果不为零标示异常
            if (obsrResult != 0) {
                omds.setWheAbnTag(1);
            }
            long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
			/* 保存omds表  end*/
			
			/* 保存血糖obsr表  begin*/
            obsr.setEventId(eventId);
            obsr.setUploadTime(uploadTime);
            obsr.setMemberId(callValue.getMemberId());
            long docentry = measureDao.insertObsrReturnPrimaryKey(obsr);
            obsr.setId(docentry);
            /* 保存血糖obsr表  end*/
			/* V3.0版本开始去掉分表，主键自动生成  end */
            
			re.setState(0);
			re.setMessage("成功");
			// re.setContent("{\"id\":" + obsrid + "}");
			re.setContent(JSON.toJSONString(obsr));
			logger.info("保存会员血糖测量信息成功");
			
			//添加会员测量一次血糖得3 分
			Mem8 mem8 = new Mem8();
			mem8.setMemberId(callValue.getMemberId());
			mem8.setScore(Constants.ONCE_MEASURE_BG_SCORE);
			mem8.setUploadTime(uploadTime);
			measureDao.addMemActivityScore(mem8);
			/**推送消息----------------------开始**/
//			Message pushMs=messageDao.getPushMessageByMemberId(callValue.getMemberId(),1);
//			pushMs.setCreateTime(TimeUtil.formatDatetime2(new Date()));
//			messageDao.add(pushMs);
			/**推送消息----------------------结束**/
		   return JSON.toJSONString(re);
	}

	public ReturnValue saveOsbpData(Osbp osbp,int memberId) throws Exception{
	        ReturnValue re = new ReturnValue();
			String uploadTime = TimeUtil.currentDatetime();
			/*验证血压数据是否重复  begin */
			int countOsbp = measureDao.checkHasOsbpRecord(memberId, osbp.getTestTime());
			if(countOsbp > 0){
			    re.setState(11);
	            re.setMessage("该血压数据已存在，请勿重复上传");
	            logger.info("该血压数据已存在，请勿重复上传");
	            return re;
			}
			/*验证血压数据是否重复  end */
			// 分析血压数据
			int osbpResult = getOsbpResult(osbp);
			osbp.setAbnormal(osbpResult + "");
//			// 获取事件id
//			long event_id = measureDao.insertEventId();
//			osbp.setEventId(event_id);
//			osbp.setUploadTime(uploadTime);
//			osbp.setMemberId(memberId);
//			/** 保存omds表开始 **/
//			Omds omds = new Omds();
//			omds.setDataType(1);
//			omds.setEventId(event_id);
//			omds.setMemberId(memberId);
//			omds.setStatusTag(2);
//			omds.setUploadTime(osbp.getUploadTime());
//			// 结果不为零标示异常
//			if (osbpResult != 0) {
//				omds.setWheAbnTag(1);
//			}
//			measureDao.saveOmdsData(omds);
//			/** 保存omds表结束 **/
//
//			// 保存血压数据
//			// 获取血压id
//			long obsrid = measureDao.insertId("osbp_docentry");
//			osbp.setId(obsrid);
//			measureDao.saveOsbpData(osbp);
			
			/* V3.0版本开始去掉分表，主键自动生成  begin */
            /* 保存omds表  begin*/
            Omds omds = new Omds();
            omds.setDataType(1);
            omds.setMemberId(memberId);
            omds.setStatusTag(2);
            omds.setUploadTime(osbp.getUploadTime());
            // 结果不为零标示异常
            if (osbpResult != 0) {
                omds.setWheAbnTag(1);
            }
            long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
            /* 保存omds表  end*/
            
            /* 保存血压osbp表  begin*/
            osbp.setEventId(eventId);
            osbp.setUploadTime(uploadTime);
            osbp.setMemberId(memberId);
            long docentry = measureDao.insertOsbpReturnPrimaryKey(osbp);
            osbp.setId(docentry);
            /* 保存血压osbp表  end*/
            /* V3.0版本开始去掉分表，主键自动生成  end */
            
			re.setState(0);
			re.setMessage("成功");
			// re.setContent("{\"id\":" + obsrid + "}");
			re.setContent(JSON.toJSONString(osbp));
			logger.info("保存会员血压测量信息成功");
			
			//添加会员每测量一次血压得1 分
			Mem8 mem8 = new Mem8();
			mem8.setMemberId(memberId);
			mem8.setScore(Constants.ONCE_MEASURE_BP_SCORE);
			mem8.setUploadTime(uploadTime);
			measureDao.addMemActivityScore(mem8);
			/**推送消息----------------------开始**/
//			Message pushMs=messageDao.getPushMessageByMemberId(memberId,1);
//			pushMs.setCreateTime(TimeUtil.formatDatetime2(new Date()));
//			messageDao.add(pushMs);
			/**推送消息----------------------结束**/
		    return re;
	}

	public String saveEcgData(HttpServletRequest request)throws Exception {
	    	ReturnValue re = new ReturnValue();
			String uploadTime = TimeUtil.currentDatetime();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			Oecg oecg = JSON.parseObject(callValue.getParam(), Oecg.class);
			
			/*验证mini心电数据是否重复  begin */
			int countOecg = measureDao.checkHasOecgRecord(callValue.getMemberId(), oecg.getMeasureTime());
			if(countOecg > 0){
			    re.setState(11);
			    re.setMessage("该MINI心电数据已存在，请勿重复上传");
			    logger.info("该MINI心电数据已存在，请勿重复上传");
			    return JSON.toJSONString(re);
			}
			/*验证mini心电数据是否重复  end */

			String rawEcg=null;
	
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			MultipartFile ecg_file = mRequest.getFile(Constants.ECG_PARAM_FILE);				
			// 获取保存文件id
			rawEcg = mongoEntityDao.saveFile(Constants.MDB_FILE, ecg_file,oecg.getInComing());
//			oecg.setRawEcg(rawEcg);
//			oecg.setMemberId(callValue.getMemberId());
//
//			// 获取事件id
//			long event_id = measureDao.insertEventId();
//			oecg.setEventId(event_id);
//			oecg.setUploadTime(uploadTime);
//			/** 保存omds表开始 **/
//			Omds omds = new Omds();
//			omds.setDataType(4);
//			omds.setEventId(event_id);
//			omds.setMemberId(callValue.getMemberId());
//			omds.setStatusTag(2);
//			omds.setUploadTime(oecg.getUploadTime());
//			measureDao.saveOmdsData(omds);
//			/** 保存omds表结束 **/
//
//			// 保存心电数据
//			// 获取心电id
//			long ecgid = measureDao.insertId("oecg_docentry");
//			oecg.setId(ecgid);
//			measureDao.saveOecgData(oecg);
			
			/* V3.0版本开始去掉分表，主键自动生成  begin */
            /* 保存omds表  begin*/
            Omds omds = new Omds();
            omds.setDataType(4);
            omds.setMemberId(callValue.getMemberId());
            omds.setStatusTag(2);
            omds.setUploadTime(oecg.getUploadTime());
            long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
            /* 保存omds表  end*/
            
            /* 保存心电oecg表  begin*/
            oecg.setRawEcg(rawEcg);
            oecg.setMemberId(callValue.getMemberId());
            oecg.setEventId(eventId);
            oecg.setUploadTime(uploadTime);
            long docentry = measureDao.insertOecgReturnPrimaryKey(oecg);
            oecg.setId(docentry);
            /* 保存心电oecg表  end*/
            /* V3.0版本开始去掉分表，主键自动生成  end */
			
			String message = getMqOecgMessage(oecg);
			ClientSender.sender(message);

			re.setState(0);
			re.setMessage("成功");
			re.setContent("{\"id\":" + docentry + "}");
			logger.info("保存会员心电测量信息成功");			
			
			//添加会员测量一次miniHolter得5 分
			Mem8 mem8 = new Mem8();
			mem8.setMemberId(callValue.getMemberId());
			mem8.setScore(Constants.ONCE_MEASURE_MNH_SCORE);
			mem8.setUploadTime(uploadTime);
			measureDao.addMemActivityScore(mem8);
			/**推送消息----------------------开始**/
//			Message pushMs=messageDao.getPushMessageByMemberId(callValue.getMemberId(),1);
//			pushMs.setCreateTime(TimeUtil.formatDatetime2(new Date()));
//			//拉取添加
//			messageDao.add(pushMs);
			//推送消息
//			String memName=memDao.findNameByMemberId(callValue.getMemberId());
//			PushMessageUtil.sendMessage(new MessageData(callValue.getMemberId(), memName, 1), pushMs.getNotifier());
			/**推送消息----------------------结束**/
	    	return JSON.toJSONString(re);
	}

	public String saveEcgPpgData(HttpServletRequest request)throws Exception {
		    ReturnValue re = new ReturnValue();
			String uploadTime = TimeUtil.currentDatetime();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			ThreeParam param = JSON.parseObject(callValue.getParam(),ThreeParam.class);
			/*验证三合一数据是否重复  begin */
            int countOppg = measureDao.checkHasThreeInOneRecord(callValue.getMemberId(), param.getMeasureTime());
            if(countOppg > 0){
                re.setState(11);
                re.setMessage("该三合一数据已存在，请勿重复上传");
                logger.info("该三合一数据已存在，请勿重复上传");
                return JSON.toJSONString(re);
            }
            /*验证三合一数据是否重复  end */
			
			Osbp osbp = measureDao.findObsrByMemberId(callValue.getMemberId());
			if (osbp == null) {
				re.setState(4);
				re.setMessage("没有相应的血压测量信息");
				return JSON.toJSONString(re);
			}

			Omem omem = memDao.findOmembyId(callValue.getMemberId());
			if(omem != null){
			    if(StringUtils.isEmpty(omem.getBirthDate())){
			        re.setState(5);
	                re.setMessage("无法获取该会员的年龄");
	                return JSON.toJSONString(re);
			    }
			}
			Oecg oecg = new Oecg();
//			oecg.setMemberId(callValue.getMemberId());
//			oecg.setDeviceCode("SIAT3IN1_E");
//			oecg.setBluetoothMacAddr(param.getBluetoothMacAddr());
//			oecg.setFs(param.getEcgFs());
//			oecg.setTimeLength(param.getTimeLength());
//			oecg.setMeasureTime(param.getMeasureTime());
//			Oppg oppg = new Oppg();
//			oppg.setMemberId(callValue.getMemberId());
//			oppg.setDeviceCode(param.getDeviceCode());
//			oppg.setBluetoothMacAddr(param.getBluetoothMacAddr());
//			oppg.setFs(param.getPpgFs());
//			oppg.setTimeLength(param.getTimeLength());
//			oppg.setMeasureTime(param.getMeasureTime());
//			oppg.setSpo(param.getSpo());

			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
			MultipartFile ecg_file = mRequest.getFile(Constants.ECG_PARAM_FILE);
			MultipartFile ppg_file = mRequest.getFile(Constants.PPG_PARAM_FILE);
//			 FileOutputStream out1 = new
//			 FileOutputStream("E://"+TimeUtil.currentDatetime()+"ecg.txt");
//			 FileOutputStream out2 = new
//			 FileOutputStream("E://"+TimeUtil.currentDatetime()+"ppg.txt");
//			 FileCopyUtils.copy(ecg_file.getInputStream(), out1);
//			 FileCopyUtils.copy(ppg_file.getInputStream(), out2);
//			 out1.flush();
//			 out2.flush();
//			 out1.close();
//			 out2.close();
			// 保存心电数据
			 
			// 获取心电id
//			long ecgid = measureDao.insertId("oecg_docentry");
//			long ppgid = measureDao.insertId("oppg_docentry");
			
			// 获取保存文件id
			String rawEcg_ecg = mongoEntityDao.saveFile(Constants.MDB_FILE,ecg_file,oecg.getInComing());
			String rawEcg_ppg = mongoEntityDao.saveFile(Constants.MDB_FILE,ppg_file,null);
//			oecg.setRawEcg(rawEcg_ecg);
//			oppg.setRawPpg(rawEcg_ppg);

			// 获取事件id
//			long event_id = measureDao.insertEventId();
//			oecg.setEventId(event_id);
//			oppg.setEventId(event_id);
//			oecg.setUploadTime(uploadTime);
//			oppg.setUploadTime(uploadTime);
//			/** 保存omds表开始 **/
//			Omds omds = new Omds();
//			omds.setDataType(3);
//			omds.setEventId(event_id);
//			omds.setMemberId(callValue.getMemberId());
//			omds.setStatusTag(2);
//			omds.setUploadTime(oecg.getUploadTime());
//			measureDao.saveOmdsData(omds);
//			/** 保存omds表结束 **/
//
//	
//			oecg.setId(ecgid);
//			oppg.setId(ppgid);
//			measureDao.saveOecgData(oecg);
//			measureDao.saveOppgData(oppg);
			
			/* V3.0版本开始去掉分表，主键自动生成  begin */
            /* 保存omds表  begin*/
            Omds omds = new Omds();
            omds.setDataType(3);
            omds.setMemberId(callValue.getMemberId());
            omds.setStatusTag(2);
            omds.setUploadTime(oecg.getUploadTime());
            long eventId = measureDao.insertOmdsReturnPrimaryKey(omds);
            /* 保存omds表  end*/
            
            /* 保存心电oecg表  begin*/
            oecg.setMemberId(callValue.getMemberId());
            oecg.setDeviceCode("SIAT3IN1_E");
            oecg.setBluetoothMacAddr(param.getBluetoothMacAddr());
            oecg.setFs(param.getEcgFs());
            oecg.setTimeLength(param.getTimeLength());
            oecg.setMeasureTime(param.getMeasureTime());
            oecg.setRawEcg(rawEcg_ecg);
            oecg.setEventId(eventId);
            oecg.setUploadTime(uploadTime);
            long ecgid = measureDao.insertOecgReturnPrimaryKey(oecg);
            oecg.setId(ecgid);
            /* 保存心电oecg表  end*/
            
            /* 保存脉搏oppg表  begin*/
            Oppg oppg = new Oppg();
            oppg.setMemberId(callValue.getMemberId());
            oppg.setDeviceCode(param.getDeviceCode());
            oppg.setBluetoothMacAddr(param.getBluetoothMacAddr());
            oppg.setFs(param.getPpgFs());
            oppg.setTimeLength(param.getTimeLength());
            oppg.setMeasureTime(param.getMeasureTime());
            oppg.setSpo(param.getSpo());
            oppg.setRawPpg(rawEcg_ppg);
            oppg.setEventId(eventId);
            oppg.setUploadTime(uploadTime);
            long ppgid = measureDao.insertOppgReturnPrimaryKey(oppg);
            oecg.setId(ppgid);
            /* 保存脉搏oppg表  end*/
            /* V3.0版本开始去掉分表，主键自动生成  end */
			
			String message = getMqOecgOppgMessage(oecg, oppg, osbp, omem);
			ClientSender.sender(message);

			re.setState(0);
			re.setMessage("成功");
			re.setContent("{\"ecg_id\":" + ecgid + ",\"ppg_id\":" + ppgid + "}");
			logger.info("保存会员三合一测量信息成功");
			
			//添加会员测量一次三合一得2 分
			Mem8 mem8 = new Mem8();
			mem8.setMemberId(callValue.getMemberId());
			mem8.setScore(Constants.ONCE_MEASURE_TIN_SCORE);
			mem8.setUploadTime(uploadTime);
			measureDao.addMemActivityScore(mem8);
			
			/**推送消息----------------------开始**/
//			Message pushMs=messageDao.getPushMessageByMemberId(callValue.getMemberId(),1);
//			pushMs.setCreateTime(TimeUtil.formatDatetime2(new Date()));
//			messageDao.add(pushMs);
			/**推送消息----------------------结束**/
		    return JSON.toJSONString(re);
	}

	public String getOecgById(HttpServletRequest request)throws Exception {
		    ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
			long id = jsonObject.getLongValue("id");
			Oecg ecgs = measureDao.findOecgById(id);
			if (ecgs != null) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(ecgs));
				logger.info("查询会员心电信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该心电信息");
				logger.info("查询不到该心电信息");
			}
		    return JSON.toJSONString(re);
	}

	public String getOppgById(HttpServletRequest request) throws Exception{
		    ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			JSONObject jsonObject = JSONObject
					.parseObject(callValue.getParam());
			long id = jsonObject.getLongValue("id");
			// 更新同行状态信息
			Oppg oppg = measureDao.findOppgById(id);
			if (oppg != null) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(oppg));
				logger.info("查询会员脉搏信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到该脉搏信息");
				logger.info("查询不到该脉搏信息");
			}
		    return JSON.toJSONString(re);
	}

	/**
	 * 获取心电发送到mq的数据
	 * 
	 * @param oecg
	 * @return
	 */
	private String getMqOecgMessage(Oecg oecg)throws Exception {
		// ECG_FILTER|Debug,ecg,omds表ID,文件ID,数据类型，采样频率,设备DeviceCode,用户ID
		StringBuffer sb = new StringBuffer();
		sb.append("ECGHOLTER_ANALYZE|debug,");
		sb.append("ecg,");
		sb.append(oecg.getEventId()).append(",");
		sb.append(oecg.getRawEcg()).append(",");
		int dataType = oecg.getDataType();
		int newDataType = 1;
		if(dataType != 0){
		    newDataType = dataType;
		}
		sb.append(newDataType).append(",");
		//web端传递该参数
		/*if(oecg.getInComing()!=null&&oecg.getInComing().equals("web")){
			sb.append(oecg.getDataType()).append(",");
		}else {
			sb.append(1).append(",");
		}*/
		sb.append(oecg.getFs()).append(",");
		String deviceCode = oecg.getDeviceCode();
		int addValue = oecg.getAddValue();
		//老mini设备
		if(!StringUtils.isEmpty(deviceCode) && "SIAT_ELECECG".equals(deviceCode)){
		    //数据类型为双字节()时 addValue的值为384;单字节的时 addValue的值为47
		    if(newDataType == 1 || newDataType == 2){
		        addValue = 47;
		    }else{
		        addValue = 384;
		    }
		}
		//新mini设备
		else if(!StringUtils.isEmpty(deviceCode) && "ZKHK_ELECECG".equals(deviceCode)){
		    addValue = 200;
        }
		sb.append(addValue).append(",");
		sb.append(oecg.getMemberId());
		return sb.toString();
	}

	/**
	 * 获取心电发送到mq的数据
	 * 
	 * @param oecg
	 * @return
	 * @throws Exception
	 */
	private String getMqOecgOppgMessage(Oecg oecg, Oppg oppg, Osbp osbp,
			Omem omem) throws Exception {
		// EP_ANALYZE|Debug,ecg,omds表ID,ecg文件ID,Ecg采样频率,设备DeviceCode,ppg,omds表ID,ppg文件ID,ppg采样频率,血压高压,血压低压,身高,年龄,血氧饱和度,体重,用户ID"
		StringBuffer sb = new StringBuffer();
		sb.append("EP_ANALYZE|debug,");
		sb.append("ecg").append(",");
		// sb.append(oecg.getId()).append(",");
		sb.append(oecg.getEventId()).append(",");
		sb.append(oecg.getRawEcg()).append(",");
		sb.append(4).append(",");
		sb.append(oecg.getFs()).append(",");
		sb.append(200).append(",");
		// sb.append(oecg.getDeviceCode()).append(",");
		sb.append("ppg").append(",");
		sb.append(oppg.getEventId()).append(",");
		sb.append(oppg.getRawPpg()).append(",");
		sb.append(1).append(",");
		//sb.append(-1).append(",");
		sb.append(oppg.getFs()).append(",");
		// sb.append(1).append(",");
		sb.append(osbp.getSbp()).append(",");
		sb.append(osbp.getDbp()).append(",");
		MemFile mem = memDao.findMemFilebyId(oecg.getMemberId());
		sb.append(mem.getHeight()).append(",");
		sb.append(getAgeByBirthday(omem.getBirthDate())).append(",");
		sb.append(oppg.getSpo()).append(",");
		sb.append((int) mem.getWeight()).append(",");
		sb.append(oecg.getMemberId());
		return sb.toString();
	}

	/***
	 * 通过生日算年龄
	 * 
	 * @param birthDate
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private static int getAgeByBirthday(String birthDate)throws Exception {

		try {

			Calendar calendar = Calendar.getInstance();

			SimpleDateFormat formatDate = new SimpleDateFormat("YYYYMMdd");

			String currentTime = formatDate.format(calendar.getTime());

			Date today = formatDate.parse(currentTime);

			Date brithDay = formatDate.parse(birthDate);

			return today.getYear() - brithDay.getYear();

		} catch (Exception e) {

			return 0;

		}
	}

	/**
	 * 分析血压数据异常状态 0 正常1 低血压2 高度高血压3 中度高血压4 轻度高血压5 单纯收缩高血压
	 * 
	 * @param osbp
	 * @return
	 */
	private int getOsbpResult(Osbp osbp)throws Exception {
		int sbp = osbp.getSbp();
		int dbp = osbp.getDbp();
		if ((sbp >= 140 && sbp < 150) && (dbp >= 60 && dbp < 90)) {
			return 5; // "单纯收缩期高血压";
		} else if ((sbp >= 140 && sbp < 160) || (dbp >= 90 && dbp < 100)) {
			return 4; // "轻度高血压";
		} else if ((sbp >= 160 && sbp < 180) || (dbp >= 100 && dbp < 110)) {
			return 3; // "中度高血压";
		} else if (sbp >= 180 || dbp >= 110) {
			return 2; // "高度高血压";
		} else if (sbp < 90 || dbp < 60) {
			return 1; // "低血压";
		} else if ((sbp >= 90 && sbp <= 130) && (dbp >= 60 && dbp <= 85)) {
			return 0; // "正常";
		} else if ((sbp > 130 && sbp < 140) && (dbp > 85 && dbp < 90)) {
			return 6; // "正常偏高";
		}
		return 0;
	}

	/**
	  * @Description 分析血糖结果
	  * @author liuxiaoqin
	  * @CreateDate 2015年9月28日
	*/
	private int getOsbpResult(Obsr obsr)throws Exception 
	{
	    float value = obsr.getBsValue();
	    int timePeriod = obsr.getTimePeriod();
	    //分析结果值(1:血糖偏低;2:血糖偏高;0：正常)
	    int analysisResult = 0; 
	    //早晨空腹(1),午餐前(3),晚餐前(5),睡前(7),夜间(8)  值为：3.9-6.1mmol/L(正常值：包括前者，不包括后者)
	    if(timePeriod == 1 || timePeriod == 3 || timePeriod == 5 || timePeriod == 7 || timePeriod == 8)
	    {
	        if(value < 3.9f)
            {
	            analysisResult = 1; 
            }
            else if(value >= 6.1f)
            {
                analysisResult = 2;
            }
	    }
	    //早餐后2小时(2),午餐后2小时(4),晚餐后2小时(6)  值为：3.9-7.8mmol/L(正常值：包括前者，不包括后者)
	    else if(timePeriod == 2 || timePeriod == 4 || timePeriod == 6)
	    {
	        if(value < 3.9f)
	        {
	            analysisResult = 1;
	        }
	        else if(value >= 7.8f)
	        {
	            analysisResult = 2;
	        }
	    }
	    //随机血糖(0) 值为：3.9-11.1mmol/L(正常值：包括前者，不包括后者)
	    else
        {
            if(value < 3.9f)
            {
                analysisResult = 1;
            }
            else if(value >= 11.1f)
            {
                analysisResult = 2;
            }
        }
        return analysisResult;
	}


	/**
	 * 获取jfreeChare的数据
	 * 
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	private Map< String, Object> createEcgDateForJs(GridFSDBFile file, int fs,int page,String measureTime ,int width,int height) throws Exception {
		Map< String, Object> map=new HashMap<String, Object>();
		if(file==null){
			map.put("msg", false);
			return map;
		}
		map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
		map.put("page", page);
		map.put("fs", fs);
		map.put("width", width);
		map.put("height", height);
		map.put("type", "ecg");
		List<Integer> list=new ArrayList<Integer>();

			byte[] ubs  = Util.input2byte(file,(page-1)*12*fs,page*12*fs);
				for (int i = 0; i <  fs * 6*2; i += 2) {
					if (i < ubs.length-1) {
						list.add((int) Util.getShort(ubs,i ));
					} else {
						break;
					}

				}
				if(ubs.length<fs*6*2){
					int num=(fs*6*2-ubs.length)/2;
					for (int i = 0; i < num; i++) {
						list.add( (int) 0);
					}
				}
				map.put("data", list);
	      return map;
	}



//	/**
//	 * 获取jfreeChare的数据
//	 * 
//	 * @param file
//	 * @return
//	 * @throws Exception 
//	 */
//	private XYDataset createXYData(GridFSDBFile file, int fs,int page,String measureTime) throws Exception {
//		String timeString = getMeasureTime(page,measureTime);
//		XYSeries series = new XYSeries(timeString);
//		try {
//			byte[] ubs  = Util.input2byte(file,(page-1)*12*fs,page*12*fs);
//			int j = 0;
//		
//			byte[] b = new byte[2];
//				for (int i = 0; i <  fs * 6*2; i += 2) {
//					if (i < ubs.length-1) {
//						j += 2;
//						b[0] = ubs[i];
//						b[1] = ubs[i + 1];
//					// System.out.println(Util.getShort(b, false)+"====");
//					  // System.out.println(Util.getShort(ubs,i )+"====");
//						series.add(j, Util.getShort(b, false));
//					} else {
//						break;
//					}
//
//				}
//				if(ubs.length<fs*6*2){
//					int num=(fs*6*2-ubs.length)/2;
//					for (int i = 0; i < num; i++) {
//						j += 2;
//						series.add(j, 0);
//					}
//				}
//			
//		} catch (IOException e) {
//			logger.error("获取坐标数据异常");
//		}
//		final XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series);
//
//		return dataset;
//
//	}

	public void getJfreeChartFileName(HttpServletRequest request,HttpServletResponse response)throws Exception {
		try {
			String params = request.getParameter("params");
			CallValue value = JSON.parseObject(params, CallValue.class);
			ViewParam param = JSON.parseObject(value.getParam(),ViewParam.class);
			GridFSDBFile file = mongoEntityDao.retrieveFileOne("fs",new ObjectId(param.getRawImage()));
//			JFreeChart chart = null;
			if ("ecg".equals(param.getType())) {			
//				XYDataset xyDataset = createXYData(file, param.getFs(), param.getPage(),param.getMeasureTime());
//				chart = JfreeChartUtil.createXYChart(xyDataset, null,
//						null, null, false, param.getFs());
				
				String date =JSON.toJSONString(createEcgDateForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
	            request.setAttribute("highChareData", date);
				
			}else if ("mi_ecg".equals(param.getType())) {
				boolean isWeb=measureDao.findOecgByRawImg(param.getRawImage());
				String date =JSON.toJSONString(createMiEcgDateForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight(),isWeb));
				 request.setAttribute("highChareData", date);
//				XYDataset xyDataset = createMiEcgData(file, param.getFs(), param.getPage(),param.getMeasureTime());
//				chart = JfreeChartUtil.createMiEcgChart(xyDataset, null,
//						null, null, false, param.getFs());
				
				
			} 
			else if ("ppg".equals(param.getType())) {
//				XYDataset xyDataset = createPpgData(file, param.getFs(), param.getPage(),param.getMeasureTime());
//				chart = JfreeChartUtil.createPPgChart(xyDataset,null,
//						null, null, false, param.getFs());		
			String date =JSON.toJSONString(createPpgDateForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
			request.setAttribute("highChareData", date);
			} else if ("hr_ecg".equals(param.getType())) {
				//XYDataset xyDataset=createHrecgData(file, param.getFs(), param.getPage(),param.getMeasureTime());
//					chart = JfreeChartUtil.createHrecgChart(xyDataset, null,null, null, false, param.getFs());
					String date =JSON.toJSONString(createHrecgDateForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
					request.setAttribute("highChareData", date);
				
			} else if ("hr_ppg".equals(param.getType())) {
			//	XYDataset xyDataset=createHrppgData(file, param.getFs(), param.getPage(),param.getMeasureTime());
//					chart = JfreeChartUtil.createHrppgChart(xyDataset, null,	null, null, false, param.getFs());
					String date =JSON.toJSONString(createHrppgDateForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight()));
					request.setAttribute("highChareData", date);
				
			} else if ("ab_ecg".equals(param.getType())) {
		//	XYDataset xyDataset = createAbecgData(file, param.getFs(), param.getPage(),param.getMeasureTime());
//				chart = JfreeChartUtil.createAbecgChart(xyDataset, null,
//						null, null, false, param.getFs());
				String date =JSON.toJSONString(createAbEcgDateForJs(file,  param.getFs(), param.getPage(),param.getMeasureTime(),param.getWidth(),param.getHeight(),param.getDeviceCode()));
				 request.setAttribute("highChareData", date);
			}
		//	HttpSession session = request.getSession();
			//StandardEntityCollection sec = new StandardEntityCollection();
			//ChartRenderingInfo info = new ChartRenderingInfo(sec);
//			String filename = "";
//			try {
//		          if("hr_ecg".equals(param.getType())||"ppg".equals(param.getType())||"hr_ppg".equals(param.getType())){
//				// 获取图片的保存的路径  
//				              String realPath = request.getSession().getServletContext().getRealPath("/temp");		 
//				               // 利用时间生成文件名  
//				              filename = param.getType() + UUID.randomUUID() + ".png";             
//			                  File file2 = new File(realPath, filename);  
//				              ChartUtilities.saveChartAsPNG(file2, chart, param.getWidth(),param.getHeight());   
//				              request.setAttribute("lineFileName", filename);
//				              response.setHeader("status", "success");
//			                  logger.info("生成"+filename+"图片成功");
//		          }
//			} catch (IOException e) {
//			}
			
			// 路径跟过滤器配置
			//filename = request.getContextPath()	+ "/servlet/DisplayChart?filename=" + filename;
			//return filename;
		} catch (Exception e1) {
			logger.error("获取图片数据异常");
		}
		//return null;
	}
	
	private Map<String, Object> createHrppgDateForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		if(file==null){
			map.put("msg", false);
			return map;
		}
		map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
		map.put("page", page);
		map.put("fs", fs);
		map.put("width", width);
		map.put("height", height);
		map.put("type", "hr_ppg");
		String startTime = TimeUtil.formatDatetime2(TimeUtil.datetimeFormat.parse(measureTime));
		String endTime ="";
	
		byte[] ubs = Util.input3byte(file);
		float time= 0;
		String message = new String(ubs);
		String[] hr = message.split("\n{1,}");
		for (int i = 0; i < hr.length; i++) {
			time+=(float)Float.parseFloat(hr[i])/1000;	
		}
		endTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),(int)time));
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<Float> list=new ArrayList<Float>();		
			
			for (int i = 0; i < hr.length-1; i++) {
	          list.add(60 * 1000 /Float.parseFloat(hr[i])) ;	
			}
	        map.put("data", list);
		    return map;
	}

	private Map<String, Object> createHrecgDateForJs(GridFSDBFile file, int fs, int page,	String measureTime, int width, int height) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		if(file==null){
			map.put("msg", false);
			return map;
		}
		map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
		map.put("page", page);
		map.put("fs", fs);
		map.put("width", width);
		map.put("height", height);
		map.put("type", "hr_ecg");
		String startTime = null;
		String endTime=null;
	
		byte[] ubs = Util.input2byte(file,0,page*684*2);
		byte[] b = new byte[2];
	
		float time=0;
		for (int i = 0; i <ubs.length; i += 2) {
			if (i <=(ubs.length-1)) {
				b[0] = ubs[i];
				b[1] = ubs[i + 1];
				short value=Util.getShort(b, false);
				time+= (float)value/1000;
				//System.out.println(i);
				if(i==((page-1)*684*2-1)||i==((page-1)*684*2+1)||i==((page-1)*684*2)){
					
				startTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),6 * (page - 1)));
				}					
			} else {
			
				break;
			}
			
		}

		endTime=TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),(int)time));
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<Float> list=new ArrayList<Float>();
			
				for (int i = (page-1)*684*2; i <ubs.length; i += 2) {
					if (i <=(ubs.length-1)) {
						b[0] = ubs[i];
						b[1] = ubs[i + 1];
						list.add( 60*1000/(float)Util.getShort(b, false));		
					//	System.out.println( 60*1000/(float)Util.getShort(b, false)+"-----------------------");
					} else {
						break;
					}
				}
			    map.put("data", list);
		return map;
	}

	/**
	 * 创建脉搏图数据
	 * @param file
	 * @param fs
	 * @param page
	 * @param measureTime
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Object> createPpgDateForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height) throws Exception {
		
		Map<String, Object> map=new HashMap<String, Object>();
		if(file==null){
			map.put("msg", false);
			return map;
		}
		map.put("measureTime",TimeUtil.datetimeFormat.parse(measureTime));
		map.put("page", page);
		map.put("fs", fs);
		map.put("width", width);
		map.put("height", height);
		map.put("type", "ppg");
		List<Integer> list=new ArrayList<Integer>();
			byte[] bs = Util.input2byte(file,(page-1)*6*fs,page*6*fs);
			for (int i = 0; i < fs * 6; i++) {
				if (i <bs.length) {
				list.add((int) bs[i]);	
				} else {
					break;
				}
			}
			if(bs.length<fs*6){
				int num=(fs*6-bs.length);
				for (int i = 0; i < num; i++) {
				list.add(0);
				}
			}
		map.put("data", list);
		return map;
	}

	private Map<String, Object> createAbEcgDateForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height,String deviceCode) throws Exception {
		Map< String, Object> map=new HashMap<String, Object>();
		if(file==null){
			map.put("msg", false);
			return map;
		}
		map.put("measureTime", TimeUtil.datetimeFormat.parse(measureTime));
		map.put("page", page);
		map.put("fs", fs);
		map.put("width", width);
		map.put("height", height);
		if(deviceCode!=null&&"SIAT3IN1_E".equals(deviceCode)){	
			map.put("type", "ab_ecg");
		}else if(deviceCode!=null&&"MINIHOLTER_E".equals(deviceCode)) {
			map.put("type", "ab_ecg1");
		}else if(deviceCode != null && "SIAT_ELECECG".equals(deviceCode)) {
            map.put("type", "ab_ecg1");
        }else if(deviceCode != null && "ZKHK_ELECECG".equals(deviceCode)) {
            map.put("type", "ab_ecg1");
        }else {
			map.put("type", "ab_ecg2");
		}
		List<Float> list=new ArrayList<Float>();
    	try {


			byte[] bs = Util.input3byte(file);	
				String message = new String(bs);
				String[] hr = message.split("\n{1,}");
				for (String i : hr) {
					list.add(Float.parseFloat(i));
			 }
           map.put("data", list);
		} catch (Exception e) {
			logger.error("获取坐标数据异常");
		}
		return map;
	}

	private Map<String, Object> createMiEcgDateForJs(GridFSDBFile file, int fs, int page,String measureTime, int width, int height,boolean isWeb) throws Exception {
		Map< String, Object> map=new HashMap<String, Object>();
		if(file==null){
			map.put("msg", false);
			return map;
		}
		map.put("measureTime", TimeUtil.datetimeFormat.parse(measureTime));
		map.put("page", page);
		map.put("fs", fs);
		map.put("width", width);
		map.put("height", height);
		List<Integer> list=new ArrayList<Integer>();
		try {

			byte[] ubs  = Util.input2byte(file,(page-1)*6*fs,page*6*fs);
				for (int i = 0; i <  fs * 6; i ++) {
					if (i < ubs.length) {
						//有符合行单字节
                    if(isWeb){  	
				    list.add((int) ubs[i]);
					map.put("type", "mi_ecg");
                    }else {
                    	//无符号型单字节
                    	 list.add((ubs[i]&0x0FF)-128);
                    	map.put("type", "mi_ecg");
					}
					} else {
						break;
					}

				}
				if(ubs.length<fs*6){
					int num=(fs*6-ubs.length);
					for (int i = 0; i < num; i++) {
					    list.add(0);	
						
					}
				}
				map.put("data", list);
		} catch (IOException e) {
			logger.error("获取坐标数据异常");
		}
		
        return map;
		
		
	}

//	private XYDataset createMiEcgData(GridFSDBFile file, int fs, int page,String measureTime) throws Exception {
//		String timeString = getMeasureTime(page,measureTime);
//		XYSeries series = new XYSeries(timeString);
//		try {
//
//			byte[] ubs  = Util.input2byte(file,(page-1)*6*fs,page*6*fs);
//			float j = 0;
//				for (int i = 0; i <  fs * 6; i ++) {
//					if (i < ubs.length) {
//                        j+=1/(float)fs*6;	
//						series.add(j, ubs[i]);
//					} else {
//						break;
//					}
//
//				}
//				if(ubs.length<fs*6){
//					int num=(fs*6-ubs.length);
//					for (int i = 0; i < num; i++) {
//						j += 1/(float)fs*6;
//						series.add(j, 0);
//					}
//				}
//			
//		} catch (IOException e) {
//			logger.error("获取坐标数据异常");
//		}
//		final XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series);
//
//		return dataset;
//	}

//	/**
//	 * 获取异常心电相间
//	 * @param file
//	 * @param fs
//	 * @param type
//	 * @param page
//	 * @return
//	 */
//    private XYDataset createAbecgData(GridFSDBFile file, int fs,int page,String measureTime)throws Exception {
//    	// 获取时间
//    	XYSeries series =null;
//    	try {
//		String timeString = getMeasureTime(page,measureTime);
//		 series = new XYSeries(timeString);
//
//			byte[] bs = Util.input3byte(file);
//			byte[] ubs = bs;
//			int j = 0;
//			
//				String message = new String(ubs);
//				String[] hr = message.split("\n{1,}");
//				for (String i : hr) {
//					j += 1;
//					series.add(j, Float.parseFloat(i));
//
//			}
//
//		} catch (Exception e) {
//			logger.error("获取坐标数据异常");
//		}
//		final XYSeriesCollection dataset = new XYSeriesCollection();
//		dataset.addSeries(series);
//
//		return dataset;
//	}

//	/**
//     * 创建瞬时脉搏数据
//     * @param file
//     * @param fs
//     * @param type
//     * @param page
//     * @return
//     */
//	private  XYDataset createHrppgData(GridFSDBFile file, int fs, int page,String measureTime)throws Exception {	
//		 XYSeriesCollection dataset=null;
//		try {
//			
//			String startTime = TimeUtil.formatDatetime2(TimeUtil.datetimeFormat.parse(measureTime));
//			String endTime ="";
//		
//
//			byte[] bs = Util.input3byte(file);
//			byte[] ubs = bs;
//			float j = 0;
//			float time= 0;
//			String message = new String(ubs);
//			String[] hr = message.split("\n{1,}");
//			for (int i = 0; i < hr.length; i++) {
//				time+=(float)Float.parseFloat(hr[i])/1000;	
//			}
//			endTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),(int)time));
//			XYSeries series = new XYSeries(startTime+"                                                               "+endTime);
//			for (int i = 0; i < hr.length-1; i++) {
//				j+=Float.parseFloat(hr[i]);
//			//	System.out.println(60 * 1000 /Float.parseFloat(hr[i]));
//	            series.add((float)j/1000, 60 * 1000 /Float.parseFloat(hr[i]));
//				
//			}
//			dataset = new XYSeriesCollection();
//			dataset.addSeries(series);
//	        
//		} catch (Exception e) {
//			logger.error("获取坐标数据异常");
//		}
//	
//		return dataset;
//	}

//	/**
//	 * 创建瞬时心电数据
//	 * 
//	 * @param file
//	 * @param fs
//	 * @param type
//	 * @param page
//	 * @return
//	 */
//	private  XYDataset  createHrecgData(GridFSDBFile file, int fs, int page,String measureTime)throws Exception {
//		 XYSeriesCollection data=null;
//		try {
//	
//			String startTime = null;
//			String endTime=null;
//		
//			byte[] ubs = Util.input2byte(file,0,page*684*2);
//			float j = 0;
//		
//			byte[] b = new byte[2];
//		
//			float time=0;
//			for (int i = 0; i <ubs.length; i += 2) {
//				if (i <=(ubs.length-1)) {
//					b[0] = ubs[i];
//					b[1] = ubs[i + 1];
//					short value=Util.getShort(b, false);
//					time+= (float)value/1000;
//					//System.out.println(i);
//					if(i==((page-1)*684*2-1)||i==((page-1)*684*2+1)||i==((page-1)*684*2)){
//						
//					startTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),6 * (page - 1)));
//					}					
//				} else {
//				
//					break;
//				}
//				
//			}
//
//			
//			endTime=TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),(int)time));
//			XYSeries series = new XYSeries(startTime+"                                                               "+endTime);
//			for (int i = (page-1)*684; i <ubs.length; i += 2) {
//					if (i <=(ubs.length-1)) {
//						b[0] = ubs[i];
//						b[1] = ubs[i + 1];
//						short value=Util.getShort(b, false);
//						j += (float)value/1000;
//						series.add(j, 60*1000/(float)value);	
//						//System.out.println( 60*1000/(float)Util.getShort(b, false)+"========================");
//					} else {
//						break;
//					}
//				}
//		
//			 data = new XYSeriesCollection();
//			data.addSeries(series);
//		} catch (Exception e) {
//			logger.error("获取坐标数据异常");
//		}
//
//		return data;
//	}

//	/**
//	 * 创建ppg脉搏数据的数据
//	 * 
//	 * @param file
//	 * @param fs
//	 * @param type
//	 * @param page
//	 * @return
//	 * @throws Exception 
//	 */
//	private XYDataset createPpgData(GridFSDBFile file, int fs,
//			int page,String measureTime) throws Exception {
//		String timeString = getMeasureTime(page,measureTime);
//		XYSeries series = new XYSeries(timeString);
//		byte[] bs;
//		try {
//			float j=0;
//			bs = Util.input2byte(file,(page-1)*6*fs,page*6*fs);
//			for (int i = 0; i < fs * 6; i++) {
//				if (i <bs.length) {
//					j+=(float)1/fs;
//					series.add(j, bs[i]);		
//				} else {
//					break;
//				}
//			}
//			if(bs.length<fs*6){
//				int num=(fs*6-bs.length);
//				for (int i = 0; i < num; i++) {
//					j += 1/(float)fs;
//					series.add(j, 0);
//				}
//			}
//			// 将数据序列放在一个数据集合中
//			final XYSeriesCollection dataset = new XYSeriesCollection();
//			dataset.addSeries(series);
//			return dataset;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//		}
//		return null;
//	}

//	/**
//	 * 获取图片测量时间
//	 * 
//	 * @param date
//	 * @return
//	 * @throws Exception 
//	 */
//	private String getMeasureTime( int page,String measureTime) throws Exception {
//		String startTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),6 * (page - 1)));
//		String endTime = TimeUtil.formatDatetime2(TimeUtil.addSecond(TimeUtil.datetimeFormat.parse(measureTime),6 * page));
//		return startTime + "                                                                " + endTime;
//	}

	public String getPageByparam(HttpServletRequest request) throws Exception{
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue value = JSON.parseObject(params, CallValue.class);
			ViewParam param = JSON.parseObject(value.getParam(),ViewParam.class);
			ObjectId id=new ObjectId(param.getRawImage());
	        GridFSDBFile file=mongoEntityDao.retrieveFileOne("fs", id);
			// 更新同行状态信息
			if (file != null) {
				int fileLeng=Util.getFileLength(file);
				int page=0;
				if("ecg".equals(param.getType())){
					if(fileLeng%(param.getFs()*6*2)==0){
						page=(int) (fileLeng/(param.getFs()*6*2)); 
					}else {
						page=(int) (fileLeng/(param.getFs()*6*2)+1); 
					}
				}else if("ppg".equals(param.getType())){
					if(fileLeng%(param.getFs()*6)==0){
						page=(int) (fileLeng/(param.getFs()*6)); 
					}else {
						page=(int) (fileLeng/(param.getFs()*6)+1); 
					}
				}else if ("hr_ecg".equals(param.getType())) {
//					String message = new String(Util.input3byte(file));
//					String[] hr = message.split("\n{1,}");
				//	System.out.println(hr.length+"");
					if(fileLeng%(684*2)==0){
					page=fileLeng/(684*2); 
					}else {
					page=fileLeng/(684*2)+1; 
					}
					
				}else if ("mi_ecg".equals(param.getType())) {

					if(fileLeng%(param.getFs()*6)==0){
						page=(int) (fileLeng/(param.getFs()*6)); 
					}else {
						page=(int) (fileLeng/(param.getFs()*6)+1); 
					}		
				}
				
				//System.out.println(page+"=============================");
				re.setState(0);
				re.setMessage("查询成功");
				re.setContent("{\"page\":"+page+"}");
				logger.info("查询页数成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到相应的文件");
				logger.info("查询不到相应在文件");
			}
		return JSON.toJSONString(re);
	}

	public String getAllAbnormal(HttpServletRequest request)throws Exception {
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			JSONObject jsonObject = JSONObject
					.parseObject(callValue.getParam());
			String startTime = jsonObject.getString("startTime");
			String endTime = jsonObject.getString("endTime");
			// 更新同行状态信息
		 AllAnomaly allAnomaly=new AllAnomaly();
		 List<Oecg> oecgs=measureDao.findAnomalyEcgbyMemberIdAndTime(callValue.getMemberId(),startTime,endTime);
         List<Oppg> oppgs=measureDao.findAnomalyPpgbyMemberIdAndTime(callValue.getMemberId(),startTime,endTime);
         List<Obsr> obsrs=measureDao.findAnomalyBsrbyMemberIdAndTime(callValue.getMemberId(),startTime,endTime);
         List<Osbp> osbps=measureDao.findAnomalySbpbyMemberIdAndTime(callValue.getMemberId(),startTime,endTime);
         allAnomaly.setOecgs(oecgs);
         allAnomaly.setObsrs(obsrs);
         allAnomaly.setOppgs(oppgs);
         allAnomaly.setOsbps(osbps);
         re.setState(0);
         re.setContent(JSON.toJSONString(allAnomaly));
         re.setMessage("查询所有异常信息成功");
		 return JSON.toJSONString(re);
	}

	public String isExitOsbp(HttpServletRequest request) throws Exception {
		ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			Osbp osbp = measureDao.findObsrByMemberId(callValue.getMemberId());
			if (osbp == null) {
				re.setState(1);
				re.setMessage("没有相应的血压测量信息");
				return JSON.toJSONString(re);
			}else{
				re.setState(0);
				re.setMessage("存在血压测试数据");
				return JSON.toJSONString(re);
			}
	
	}
	public void download(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		JSONObject object=JSON.parseObject(callValue.getParam());
		GridFSDBFile file = mongoEntityDao.retrieveFileOne("fs",new ObjectId( object.getString("rawImg")));
		FileOperateUtil.download(request, response, file);
		
	}

	public String getMeasureByEventId(HttpServletRequest request)throws Exception {
		String params = request.getParameter("params");
		ReturnValue re = new ReturnValue();
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		JSONObject object=JSON.parseObject(callValue.getParam());
		String type=object.getString("measureTSype");
		long eventId=object.getLongValue("eventId");
		if("PPG".equals(type)){
		Oppg oppg=	measureDao.getPpgByEventId(eventId);
		if(oppg!=null){
		re.setState(0);
		re.setMessage("查询成功");
		re.setContent(JSON.toJSONString(oppg));
		}else {
			re.setState(1);
			re.setMessage("查询不到相应的脉搏数据");
		}
		}else if ("ECG".equals(type)) {
			Oecg oecg=measureDao.getEcgByEventId(eventId);
			if(oecg!=null){
				re.setState(0);
				re.setMessage("查询成功");
				re.setContent(JSON.toJSONString(oecg));
				}else {
					re.setState(1);
					re.setMessage("查询不到相应的心电数据");
				}
		}else {
			re.setState(3);
			re.setMessage("请传递正确的类型");	
		}
		return JSON.toJSONString(re);
	}

	public String deleteMeasureRecord(HttpServletRequest request) throws Exception {
		ReturnResult result = new ReturnResult();
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		DeleteRecord deleteRecord = JSON.parseObject(callValue.getParam(), DeleteRecord.class);
		
		if(deleteRecord.getDataType() < 1 || deleteRecord.getDataType() > 5)
		{
			result.setState(3);
			result.setMessage("传入的数据类型错误");
			logger.info("传入的数据类型错误");
		}
		else
		{
		    String eventIds = deleteRecord.getEventIds();
            String[] ids = eventIds.split(",");
            for(int i = 0; i <ids.length ; i++)
            {
                int eventId = Integer.valueOf(ids[i]);
                int memberId = deleteRecord.getMemberId();
                int dataType = deleteRecord.getDataType();
                int rows = measureDao.deleteMeasureRecord(memberId,eventId,dataType);
                //如果是三合一数据
                if(dataType == 5 && rows == 1)
                {
                    result.setState(1);
                    result.setMessage("删除三合一数据失败！");
                    logger.info("删除三合一数据失败！");
                }
                else if(rows == 0){
                    result.setState(1);
                    result.setMessage("未找到删除的数据");
                    logger.info("未找到删除的数据");
                }else{
                    result.setState(0);
                    result.setMessage("会员测量数据删除成功");
                    logger.info("会员测量数据删除成功");
                }
            }
		}
		return JSON.toJSONString(result);
	}
	
	public String getMeasueChartData(ChartParam param) throws Exception {
		
		Map<?, ?> map = measureDao.queryMeasueChartData(param);
		JSONArray jsonArr = new JSONArray();
		if(map != null){
			if(param.getDataType() == 1){
				//血压
				List<ChartOsbp> list1 = (ArrayList<ChartOsbp>) map.get("pie_bar");
				List<ChartOsbp> list2 = (ArrayList<ChartOsbp>) map.get("foursubchart");
				List<ChartOsbp> list3 = (ArrayList<ChartOsbp>) map.get("scachart");
				List<ChartOsbp> list4 = (ArrayList<ChartOsbp>) map.get("trend1");
				List<ChartOsbp> list5 = (ArrayList<ChartOsbp>) map.get("trend2");
				List<ChartOsbp> list6 = (ArrayList<ChartOsbp>) map.get("trend3");
				jsonArr = singleXueYaChart(list1, list2, list3, list4, list5, list6);
			}else if(param.getDataType() == 2){
				//血糖
				List<ChartObsr> list1 = (ArrayList<ChartObsr>) map.get("chart1");
				List<ChartObsr> list2 = (ArrayList<ChartObsr>) map.get("chart2");
				List<ChartObsr> list3 = (ArrayList<ChartObsr>) map.get("chart3");
				List<ChartObsr> list4 = (ArrayList<ChartObsr>) map.get("chart4");
				List<ChartObsr> list5 = (ArrayList<ChartObsr>) map.get("trend");
				jsonArr = singleXueTangChart(list1, list2, list3, list4, list5);
			}else if(param.getDataType() == 3){
				//三合一心电
				List<ChartEcg2> list1 = (ArrayList<ChartEcg2>) map.get("chart1");
				List<ChartEcg2> list2 = (ArrayList<ChartEcg2>) map.get("chart2");
				List<ChartOppg> list3 = (ArrayList<ChartOppg>) map.get("chart3");
				List<ChartOppg> list4 = (ArrayList<ChartOppg>) map.get("chart4");
				jsonArr = singleSanHeYiChart(list1, list2, list3, list4);
			}else if(param.getDataType() == 4){
				//动态心电
				List<ChartEcg2> list1 = (ArrayList<ChartEcg2>) map.get("chart1");
				List<ChartEcg2> list2 = (ArrayList<ChartEcg2>) map.get("chart2");
				jsonArr = singleSanHeYiChart(list1, list2, null, null);
			}
		}
		logger.info("获取单项测量chart图数据成功");
		return jsonArr.toString();		
	}
	
	
	private JSONArray singleXueYaChart(List<ChartOsbp> list1, List<ChartOsbp> list2, List<ChartOsbp> list3,List<ChartOsbp> list4, List<ChartOsbp> list5, List<ChartOsbp> list6){
		Map map = new HashMap();
		JSONArray jsonArr = new JSONArray();
		double sum = 0.00;
		
		JSONArray pie1 = new JSONArray();
		JSONArray bar4 = new JSONArray();
		
		if(list1 != null && list1.size() > 0){
			//血压测量表中异常状态 0:正常	1:低血压	2:高度高血压	3:中度高血压	4:轻度高血压	5:单纯收缩期高血压
			for(int i = 0; i < 6; i++){
				boolean flag = false;
				double num = 0.00;
				for(ChartOsbp os : list1){
					if(os != null && (""+i).equals(os.getAbnormal())){
						flag = true;
						num = os.getNum();
						sum += num;
						break;
					}
				}
				if(flag){
					JSONObject bar = new JSONObject();
					bar.put(InvoValue(i, "xyyc", 1), num);
					bar4.add(bar);
					map.put("bar"+i, num);
					num = 0.00;
					flag = false;
				}else{
					JSONObject bar = new JSONObject();
					bar.put(InvoValue(i, "xyyc", 1), num);
					bar4.add(bar);
					map.put("bar"+i, num);
				}
			}
			
			//Map pieMap = new HashMap();
			if(sum > 0){
				Iterator iter = map.keySet().iterator();
				while(iter.hasNext()){
					Object obj = iter.next();
					if(obj != null){
						double per = Double.parseDouble(map.get(obj.toString()).toString()) / sum;
						JSONObject pie = new JSONObject();
						pie.put(InvoValue(Integer.parseInt(obj.toString().substring(3)), "xyyc", 1), per);
						pie1.add(pie);
					}
				}
			}else{
				for(int i = 0; i < 6; i++){
					JSONObject pie = new JSONObject();
					pie.put(InvoValue(i, "xyyc", 1), 0.00);
					pie1.add(pie);
				}
			}
		}else{
			for(int i = 0; i < 6; i++){
				
				JSONObject pie = new JSONObject();
				pie.put(InvoValue(i, "xyyc", 1), 0.00);
				pie1.add(pie);
				
				JSONObject bar = new JSONObject();
				bar.put(InvoValue(i, "xyyc", 1), 0.00);
				bar4.add(bar);
			}
		}
		
		//饼图
		JSONObject piechart = new JSONObject();
		piechart.put("pie1", pie1);
		jsonArr.add(piechart);
		
		//柱状图
		JSONObject barchart = new JSONObject();
		barchart.put("bar4", bar4);
		jsonArr.add(barchart);
		//------------------------------------------------
		sum = 0.00;
		map = new HashMap();
		Map timeMap = new HashMap();
		JSONArray timeQ = new JSONArray();	//四个小图
		if(list2 != null && list2.size() > 0){
			//8点前	8-12点	12-18点	18点以后
			for(int i = 0; i < 4; i++){
				//四个时间段的饼图数据
				JSONArray subTimeArr = new JSONArray();
				
				boolean flag = false;
				Map xyMap = new HashMap();
				for(ChartOsbp os : list2){
					if(os != null && (""+i).equals(os.getTimeQ().toString())){
						flag = true;
						sum += os.getNum();
						xyMap.put("xy"+os.getXY(), os.getNum());
					}
				}
				
				//判断是否有对应子图的数据
				if(flag){
					Map subtime = new LinkedHashMap();
					for(int x = 0; x < 3; x++){
						String n = xyMap.get("xy"+x) == null ? "0" : xyMap.get("xy"+x).toString();
						if(!"0".equals(n)){
							subtime.put(InvoValue(x, "gzd", 1), Double.parseDouble(n)/sum);
						}else{
							subtime.put(InvoValue(x, "gzd", 1), 0.00);
						}
					}
					subTimeArr.add(subtime);
					JSONObject subChart = new JSONObject();
					subChart.put(InvoValue(i, "timeQ", 1), subTimeArr);
					
					timeQ.add(subChart);
					
					flag = false;
				}else{
					//0偏高、1正常、2偏低
					Map LinkedMap = new LinkedHashMap();
					LinkedMap.put("偏低", 0.00);
					LinkedMap.put("正常", 0.00);
					LinkedMap.put("偏高", 0.00);
				
					subTimeArr.add(LinkedMap);
					JSONObject subChart = new JSONObject();
					subChart.put(InvoValue(i, "timeQ", 1), subTimeArr);
					
					timeQ.add(subChart);
				}
				sum=0.00;	//清空累加器
			}
		}else{
			for(int i = 0; i < 4; i++){
				
				JSONArray subTimeArr = new JSONArray();
				
				Map subtime = new LinkedHashMap();
				subtime.put("偏低", 0.00);
				subtime.put("正常", 0.00);
				subtime.put("偏高", 0.00);
				subTimeArr.add(subtime);
				
				JSONObject subChart = new JSONObject();
				subChart.put(InvoValue(i, "timeQ", 1), subTimeArr);
				
				timeQ.add(subChart);
			}
		}
		JSONObject timeQChart = new JSONObject();
		timeQChart.put("timeQ", timeQ);
		jsonArr.add(timeQChart);
		
		Map scat = new LinkedHashMap();
		if(list3 != null && list3.size() > 0){
			JSONArray scatSArr = new JSONArray();	//收缩压
			JSONArray scatDArr = new JSONArray();	//舒张压
			
			for(ChartOsbp os : list3){
				if(os != null && os.getTestTimes() != null && !"".equals(os.getTestTimes().trim())){
					//os.TestTimes ,os.SBP, os.DBP
					JSONObject s = new JSONObject();
					JSONObject d = new JSONObject();
					
					double time = Double.parseDouble(os.getTestTimes().replace(":", "."));
					
					s.put(String.valueOf(time), os.getSbp()==null ? 0 : os.getSbp());
					scatSArr.add(s);
					
					d.put(String.valueOf(time), os.getDbp()==null ? 0 : os.getDbp());
					scatDArr.add(d);
					
				}
			}
			scat.put("scotS", scatSArr);	//收缩压
			scat.put("scotD", scatDArr);	//舒张压
		}else{
			JSONArray scatSArr = new JSONArray();
			JSONArray scatDArr = new JSONArray();
			scat.put("scotS", scatSArr);	//收缩压
			scat.put("scotD", scatDArr);	//舒张压
		}
		jsonArr.add(scat);
		
		JSONObject trend1 = new JSONObject();
		if(list4 != null && list4.size() > 0){
			JSONObject obj = new JSONObject();
			JSONArray time = new JSONArray();
			JSONArray sbp = new JSONArray();
			JSONArray dbp = new JSONArray();
			
			for(ChartOsbp os : list4){
				if(os != null && os.getTestTimes() != null && !"".equals(os.getTestTimes().trim())){
					time.add(os.getTestTimes());
					sbp.add(os.getSbp());
					dbp.add(os.getDbp());
				}
			}
			obj.put("time", time);
			obj.put("sbp", sbp);
			obj.put("dbp", dbp);
			trend1.put("trend1", obj);
		}
		jsonArr.add(trend1);
		
		JSONObject trend2 = new JSONObject();
		if(list5 != null && list5.size() > 0){
			JSONObject obj = new JSONObject();			
			JSONArray time = new JSONArray();
			JSONArray sbp = new JSONArray();
			JSONArray dbp = new JSONArray();
			
			for(ChartOsbp os : list5){
				if(os != null && os.getTestTimes() != null && !"".equals(os.getTestTimes().trim())){
					time.add(os.getTestTimes());
					sbp.add(os.getSbp());
					dbp.add(os.getDbp());
				}
			}
			obj.put("time", time);
			obj.put("sbp", sbp);
			obj.put("dbp", dbp);
			trend2.put("trend2", obj);
		}
		jsonArr.add(trend2);
		
		JSONObject trend3 = new JSONObject();
		if(list6 != null && list6.size() > 0){
			JSONObject obj = new JSONObject();
			JSONArray time = new JSONArray();
			JSONArray sbp = new JSONArray();
			JSONArray dbp = new JSONArray();
			
			for(ChartOsbp os : list6){
				if(os != null && os.getTestTimes() != null && !"".equals(os.getTestTimes().trim())){
					time.add(os.getTestTimes());
					sbp.add(os.getSbp());
					dbp.add(os.getDbp());
				}
			}
			obj.put("time", time);
			obj.put("sbp", sbp);
			obj.put("dbp", dbp);
			trend3.put("trend3", obj);
		}
		jsonArr.add(trend3);
		return jsonArr;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JSONArray singleXueTangChart(List<ChartObsr> list1, List<ChartObsr> list2, List<ChartObsr> list3, List<ChartObsr> list4, List<ChartObsr> list5){
		JSONArray jsonArr = new JSONArray();
		
		Map<String, JSONArray> chart1 = new LinkedHashMap();
		JSONArray c0 = new JSONArray();
		JSONArray c1 = new JSONArray();
		JSONArray c2 = new JSONArray();
		JSONArray c3 = new JSONArray();
		JSONArray c4 = new JSONArray();
		JSONArray c5 = new JSONArray();
		JSONArray c6 = new JSONArray();
		JSONArray c7 = new JSONArray();
		JSONArray c8 = new JSONArray();
		if(list1 != null && list1.size() > 0){
			for(ChartObsr ob : list1){
				Map json = new LinkedHashMap();
				String time = (ob.getTestTimes() == null ? "00:00" : ob.getTestTimes()).replace(":", ".");
				
				json.put(String.valueOf(Double.parseDouble(time)), ob.getBsValue());
				
				if("0".equals(ob.getTimePeriod())){
					c0.add(json);
				}else if("1".equals(ob.getTimePeriod())){
					c1.add(json);
				}else if("2".equals(ob.getTimePeriod())){
					c2.add(json);
				}else if("3".equals(ob.getTimePeriod())){
					c3.add(json);
				}else if("4".equals(ob.getTimePeriod())){
					c4.add(json);
				}else if("5".equals(ob.getTimePeriod())){
					c5.add(json);
				}else if("6".equals(ob.getTimePeriod())){
					c6.add(json);
				}else if("7".equals(ob.getTimePeriod())){
					c7.add(json);
				}else if("8".equals(ob.getTimePeriod())){
					c8.add(json);
				}
			}
		}
		chart1.put(InvoValue("0", null, 2), c0);
		chart1.put(InvoValue("1", null, 2), c1);
		chart1.put(InvoValue("2", null, 2), c2);
		chart1.put(InvoValue("3", null, 2), c3);
		chart1.put(InvoValue("4", null, 2), c4);
		chart1.put(InvoValue("5", null, 2), c5);
		chart1.put(InvoValue("6", null, 2), c6);
		chart1.put(InvoValue("7", null, 2), c7);
		chart1.put(InvoValue("8", null, 2), c8);
		jsonArr.add(chart1);
		
		Map<String, JSONArray> chart2 = new LinkedHashMap<String, JSONArray>();
		if(list2 != null && list2.size() > 0){
			//时间段(9种，此处为硬编码)
			for(int i = 0; i <= 8; i++){
				int count = 0;
				double max = 0.00;
				double upper = 0.00;
				double median = 0.00;
				double lower = 0.00;
				double min = 0.00;
				
				List<Double> ls = new ArrayList();
				for(ChartObsr ob : list2){
					if((""+i).equals(ob.getTimePeriod())){
						ls.add(ob.getBsValue());
						max = max < ob.getBsValue() ? ob.getBsValue() : max;
						min = min > ob.getBsValue() ? ob.getBsValue() : min;
						count++;
					}
				}
				
				if(count == 1){
					upper = max;
					median = max;
					lower = max;
					min = max;
				}else if(count == 2){
					upper = max;
					median = (max + min) / 2;
					lower = min;
				}else if(count == 3){
					for(double bs : ls){
						if(bs <= max && bs >=min){
							upper = bs;
							median = bs;
							lower = bs;
							break;
						}
					}
				}else if(count >= 4){
					ls.remove(max);
					ls.remove(min);
					
					double subsum = 0.00;
					for(double bsv : ls){
						upper = upper < bsv ? bsv : upper;
						lower = lower > bsv ? bsv : lower;
						subsum += bsv;
					}
					
					median = subsum / ls.size();
				}
				
				JSONArray sub = new JSONArray();
				Map json = new LinkedHashMap();
				json.put("max", max);
				json.put("upper", upper);
				json.put("median", median);
				json.put("lower", lower);
				json.put("min", min);
				sub.add(json);
				chart2.put(InvoValue(""+i, null, 2), sub);
			}
		}else{
			//时间段(9种，此处为硬编码)
			for(int i = 0; i <= 8; i++){
				JSONArray sub = new JSONArray();
				Map json = new LinkedHashMap();
				json.put("max", 0.0);
				json.put("upper", 0.0);
				json.put("median", 0.0);
				json.put("lower", 0.0);
				json.put("min", 0.0);
				
				sub.add(json);
				chart2.put(InvoValue(""+i, null, 2), sub);
			}
		}
		jsonArr.add(chart2);
		
		Map chart3 = new LinkedHashMap();
		JSONArray max = new JSONArray();
		JSONArray min = new JSONArray();
		if(list3 != null && list3.size() > 0){
			for(ChartObsr ob : list3){
				Map jsonMax = new LinkedHashMap();
				jsonMax.put(ob.getDays(), ob.getMax());
				max.add(jsonMax);
				
				Map jsonMin = new LinkedHashMap();
				jsonMin.put(ob.getDays(), ob.getMin());
				min.add(jsonMin);
			}
		}
		chart3.put("最高值", max);
		chart3.put("最低值", min);
		jsonArr.add(chart3);
		
		
		Map chart4 = new LinkedHashMap();
		if(list4 != null && list4.size() > 0){
			//时间段(9种，此处为硬编码)
			for(int i = 0; i <= 8; i++){
				short count = 0;
				int nNum = 0;
				int hNum = 0;
				int lNum = 0;
				
				for(ChartObsr ob : list4){
					if((""+i).equals(ob.getTimePeriod())){
						if("H".equals(ob.getNE())){
							hNum = ob.getNum();
							count++;
						}else if("L".equals(ob.getNE())){
							lNum = ob.getNum();
							count++;
						}else if("N".equals(ob.getNE())){
							nNum = ob.getNum();
							count++;
						}
						if(count >= 2){
							break;
						}
					}
				}
				double sum = 0.00;
				sum += nNum + hNum + lNum;	//小数
				
				JSONObject n = new JSONObject();
				JSONObject h = new JSONObject();
				JSONObject l = new JSONObject();
				if(sum > 0){
					n.put("正常", nNum / sum);
					h.put("偏高", hNum / sum);
					l.put("偏低", lNum / sum);
				}else{
					n.put("正常", 0);
					h.put("偏高", 0);
					l.put("偏低", 0);
				}
				JSONArray sub = new JSONArray();
				sub.add(n);
				sub.add(h);
				sub.add(l);
				chart4.put(InvoValue(""+i, null, 2), sub);
			}
		}else{
			//时间段(9种，此处为硬编码)
			for(int i = 0; i <= 8; i++){
				JSONObject n = new JSONObject();
				JSONObject h = new JSONObject();
				JSONObject l = new JSONObject();
				n.put("正常", 0);
				h.put("异常", 0);
				l.put("异常", 0);
				
				JSONArray sub = new JSONArray();
				sub.add(n);
				sub.add(h);
				sub.add(l);
				chart4.put(InvoValue(""+i, null, 2), sub);
			}
		}
		
		jsonArr.add(chart4);
		
		JSONArray other = new JSONArray();		// 其他时间段
		JSONArray kongfu = new JSONArray();		// 早晨空腹血糖
		JSONArray afterBr = new JSONArray();	// 早餐后2小时
		JSONArray bfLunch = new JSONArray();	// 午餐前
		JSONArray afLunch = new JSONArray();	// 午餐后2小时
		JSONArray bfAf = new JSONArray();		// 晚餐前
		JSONArray afAf = new JSONArray();		// 晚餐后2小时
		JSONArray bfSleep = new JSONArray();	// 睡觉前
		JSONArray midNight = new JSONArray();	// 夜间
		
		Map object = new LinkedHashMap();
		JSONObject trend = new JSONObject();
		if(list5 != null && list5.size() > 0){
			for(ChartObsr obsr : list5){
				JSONArray json = new JSONArray();
				json.add(obsr.getTestTime().getTime());
				json.add(obsr.getBsValue());
				
				if ("0".equals(obsr.getTimePeriod())) { // 其他时间段
					other.add(json);
				}else if("1".equals(obsr.getTimePeriod())) { // 早晨空腹血糖
					kongfu.add(json);
				} else if("2".equals(obsr.getTimePeriod())) { // 早餐后2小时
					afterBr.add(json);
				}else if("3".equals(obsr.getTimePeriod())) { // 午餐前
					bfLunch.add(json);
				}else if("4".equals(obsr.getTimePeriod())) { // 午餐后2小时
					afLunch.add(json);
				}else if("5".equals(obsr.getTimePeriod())) { //  晚餐前
					bfAf.add(json);
				}else if("6".equals(obsr.getTimePeriod())) { // 晚餐后2小时
					afAf.add(json);
				}else if("7".equals(obsr.getTimePeriod())) { // 睡觉前
					bfSleep.add(json);
				}else if("8".equals(obsr.getTimePeriod())) { // 夜间
					midNight.add(json);
				}
			}
			object.put("other", other);
			object.put("kongfu", kongfu);
			object.put("afterBr", afterBr);
			object.put("bfLunch", bfLunch);
			object.put("afLunch", afLunch);
			object.put("bfAf", bfAf);
			object.put("afAf", afAf);
			object.put("bfSleep", bfSleep);
			object.put("midNight", midNight);
		}
		trend.put("trend", object);
		jsonArr.add(trend);
		return jsonArr;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JSONArray singleSanHeYiChart(List<ChartEcg2> list1, List<ChartEcg2> list2, List<ChartOppg> list3, List<ChartOppg> list4){
		JSONArray jsonArr = new JSONArray();
		
		Map chart1 = new LinkedHashMap();
		JSONArray Polycardia = new JSONArray();
		JSONArray Bradycardia = new JSONArray();
		JSONArray Arrest = new JSONArray();
		JSONArray Missed = new JSONArray();
		JSONArray Wide = new JSONArray();
		JSONArray PVB = new JSONArray();
		JSONArray APB = new JSONArray();
		JSONArray Insert_PVB = new JSONArray();
		JSONArray VT = new JSONArray();
		JSONArray Bigeminy = new JSONArray();
		JSONArray Trigeminy = new JSONArray();
		JSONArray Arrhythmia = new JSONArray();
		if(list1 != null && list1.size() > 0){
			for(ChartEcg2 e2 : list1){
				Map jsonObj = new LinkedHashMap();
				double time = Double.parseDouble((e2.getMeasTimes() == null ? "0:00" : e2.getMeasTimes()).replace(":", "."));
				jsonObj.put(String.valueOf(time), e2.getAbnNum());
				
				if("Polycardia".equals(e2.getAbnName())){
					Polycardia.add(jsonObj);
				}else if("Bradycardia".equals(e2.getAbnName())){
					Bradycardia.add(jsonObj);
				}else if("Arrest".equals(e2.getAbnName())){
					Arrest.add(jsonObj);
				}else if("Missed".equals(e2.getAbnName())){
					Missed.add(jsonObj);
				}else if("Wide".equals(e2.getAbnName())){
					Wide.add(jsonObj);
				}else if("PVB".equals(e2.getAbnName())){
					PVB.add(jsonObj);
				}else if("PAB".equals(e2.getAbnName())){
					APB.add(jsonObj);
				}else if("Insert_PVB".equals(e2.getAbnName())){
					Insert_PVB.add(jsonObj);
				}else if("VT".equals(e2.getAbnName())){
					VT.add(jsonObj);
				}else if("Bigeminy".equals(e2.getAbnName())){
					Bigeminy.add(jsonObj);
				}else if("Trigeminy".equals(e2.getAbnName())){
					Trigeminy.add(jsonObj);
				}else if("Arrhythmia".equals(e2.getAbnName())){
					Arrhythmia.add(jsonObj);
				}else{
					continue;
				}
			}
		}
		chart1.put(InvoValue("Polycardia", "chart1", 3), Polycardia);		//心动过速
		chart1.put(InvoValue("Bradycardia", "chart1", 3), Bradycardia);		//心动过缓
		chart1.put(InvoValue("Arrest", "chart1", 3), Arrest);				//停搏
		chart1.put(InvoValue("Missed", "chart1", 3), Missed);				//漏搏
		chart1.put(InvoValue("Wide", "chart1", 3), Wide);					//宽搏
		chart1.put(InvoValue("PVB", "chart1", 3), PVB);						//室性早搏
		chart1.put(InvoValue("PAB", "chart1", 3), APB);						//房性早搏
		chart1.put(InvoValue("Insert_PVB", "chart1", 3), Insert_PVB);		//插入性室早搏
		chart1.put(InvoValue("VT", "chart1", 3), VT);						//阵发性心动过速
		chart1.put(InvoValue("Bigeminy", "chart1", 3), Bigeminy);			//二联律
		chart1.put(InvoValue("Trigeminy", "chart1", 3), Trigeminy);			//三联律
		chart1.put(InvoValue("Arrhythmia", "chart1", 3), Arrhythmia);		//心律不齐
		jsonArr.add(chart1);
		
		Map chart2 = new LinkedHashMap();
		chart2.put(InvoValue("Polycardia", "chart1", 3), 0);		//心动过速
		chart2.put(InvoValue("Bradycardia", "chart1", 3), 0);		//心动过缓
		chart2.put(InvoValue("Arrest", "chart1", 3), 0);			//停搏
		chart2.put(InvoValue("Missed", "chart1", 3), 0);			//漏搏
		chart2.put(InvoValue("Wide", "chart1", 3), 0);				//宽搏
		chart2.put(InvoValue("PVB", "chart1", 3), 0);				//室性早搏
		chart2.put(InvoValue("PAB", "chart1", 3), 0);				//房性早搏
		chart2.put(InvoValue("Insert_PVB", "chart1", 3), 0);		//插入性室早搏
		chart2.put(InvoValue("VT", "chart1", 3), 0);				//阵发性心动过速
		chart2.put(InvoValue("Bigeminy", "chart1", 3), 0);			//二联律
		chart2.put(InvoValue("Trigeminy", "chart1", 3), 0);			//三联律
		chart2.put(InvoValue("Arrhythmia", "chart1", 3), 0);		//心律不齐
		if(list2 != null && list2.size() > 0){
			for(ChartEcg2 e2 : list2){
				if(InvoValue(e2.getAbnName(),"chart1", 3) != null){
					chart2.put(InvoValue(e2.getAbnName(),"chart1", 3), e2.getNum());	//覆盖操作
				}
			}
		}
		jsonArr.add(chart2);
		
		//-------------------------------------------------------------------
		Map chart3 = new LinkedHashMap();
		JSONArray pulseRate = new JSONArray();
		JSONArray Co = new JSONArray();
		JSONArray Sv = new JSONArray();
		JSONArray Spo = new JSONArray();
		
		JSONArray K = new JSONArray();
		JSONArray Ac = new JSONArray();
		JSONArray Si = new JSONArray();
		JSONArray V = new JSONArray();
		JSONArray Tpr = new JSONArray();
		JSONArray Ci = new JSONArray();
		JSONArray Spi = new JSONArray();
		JSONArray Pm = new JSONArray();
		
		if(list3 != null && list3.size() > 0){
			for(ChartOppg op : list3){
				double key = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("H.m");
				if(op.getMeasureTime() == null){
					key = Double.parseDouble("0:00");
				}else{
					key = Double.parseDouble(sdf.format(op.getMeasureTime()));
				}

				JSONObject p = new JSONObject();
				p.put(String.valueOf(key), op.getPulseRate());
				pulseRate.add(p);
				
				JSONObject c = new JSONObject();
				c.put(String.valueOf(key), op.getCo());
				Co.add(c);
				
				JSONObject sv = new JSONObject();
				sv.put(String.valueOf(key), op.getSv());
				Sv.add(sv);
				
				JSONObject sp = new JSONObject();
				sp.put(String.valueOf(key), op.getSpo());
				Spo.add(sp);
				
				JSONObject k = new JSONObject();
				k.put(String.valueOf(key), op.getK());
				K.add(k);
				
				JSONObject ac = new JSONObject();
				ac.put(String.valueOf(key), op.getAc());
				Ac.add(ac);
				
				JSONObject si = new JSONObject();
				si.put(String.valueOf(key), op.getsI());
				Si.add(si);
				
				JSONObject v = new JSONObject();
				v.put(String.valueOf(key), op.getV());
				V.add(v);
				
				JSONObject tpr = new JSONObject();
				tpr.put(String.valueOf(key), op.getTpr());
				Tpr.add(tpr);
				
				JSONObject ci = new JSONObject();
				ci.put(String.valueOf(key), op.getCi());
				Ci.add(ci);
				
				JSONObject spi = new JSONObject();
				spi.put(String.valueOf(key), op.getSpi());
				Spi.add(spi);
				
				JSONObject pm = new JSONObject();
				pm.put(String.valueOf(key), op.getPm());
				Pm.add(pm);
			}
		}
		chart3.put(InvoValue("pulseRate", "chart3", 3), pulseRate);
		chart3.put(InvoValue("Co", "chart3", 3), Co);
		chart3.put(InvoValue("Sv", "chart3", 3), Sv);
		chart3.put(InvoValue("Spo", "chart3", 3), Spo);
		
		chart3.put(InvoValue("K", "chart3", 3), K);
		chart3.put(InvoValue("Ac", "chart3", 3), Ac);
		chart3.put(InvoValue("Si", "chart3", 3), Si);
		chart3.put(InvoValue("V", "chart3", 3), V);
		chart3.put(InvoValue("TPR", "chart3", 3), Tpr);
		chart3.put(InvoValue("CI", "chart3", 3), Ci);
		chart3.put(InvoValue("SPI", "chart3", 3), Spi);
		chart3.put(InvoValue("PM", "chart3", 3), Pm);
		jsonArr.add(chart3);
		
		Map chart4 = new LinkedHashMap();
		if(list4 != null && list4.size() > 0){
			ChartOppg op = list4.get(0);
			chart4.put(InvoValue("pr", "chart4", 3), op.getPrNum());
			chart4.put(InvoValue("spo", "chart4", 3), op.getSpoNum());
			chart4.put(InvoValue("ac", "chart4", 3), op.getAcNum());
			chart4.put(InvoValue("v", "chart4", 3), op.getvNum());
			
			chart4.put(InvoValue("k", "chart4", 3), op.getkNum());
			chart4.put(InvoValue("sv", "chart4", 3), op.getSvNum());
			chart4.put(InvoValue("si", "chart4", 3), op.getSiNum());
			chart4.put(InvoValue("tpr", "chart4", 3), op.getTprnum());
			chart4.put(InvoValue("ci", "chart4", 3), op.getCiNum());
			chart4.put(InvoValue("spi", "chart4", 3), op.getSpiNum());
			chart4.put(InvoValue("pm", "chart4", 3), op.getPmNum());
			chart4.put(InvoValue("co", "chart4", 3), op.getCoNum());
		}else{
			chart4.put(InvoValue("pr", "chart4", 3), 0);
			chart4.put(InvoValue("spo", "chart4", 3), 0);
			chart4.put(InvoValue("ac", "chart4", 3), 0);
			chart4.put(InvoValue("v", "chart4", 3), 0);
			
			chart4.put(InvoValue("k", "chart4", 3), 0);
			chart4.put(InvoValue("sv", "chart4", 3), 0);
			chart4.put(InvoValue("si", "chart4", 3), 0);
			chart4.put(InvoValue("tpr", "chart4", 3), 0);
			chart4.put(InvoValue("ci", "chart4", 3), 0);
			chart4.put(InvoValue("spi", "chart4", 3), 0);
			chart4.put(InvoValue("pm", "chart4", 3), 0);
			chart4.put(InvoValue("co", "chart4", 3), 0);
		}
		jsonArr.add(chart4);
		
		return jsonArr;
	}
	
	private String InvoValue(Object obj, String flag, int funId){
		if(funId == 1){
			int val = Integer.parseInt(obj.toString());
			//血压异常状态
			if("xyyc".equals(flag)){
				if(val == 0){
					return "正常";
				}else if(val == 1){
					return "低血压";
				}else if(val == 2){
					return "高度高血压";
				}else if(val == 3){
					return "中度高血压";
				}else if(val == 4){
					return "轻度高血压";
				}else if(val == 5){
					return "单纯收缩期高血压";
				}
			}else if("timeQ".equals(flag)){	//时间段 8点前	8-12点	12-18点	18点以后
				if(val == 0){
					return "8点前";
				}else if(val == 1){
					return "8-12点";
				}else if(val == 2){
					return "12-18点";
				}else if(val == 3){
					return "18点以后";
				}
			}else if("gzd".equals(flag)){	//偏低、正常、偏高
				if(val == 0){
					return "偏低";
				}else if(val == 1){
					return "正常";
				}else if(val == 2){
					return "偏高";
				}
			}
			
		}else if(funId == 2){
			String val = obj.toString();
			if("0".equals(val)){
				return "其他时间";
			}else if("1".equals(val)){
				return "早餐前";
			}else if("2".equals(val)){
				return "早餐后2小时";
			}else if("3".equals(val)){
				return "午餐前";
			}else if("4".equals(val)){
				return "午餐后2小时";
			}else if("5".equals(val)){
				return "晚餐前";
			}else if("6".equals(val)){
				return "晚餐后2小时";
			}else if("7".equals(val)){
				return "睡前";
			}else if("8".equals(val)){
				return "夜间";
			}
		}else if(funId == 3){
			String val = obj.toString();
			if("chart1".equals(flag)){
				if("Polycardia".equals(val)){
					return "心动过速";
				}else if("Bradycardia".equals(val)){
					return "心动过缓";
				}else if("Arrest".equals(val)){
					return "停搏";
				}else if("Missed".equals(val)){
					return "漏搏";
				}else if("Wide".equals(val)){
					return "宽搏";
				}else if("PVB".equals(val)){
					return "室性早搏";
				}else if("PAB".equals(val)){
					return "房性早搏";
				}else if("Insert_PVB".equals(val)){
					return "插入性室早搏";
				}else if("VT".equals(val)){
					return "阵发性心动过速";
				}else if("Bigeminy".equals(val)){
					return "二联律";
				}else if("Trigeminy".equals(val)){
					return "三联律";
				}else if("Arrhythmia".equals(val)){
					return "心律不齐";
				}
			}else if("chart3".equals(flag)){
				if("pulseRate".equals(val)){
					return "脉搏率";
				}else if("K".equalsIgnoreCase(val)){
					return "波形特征量";
				}else if("AC".equalsIgnoreCase(val)){
					return "血管顺应度";
				}else if("SI".equalsIgnoreCase(val)){
					return "血管硬化指数";
				}else if("V".equalsIgnoreCase(val)){
					return "血液粘度";
				}else if("TPR".equalsIgnoreCase(val)){
					return "外周阻力";
				}else if("CI".equalsIgnoreCase(val)){
					return "心指数";
				}else if("SPI".equalsIgnoreCase(val)){
					return "心搏指数";
				}else if("PM".equalsIgnoreCase(val)){
					return "平均动脉压";
				}else if("Co".equalsIgnoreCase(val)){
					return "平均每分射血量";
				}else if("Sv".equalsIgnoreCase(val)){
					return "心脏每次射血量";
				}else if("Spo".equalsIgnoreCase(val)){
					return "血氧饱和度";
				}
			}else if("chart4".equals(flag)){
				/*
			  	PRLevel		PR	平均心率
				KLevel		K	波形特征量
				SVLevel		SV	心脏每搏射血量
				COLevel		CO	平均每分射血量
				ACLevel		AC	血管顺应度
				SILevel		SI	血管硬化指数
				VLevel		V	血液粘度
				TPRLevel	TPR	外周阻力
				SPOLevel	Spo	血氧饱和度
				CILevel		CI	心指数
				SPILevel	SPI	心搏指数
				pm		pm	平均动脉压	70-105
			 */
				if("pr".equalsIgnoreCase(val)){
					return "平均心率";
				}else if("K".equalsIgnoreCase(val)){
					return "波形特征量";
				}else if("SV".equalsIgnoreCase(val)){
					return "心脏每搏射血量";
				}else if("CO".equalsIgnoreCase(val)){
					return "平均每分射血量";
				}else if("SI".equalsIgnoreCase(val)){
					return "血管硬化指数";
				}else if("TPR".equalsIgnoreCase(val)){
					return "外周阻力";
				}else if("CI".equalsIgnoreCase(val)){
					return "心指数";
				}else if("SPI".equalsIgnoreCase(val)){
					return "心搏指数";
				}else if("PM".equalsIgnoreCase(val)){
					return "平均动脉压";
				}else if("spo".equalsIgnoreCase(val)){
					return "血氧饱和度";
				}else if("ac".equalsIgnoreCase(val)){
					return "血管顺应度";
				}else if("v".equalsIgnoreCase(val)){
					return "血液粘度";
				}
			}
		}
		
		return null;
	}
	
	/** 
     * @Title: getMeasureRecordList 
     * @Description: 获取测量记录list(返回新增事件类型)
     * @liuxaioqin
     * @createDate 2016-01-28 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public String getMeasureRecordList(OmdsParam param) throws Exception{
        ReturnResult re = new ReturnResult();
        
        // 1 血压 2 血糖 3 三合一 4 动态心电（迷你）5 查询全部
        if (param.getDataType() == 1) {
            // 查询血压信息
            List<ReturnMeasureData> measureDataList = measureDao.findOsbpByOmdsValueNew(param);
            if (measureDataList.size() > 0) {
                re.setState(0);
                re.setMessage("查询会员血压测量数据成功");
                re.setContent(measureDataList);
            } else {
                re.setState(1);
                re.setMessage("该会员没有匹配的血压测量数据");
            }
        } else if (param.getDataType() == 2) {
            // 查询血糖信息
            List<ReturnMeasureData> measureDataList = measureDao.findObsrByOmdsValueNew(param);
            if (measureDataList.size() > 0) {
                re.setState(0);
                re.setMessage("查询会员血糖测量数据成功");
                re.setContent(measureDataList);
            } else {
                re.setState(1);
                re.setMessage("该会员没有匹配的血糖测量数据");
            }
        } else if (param.getDataType() == 3) {
            // 查询三合一信息
            List<ReturnMeasureData> threeJoinOnelist = measureDao.findThreeJoinOneByParamNew(param);
            if (threeJoinOnelist.size() > 0 ) {
                re.setState(0);
                re.setMessage("查询会员三合一测量数据成功");
                re.setContent(threeJoinOnelist);
            } else {
                re.setState(1);
                re.setMessage("该会员没有匹配的三合一测量数据");
            }
        } else if (param.getDataType() == 4) {
            // 查询动态心电信息
            List<ReturnMeasureData> measureDataList = measureDao.findOecgByOmdsValueNew(param);
            if (measureDataList.size() > 0) {
                re.setState(0);
                re.setMessage("查询会员心电测量数据成功");
                re.setContent(measureDataList);
            } else {
                re.setState(1);
                re.setMessage("该会员没有匹配的心电测量数据");
            }
        } else if (param.getDataType() == 5) {
            // 查询所有测量数据
            List<ReturnMeasureData> measureDataAllList = measureDao.findAllMeasureData(param);
            if (measureDataAllList.size() > 0) {
                re.setState(0);
                re.setMessage("查询会员所有类型测量数据成功！");
                re.setContent(measureDataAllList);
            } else {
                re.setState(1);
                re.setMessage("该会员没有测量数据");
            }
        }else {
            re.setState(3);
            re.setMessage("传递参数有错误");
        }
        return JSON.toJSONString(re);
    }
	
    /** 
     * @Title: getAllMeasureRecord 
     * @Description: 获取各种测量类型记录
     * @liuxaioqin
     * @createDate 2016-05-18 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String getAllMeasureRecord(HttpServletRequest request)throws Exception{
		ReturnResult result = new ReturnResult();
		String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        Integer memberId = jsonObject.getInteger("memberId");
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        List<Map<String,Object>> listMap = measureDao.getAllMeasureRecord(memberId);
        if(listMap != null && listMap.size()>0){
        	result.setState(0);
       	    result.setContent(listMap);
            result.setMessage("获取会员的测量记录成功");
            logger.info("获取会员的测量记录成功");
        }else{
        	result.setState(3);
            result.setMessage("您没有测量记录");
            logger.info("您没有测量记录");
        }
		return JSON.toJSONString(result);
	}
    
	/** 
     * @Title: findLastestMonthMeasureCount 
     * @Description: 获取最近一个月内四种类型的测量条数
     * @liuxaioqin
     * @createDate 2016-05-18 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String findLastestMonthMeasureCount(HttpServletRequest request)throws Exception{
		ReturnResult result = new ReturnResult();
		String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        Integer memberId = jsonObject.getInteger("memberId");
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        List<Map<String,Object>> listMap = measureDao.findLastestMonthMeasureCount(memberId);
        if(listMap != null && listMap.size()>0){
        	result.setState(0);
       	    result.setContent(listMap);
            result.setMessage("获取会员最近一个月内四种类型的测量条数成功");
            logger.info("获取会员最近一个月内四种类型的测量条数成功");
        }else{
        	result.setState(3);
            result.setMessage("您最近一个月没有测量记录");
            logger.info("您最近一个月没有测量记录");
        }
		return JSON.toJSONString(result);
	}
	
	/** 
     * @Title: findAllMeasureRecordByParam 
     * @Description: 根据不同条件查询测量数据
     * @liuxaioqin
     * @createDate 2016-05-30 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String findAllMeasureRecordByParam(HttpServletRequest request)throws Exception{
		ReturnResult result = new ReturnResult();
		String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        JSONObject jsonObject = JSONObject.parseObject(callValue.getParam());
        Integer memberId = jsonObject.getInteger("memberId");
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        Integer eventType = jsonObject.getInteger("eventType");
        if(eventType != null && eventType <= 0){
            result.setState(1);
            result.setMessage("参数eventType【"+eventType+"】应为正整数！");
            logger.info("参数eventType【"+eventType+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        String beginTime = jsonObject.getString("beginTime");
        if(!StringUtils.isEmpty(beginTime)){
        	beginTime += " 00:00:00";
        }
        String endTime = jsonObject.getString("endTime");
        if(!StringUtils.isEmpty(endTime)){
        	endTime += " 23:59:59";
        }
        Integer isAbnormal = jsonObject.getInteger("isAbnormal");
        Integer pageNow = 1;
        pageNow = jsonObject.getInteger("pageNow");
        Integer pageSize = 10;
        pageSize = jsonObject.getInteger("pageSize");
        List<Map<String,Object>> maplist = new ArrayList<Map<String,Object>>();
        maplist = measureDao.findAllMeasureRecordByParam(memberId,eventType,isAbnormal,beginTime,endTime,pageNow,pageSize);
        if(maplist != null && maplist.size() > 0){
        	result.setState(0);
        	result.setContent(maplist);
            result.setMessage("根据不同条件查询测量数据成功");
            logger.info("根据不同条件查询测量数据成功");
        }else{
        	result.setState(3);
            result.setMessage("没有符合条件的测量数据");
            logger.info("没有符合条件的测量数据");
        }
		return JSON.toJSONString(result);
	}
	
	/** 
     * @Title: findMeasRecordByEventIdAndType 
     * @Description: 根据测量事件id和type获取测量数据
     * @liuxaioqin
     * @createDate 2016-05-30 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
	public String findMeasRecordByEventIdAndType(HttpServletRequest request)throws Exception{
		ReturnResult result = new ReturnResult();
		String params = request.getParameter("params");
        CallValue callValue = JSON.parseObject(params, CallValue.class);
        OmdsParam param = JSON.parseObject(callValue.getParam(),OmdsParam.class);
        Integer memberId = param.getMemberId();
        if(memberId <= 0){
            result.setState(1);
            result.setMessage("参数memberId【"+memberId+"】应为正整数！");
            logger.info("参数memberId【"+memberId+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        String eventIds = param.getEventIds();
        if(StringUtils.isEmpty(eventIds)){
        	result.setState(1);
            result.setMessage("参数eventIds【"+eventIds+"】不能为空！");
            logger.info("参数eventIds【"+eventIds+"】不能为空！");
            return JSON.toJSONString(result);
        }
        Integer eventType = param.getEventType();
        if(eventType == null || eventType <= 0){
        	result.setState(1);
            result.setMessage("参数eventType【"+eventType+"】应为正整数！");
            logger.info("参数eventType【"+eventType+"】应为正整数！");
            return JSON.toJSONString(result);
        }
        // 1 血压 2 血糖 3 三合一 4 动态心电（迷你）
        if (eventType == 1){
            // 查询血压信息
            List<ReturnMeasureData> measureDataList = measureDao.findOsbpByOmdsValueNew(param);
            if(measureDataList.size() > 0){
            	result.setState(0);
            	result.setContent(measureDataList);
            	result.setMessage("查询会员血压测量数据成功");
            	logger.info("查询会员血压测量数据成功");
            }else{
            	result.setState(3);
            	result.setMessage("该会员没有匹配的血压测量数据");
            	logger.info("该会员没有匹配的血压测量数据");
            }
        }else if(eventType == 2){
            // 查询血糖信息
            List<ReturnMeasureData> measureDataList = measureDao.findObsrByOmdsValueNew(param);
            if(measureDataList.size() > 0){
            	result.setState(0);
            	result.setContent(measureDataList);
            	result.setMessage("查询会员血糖测量数据成功");
            	logger.info("查询会员血糖测量数据成功");
            }else{
            	result.setState(3);
            	result.setMessage("该会员没有匹配的血糖测量数据");
            	logger.info("该会员没有匹配的血糖测量数据");
            }
        }else if(eventType == 3){
            // 查询三合一信息
            List<ReturnMeasureData> threeJoinOnelist = measureDao.findThreeJoinOneByParamNew(param);
            if (threeJoinOnelist.size() > 0 ){
            	result.setState(0);
            	result.setContent(threeJoinOnelist);
            	result.setMessage("查询会员三合一测量数据成功");
            	logger.info("根据不同条件查询测量数据成功");
            }else{
            	result.setState(3);
            	result.setMessage("该会员没有匹配的三合一测量数据");
            	logger.info("该会员没有匹配的三合一测量数据");
            }
        }else if(eventType == 4) {
            // 查询动态心电信息
            List<ReturnMeasureData> measureDataList = measureDao.findOecgByOmdsValueNew(param);
            if(measureDataList.size() > 0){
            	result.setState(0);
            	result.setContent(measureDataList);
            	result.setMessage("查询会员心电测量数据成功");
            	logger.info("查询会员心电测量数据成功");
            }else{
            	result.setState(3);
                result.setMessage("该会员没有匹配的心电测量数据");
                logger.info("该会员没有匹配的心电测量数据");
            }
        }
        return JSON.toJSONString(result);
	}
	
}
