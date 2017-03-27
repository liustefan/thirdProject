package com.zkhk.dao.Impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zkhk.dao.MeasureDao;
import com.zkhk.entity.AnomalyEcg;
import com.zkhk.entity.ChartEcg2;
import com.zkhk.entity.ChartObsr;
import com.zkhk.entity.ChartOppg;
import com.zkhk.entity.ChartOsbp;
import com.zkhk.entity.ChartParam;
import com.zkhk.entity.Ecg2;
import com.zkhk.entity.Mem8;
import com.zkhk.entity.Obsr;
import com.zkhk.entity.Oecg;
import com.zkhk.entity.Omds;
import com.zkhk.entity.OmdsParam;
import com.zkhk.entity.Oppg;
import com.zkhk.entity.Osbp;
import com.zkhk.entity.RecordParam;
import com.zkhk.entity.ReturnMeasureData;
import com.zkhk.jdbc.JdbcService;
import com.zkhk.util.TimeUtil;

@Repository(value = "measureDao")
public class MeasureDaoImpl implements MeasureDao {
	@Resource
	private JdbcService jdbcService;

	public List<Osbp> findOsbpByOmdsValue(OmdsParam param) throws Exception {
		String sql = "select b.Docentry,b.EventId,b.UploadTime,b.TestTime,b.Abnormal,b.timePeriod,b.SBP,b.DBP,b.PulseRate "
				+ "from omds a left join osbp b on a.eventId=b.eventid where a.MemberId=? and a.EventType=? and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc limit ? ";
		List<Osbp> list = new ArrayList<Osbp>();
		SqlRowSet rowSet = jdbcService.query(
				sql,
				new Object[] { param.getMemberId(), param.getDataType(), param.getTimeStart(),
						param.getTimeEnd(), param.getCount() });
		while (rowSet.next()) {
			Osbp osbp = new Osbp();
			osbp.setAbnormal(rowSet.getString("Abnormal"));
			osbp.setDbp(rowSet.getInt("DBP"));
			osbp.setEventId(rowSet.getLong("EventId"));
			osbp.setId(rowSet.getLong("Docentry"));
			osbp.setPulseRate(rowSet.getInt("PulseRate"));
			osbp.setSbp(rowSet.getInt("SBP"));
			osbp.setTestTime(TimeUtil.paserDatetime2(rowSet
					.getString("TestTime")));
			osbp.setTimePeriod(rowSet.getInt("timePeriod"));
			osbp.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			osbp.setMemberId(param.getMemberId());
			list.add(osbp);
		}
		return list;
	}

	public List<Obsr> findObsrByOmdsValue(OmdsParam param) throws Exception {
		String sql = "select b.Docentry,b.EventId,b.Memberid,b.UploadTime,b.TestTime,b.BsValue,b.timePeriod,b.AnalysisResult"
				+ " from omds a left join obsr b on a.eventId=b.eventId where a.MemberId=? and a.EventType=? and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc limit ? ";
		List<Obsr> list = new ArrayList<Obsr>();
		SqlRowSet rowSet = jdbcService.query(
				sql,
				new Object[] { param.getMemberId(), param.getDataType(),param.getTimeStart(),
						param.getTimeEnd(), param.getCount() });
		while (rowSet.next()) {
			Obsr osbr = new Obsr();
			osbr.setAnalysisResult(rowSet.getInt("AnalysisResult"));
			osbr.setBsValue(rowSet.getFloat("BsValue"));
			osbr.setEventId(rowSet.getLong("EventId"));
			osbr.setId(rowSet.getLong("Docentry"));
			osbr.setMemberId(param.getMemberId());
			osbr.setTestTime(TimeUtil.paserDatetime2(rowSet
					.getString("TestTime")));
			osbr.setTimePeriod(rowSet.getInt("timePeriod"));
			osbr.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			list.add(osbr);
		}
		return list;
	}

	public List<Oecg> findOecgByOmdsValue(OmdsParam param) throws Exception {
		String sql = "select a.eventType, b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
				+ " from omds a left join oecg b on a.eventId=b.eventid where a.MemberId=? and a.EventType=?   and b.measTime between ? and ? and b.delTag='0' order by b.measTime desc limit ? ";
		List<Oecg> list = new ArrayList<Oecg>();
		SqlRowSet rowSet = jdbcService.query(
				sql,
				new Object[] { param.getMemberId(), param.getDataType(), param.getTimeStart(),
						param.getTimeEnd(), param.getCount() });
		while (rowSet.next()) {
			Oecg oecg = new Oecg();
			oecg.setFs(rowSet.getInt("fs"));
			oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
			oecg.setEcgResult(rowSet.getInt("ECGResult"));
			oecg.setEventId(rowSet.getLong("EventId"));
			oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
			oecg.setFastestTime(rowSet.getInt("FastestTime"));
			oecg.setFrepPsd(rowSet.getString("FreqPSD"));
			oecg.setHeartNum(rowSet.getInt("HeartNum"));
			oecg.setId(rowSet.getLong("Docentry"));
			oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("measTime")));
			oecg.setRawEcg(rowSet.getString("RawECG"));
			oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
			oecg.setRrInterval(rowSet.getString("RRInterval"));
			oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
			oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
			oecg.setStatusTag(rowSet.getInt("StatusTag"));
			oecg.setTimeLength(rowSet.getInt("TimeLength"));
			oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oecg.setMemberId(param.getMemberId());
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
			oecg.setDeviceCode(rowSet.getString("DeviceCode"));
			oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			oecg.setPnn50(rowSet.getFloat("pnn50"));
			oecg.setSdnn(rowSet.getFloat("sdnn"));
			oecg.setHrvi(rowSet.getFloat("hrvi"));

			list.add(oecg);
		}
		return list;
	}

	public List<Oppg> findOppgByOmdsValue(OmdsParam param) throws Exception {
		String sql = "select b.BluetoothMacAddr, b.DeviceCode, b.fs, b.Docentry,b.EventId,b.TimeLength,b.UploadTime,b.MeasureTime,b.PulsebeatCount,b.SlowPulse,b.SlowTime,b.QuickPulse,b.QuickTime,b.PulseRate,b.Spo,b.SPO1,b.CO,b.SI,b.SV,b.CI,b.SPI,b.K,b.K1,b.V,b.TPR,b.PWTT,b.Pm,b.AC,"
				+ "  b.ImageNum,b.AbnormalData,b.Ppgrr,b.RawPPG,b.PPGResult,b.StatusTag,b.kLevel,b.svLevel,b.coLevel,b.siLevel,b.vLevel,b.tprLevel,b.spoLevel,b.ciLevel,b.spiLevel,b.pwttLevel,b.acLevel,b.prLevel"
				+ "  from omds a left join oppg b on a.eventId=b.eventid where a.MemberId=? and a.EventType=? and b.MeasureTime between ? and ? and b.delTag='0' order by b.MeasureTime desc limit ? ";
		List<Oppg> list = new ArrayList<Oppg>();
		SqlRowSet rowSet = jdbcService.query(
				sql,
				new Object[] { param.getMemberId(), param.getDataType(), param.getTimeStart(),
						param.getTimeEnd(), param.getCount() });
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
			oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("MeasureTime")));
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
			oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oppg.setV(rowSet.getFloat("V"));
			oppg.setMemberId(param.getMemberId());
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
			oppg.setPrLevel(rowSet.getInt("prLevel"));
			oppg.setDeviceCode(rowSet.getString("DeviceCode"));
			oppg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));

			list.add(oppg);
		}
		return list;
	}

	public List<Osbp> findOsbpByRecordValue(RecordParam param) throws Exception {
		String sql = "select b.Docentry,b.EventId,b.UploadTime,b.TestTime,b.Abnormal,b.timePeriod,b.SBP,b.DBP,b.PulseRate "
				+ "from osbp b where b.MemberId=?  and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc";
		List<Osbp> list = new ArrayList<Osbp>();
		SqlRowSet rowSet = jdbcService.query(sql,
				new Object[] { param.getMemberId(), param.getMeasureStart(),
						param.getMeasureEnd() });
		while (rowSet.next()) {
			Osbp osbp = new Osbp();
			osbp.setAbnormal(rowSet.getString("Abnormal"));
			osbp.setDbp(rowSet.getInt("DBP"));
			osbp.setEventId(rowSet.getLong("EventId"));
			osbp.setId(rowSet.getLong("Docentry"));
			osbp.setPulseRate(rowSet.getInt("PulseRate"));
			osbp.setSbp(rowSet.getInt("SBP"));
			osbp.setTestTime(TimeUtil.paserDatetime2(rowSet
					.getString("TestTime")));
			osbp.setTimePeriod(rowSet.getInt("timePeriod"));
			osbp.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			osbp.setMemberId(param.getMemberId());
			list.add(osbp);
		}
		return list;
	}

	public List<Obsr> findObsrByRecordValue(RecordParam param) throws Exception {
		String sql = "select b.Docentry,b.EventId,b.Memberid,b.UploadTime,b.TestTime,b.BsValue,b.timePeriod,b.AnalysisResult"
				+ " from  obsr b where b.MemberId=? and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc";
		List<Obsr> list = new ArrayList<Obsr>();
		SqlRowSet rowSet = jdbcService.query(sql,
				new Object[] { param.getMemberId(), param.getMeasureStart(),
						param.getMeasureEnd() });
		while (rowSet.next()) {
			Obsr osbr = new Obsr();
			osbr.setAnalysisResult(rowSet.getInt("AnalysisResult"));
			osbr.setBsValue(rowSet.getFloat("BsValue"));
			osbr.setEventId(rowSet.getLong("EventId"));
			osbr.setId(rowSet.getLong("Docentry"));
			osbr.setMemberId(param.getMemberId());
			osbr.setTestTime(TimeUtil.paserDatetime2(rowSet
					.getString("TestTime")));
			osbr.setTimePeriod(rowSet.getInt("timePeriod"));
			osbr.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			list.add(osbr);
		}
		return list;
	}

	public List<Oppg> findOppgByRecordValue(RecordParam param) throws Exception {
		String sql = "select b.BluetoothMacAddr, b.DeviceCode, b.fs, b.Docentry,b.EventId,b.TimeLength,b.UploadTime,b.MeasureTime,b.PulsebeatCount,b.SlowPulse,b.SlowTime,b.QuickPulse,b.QuickTime,b.PulseRate,b.Spo,b.SPO1,b.CO,b.SI,b.SV,b.CI,b.SPI,b.K,b.K1,b.V,b.TPR,b.PWTT,b.Pm,b.AC,"
				+ "  b.ImageNum,b.AbnormalData,b.Ppgrr,b.RawPPG,b.PPGResult,b.StatusTag,b.kLevel,b.svLevel,b.coLevel,b.siLevel,b.vLevel,b.tprLevel,b.spoLevel,b.ciLevel,b.spiLevel,b.pwttLevel,b.acLevel,b.prLevel"
				+ " from oppg b  where b.MemberId=? and b.MeasureTime between ? and ? and b.delTag='0' order by b.MeasureTime desc  ";
		List<Oppg> list = new ArrayList<Oppg>();
		SqlRowSet rowSet = jdbcService.query(sql,
				new Object[] { param.getMemberId(), param.getMeasureStart(),
						param.getMeasureEnd() });
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
			oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("MeasureTime")));
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
			oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oppg.setV(rowSet.getFloat("V"));
			oppg.setMemberId(param.getMemberId());
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
			oppg.setPrLevel(rowSet.getInt("prLevel"));
			oppg.setDeviceCode(rowSet.getString("DeviceCode"));
			oppg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			// System.out.println(rowSet.getInt("kLevel")+"--------"+rowSet.getInt("vLevel"));
			list.add(oppg);
		}
		return list;
	}

	public List<Oecg> findOecgByRecordValue(RecordParam param) throws Exception {
		String sql = "select a.eventType, b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
				+ " from  oecg b left join  omds a on b.eventId=a.eventId   where b.MemberId=?  and b.measTime between ? and ? and b.delTag='0' order by b.measTime desc  ";
		List<Oecg> list = new ArrayList<Oecg>();
		SqlRowSet rowSet = jdbcService.query(sql,
				new Object[] { param.getMemberId(), param.getMeasureStart(),
						param.getMeasureEnd() });
		while (rowSet.next()) {
			Oecg oecg = new Oecg();
			oecg.setId(rowSet.getLong("Docentry"));
			List<Ecg2> ecg2s = findEcg2ById(oecg.getId() + "");
			oecg.setEcg2s(ecg2s);
			oecg.setFs(rowSet.getInt("fs"));
			oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
			oecg.setEcgResult(rowSet.getInt("ECGResult"));
			oecg.setEventId(rowSet.getLong("EventId"));
			oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
			oecg.setFastestTime(rowSet.getInt("FastestTime"));
			oecg.setFrepPsd(rowSet.getString("FreqPSD"));
			oecg.setHeartNum(rowSet.getInt("HeartNum"));
			oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("measTime")));
			oecg.setRawEcg(rowSet.getString("RawECG"));
			oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
			oecg.setRrInterval(rowSet.getString("RRInterval"));
			oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
			oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
			oecg.setStatusTag(rowSet.getInt("StatusTag"));
			oecg.setTimeLength(rowSet.getInt("TimeLength"));
			oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oecg.setMemberId(param.getMemberId());
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
			oecg.setDeviceCode(rowSet.getString("DeviceCode"));
			oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			oecg.setPnn50(rowSet.getFloat("pnn50"));
			oecg.setSdnn(rowSet.getFloat("sdnn"));
			oecg.setHrvi(rowSet.getFloat("hrvi"));
			oecg.setEventType(rowSet.getInt("eventType"));
			list.add(oecg);
		}
		return list;
	}

	public List<AnomalyEcg> findAnomalyEcgbyId(long id, String name)
			throws Exception {
		List<AnomalyEcg> ecgs = new ArrayList<AnomalyEcg>();
		String useNameString = getName(name);
		String sql = "select ObjectId,AbECGType,AbECGTime from ecg1 where Docentry=? and AbECGType like '%"
				+ useNameString + "%'";
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
			name = "ST";
		} else if ("Bradycardia".equals(name)) {
			name = "SB";
		} else if ("Arrest".equals(name)) {
			name = "SA";
		} else if ("Missed".equals(name)) {
			name = "MB";
		} else if ("Wide".equals(name)) {
			name = "WS";
		} else if ("PVB".equals(name)) {
			name = "VPB";
		} else if ("PAB".equals(name)) {
			name = "APB";
		} else if ("Insert_PVB".equals(name)) {
			name = "IVBP";
		} else if ("VT".equals(name)) {
			name = "VT";
		} else if ("Bigeminy".equals(name)) {
			name = "BG";
		} else if ("Trigeminy".equals(name)) {
			name = "TRG";
		} else if ("Arrhythmia".equals(name)) {
			name = "AR";
		}
		return name;
	}

	public List<Ecg2> findEcg2ById(String ids) throws Exception {
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

	public void saveObsrData(Obsr obsr) throws Exception {
		int tableNum = (int) (obsr.getId() % 100);
		String tableName = "obsr_" + tableNum;
		String sql = "INSERT INTO "
				+ tableName
				+ " (Docentry,EventId ,Memberid ,UploadTime ,TestTime ,DelTag ,BsValue ,timePeriod ,AnalysisResult ,DeviceCode ,BluetoothMacAddr ) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		jdbcService.doExecuteSQL(
				sql,
				new Object[] { obsr.getId(), obsr.getEventId(),
						obsr.getMemberId(), obsr.getUploadTime(),
						obsr.getTestTime(), 0, obsr.getBsValue(),
						obsr.getTimePeriod(), obsr.getAnalysisResult(),
						obsr.getDeviceCode(), obsr.getBluetoothMacAddr() });

	}

	public void saveOsbpData(Osbp osbp) throws Exception {
		int tableNum = (int) (osbp.getId() % 100);
		String tableName = "osbp_" + tableNum;
		// System.out.println(tableName);
		String sql = "INSERT INTO "
				+ tableName
				+ " (Docentry,EventId ,Memberid ,UploadTime ,TestTime ,DelTag ,Abnormal ,timePeriod ,SBP ,DBP ,PulseRate ,BluetoothMacAddr ,DeviceCode ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcService.doExecuteSQL(
				sql,
				new Object[] { osbp.getId(), osbp.getEventId(),
						osbp.getMemberId(), osbp.getUploadTime(),
						osbp.getTestTime(), 0, osbp.getAbnormal(),
						osbp.getTimePeriod(), osbp.getSbp(), osbp.getDbp(),
						osbp.getPulseRate(), osbp.getBluetoothMacAddr(),
						osbp.getDeviceCode() });
	}

	public synchronized long insertEventId() {
		String sql = "INSERT INTO omds_eventid VALUES()";
		jdbcService.doExecute2(sql);
		long id = jdbcService.getMaxId("omds_eventid", "eventId");
		return id;
	}

	public void saveOmdsData(Omds omds) throws Exception {
		int tableNum = (int) (omds.getEventId() % 100);
		String tableName = "omds_" + tableNum;
		String sql = "INSERT INTO "
				+ tableName
				+ " ( eventid ,Memberid ,UploadTime ,EventType ,WheAbnTag ,StatusTag,TimeLength ) VALUES (?,?,?,?,?,?,?)";
		jdbcService.doExecuteSQL(
				sql,
				new Object[] { omds.getEventId(), omds.getMemberId(),
						omds.getUploadTime(), omds.getDataType(),
						omds.getWheAbnTag(), omds.getStatusTag(),
						omds.getTimeLength() });

	}

	public synchronized long insertId(String tableName) throws Exception {

		String sql = "INSERT INTO " + tableName + " VALUES ()";
//		jdbcService.doExecute2(sql);
//		long id = jdbcService.getMaxId(tableName, "Docentry");
		long id=	jdbcService.addId(sql);
		return id;

	}



	public void saveOecgData(Oecg oecg) throws Exception {
		int tableNum = (int) (oecg.getId() % 100);
		String tableName = "oecg_" + tableNum;
		String sql = "INSERT INTO "
				+ tableName
				+ " ( Docentry ,EventId ,Memberid ,UploadTime ,DeviceCode ,BluetoothMacAddr ,RawECG ,DelTag ,StatusTag,fs,TimeLength,MeasTime,addValue) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcService.doExecuteSQL(
				sql,
				new Object[] { oecg.getId(), oecg.getEventId(),
						oecg.getMemberId(), oecg.getUploadTime(),
						oecg.getDeviceCode(), oecg.getBluetoothMacAddr(),
						oecg.getRawEcg(), 0, 0, oecg.getFs(),
						oecg.getTimeLength(), oecg.getMeasureTime(),
						oecg.getAddValue() });
	}

	public void saveOppgData(Oppg oppg) throws Exception {
		int tableNum = (int) (oppg.getId() % 100);
		String tableName = "oppg_" + tableNum;
		String sql = "INSERT INTO "
				+ tableName
				+ "( Docentry ,EventId ,Memberid ,UploadTime ,RawPPG ,BluetoothMacAddr ,DeviceCode ,StatusTag ,DelTag,FS,TimeLength,MeasureTime,addValue ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcService.doExecuteSQL(
				sql,
				new Object[] { oppg.getId(), oppg.getEventId(),
						oppg.getMemberId(), oppg.getUploadTime(),
						oppg.getRawPpg(), oppg.getBluetoothMacAddr(),
						oppg.getDeviceCode(), 0, 0, oppg.getFs(),
						oppg.getTimeLength(), oppg.getMeasureTime(),
						oppg.getAddValue() });

	}

	public Osbp findObsrByMemberId(int memberId) throws Exception {
		String sql = "select b.Docentry,b.EventId,b.UploadTime,b.TestTime,b.Abnormal,b.timePeriod,b.SBP,b.DBP,b.PulseRate from osbp b where b.MemberId=?  and b.delTag='0' order by b.TestTime desc limit 1";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId });
		while (rowSet.next()) {
			Osbp osbp = new Osbp();
			osbp.setDbp(rowSet.getInt("DBP"));
			osbp.setSbp(rowSet.getInt("SBP"));
			return osbp;
		}
		return null;
	}

	public Oecg findOecgById(long id) throws Exception {
		String sql = "select b.sdnn,b.pnn50,b.hrvi, b.BluetoothMacAddr, b.DeviceCode, a.eventType ,b.fs, b.memberId, b.measTime,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
				+ " from  oecg b left join omds a on b.EventId=a.EventId  where b.Docentry=?";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { id });
		if (rowSet.next()) {
			Oecg oecg = new Oecg();
			oecg.setFs(rowSet.getInt("fs"));
			oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
			oecg.setEcgResult(rowSet.getInt("ECGResult"));
			oecg.setEventId(rowSet.getLong("EventId"));
			oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
			oecg.setFastestTime(rowSet.getInt("FastestTime"));
			oecg.setFrepPsd(rowSet.getString("FreqPSD"));
			oecg.setHeartNum(rowSet.getInt("HeartNum"));
			oecg.setId(id);
			oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("measTime")));
			oecg.setRawEcg(rowSet.getString("RawECG"));
			oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
			oecg.setRrInterval(rowSet.getString("RRInterval"));
			oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
			oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
			oecg.setStatusTag(rowSet.getInt("StatusTag"));
			oecg.setTimeLength(rowSet.getInt("TimeLength"));
			oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oecg.setMemberId(rowSet.getInt("memberId"));
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
			oecg.setDeviceCode(rowSet.getString("DeviceCode"));
			oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			oecg.setPnn50(rowSet.getFloat("pnn50"));
			oecg.setSdnn(rowSet.getFloat("sdnn"));
			oecg.setHrvi(rowSet.getFloat("hrvi"));
			return oecg;

		}
		return null;
	}

	public Oppg findOppgById(long id) throws Exception {
		String sql = "select b.BluetoothMacAddr, b.DeviceCode, b.fs, b.memberId, b.EventId,b.TimeLength,b.UploadTime,b.MeasureTime,b.PulsebeatCount,b.SlowPulse,b.SlowTime,b.QuickPulse,b.QuickTime,b.PulseRate,b.Spo,b.SPO1,b.CO,b.SI,b.SV,b.CI,b.SPI,b.K,b.K1,b.V,b.TPR,b.PWTT,b.Pm,b.AC,"
				+ "  b.ImageNum,b.AbnormalData,b.Ppgrr,b.RawPPG,b.PPGResult,b.StatusTag,b.kLevel,b.svLevel,b.coLevel,b.siLevel,b.vLevel,b.tprLevel,b.spoLevel,b.ciLevel,b.spiLevel,b.pwttLevel,b.acLevel,b.prLevel "
				+ " from oppg b  where b.Docentry=? ";

		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { id });
		if (rowSet.next()) {
			Oppg oppg = new Oppg();
			oppg.setFs(rowSet.getInt("fs"));
			oppg.setAbnormalData(rowSet.getInt("AbnormalData"));
			oppg.setAc(rowSet.getFloat("AC"));
			oppg.setCi(rowSet.getFloat("CI"));
			oppg.setCo(rowSet.getFloat("CO"));
			oppg.setEventId(rowSet.getLong("EventId"));
			oppg.setId(id);
			oppg.setImageNum(rowSet.getInt("ImageNum"));
			oppg.setK(rowSet.getFloat("K"));
			oppg.setK1(rowSet.getFloat("K1"));
			oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("MeasureTime")));
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
			oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oppg.setV(rowSet.getFloat("V"));
			oppg.setMemberId(rowSet.getInt("memberId"));
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
			oppg.setDeviceCode(rowSet.getString("DeviceCode"));
			oppg.setPrLevel(rowSet.getInt("prLevel"));
			oppg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			return oppg;
		}
		return null;
	}

	public List<Oecg> findAnomalyEcgbyMemberIdAndTime(int memberId,
			String startTime, String endTime) throws Exception {
		String sql = "select b.sdnn,b.pnn50,b.hrvi, b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag"
				+ " from omds a left join oecg b on a.eventId=b.eventid where a.MemberId=?   and b.ECGResult>? and  b.measTime between ? and ? and b.delTag='0' order by b.measTime desc  ";
		List<Oecg> list = new ArrayList<Oecg>();
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId, 0,
				startTime, endTime });
		while (rowSet.next()) {
			Oecg oecg = new Oecg();
			oecg.setId(rowSet.getLong("Docentry"));
			List<Ecg2> ecg2s = findEcg2ById(oecg.getId() + "");
			oecg.setEcg2s(JSON.toJSONString(ecg2s));
			oecg.setFs(rowSet.getInt("fs"));
			oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
			oecg.setEcgResult(rowSet.getInt("ECGResult"));
			oecg.setEventId(rowSet.getLong("EventId"));
			oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
			oecg.setFastestTime(rowSet.getInt("FastestTime"));
			oecg.setFrepPsd(rowSet.getString("FreqPSD"));
			oecg.setHeartNum(rowSet.getInt("HeartNum"));
			oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("measTime")));
			oecg.setRawEcg(rowSet.getString("RawECG"));
			oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
			oecg.setRrInterval(rowSet.getString("RRInterval"));
			oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
			oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
			oecg.setStatusTag(rowSet.getInt("StatusTag"));
			oecg.setTimeLength(rowSet.getInt("TimeLength"));
			oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oecg.setMemberId(memberId);
			oecg.setPnn50(rowSet.getFloat("pnn50"));
			oecg.setSdnn(rowSet.getFloat("sdnn"));
			oecg.setHrvi(rowSet.getFloat("hrvi"));
			list.add(oecg);
		}
		return list;
	}

	public List<Obsr> findAnomalyBsrbyMemberIdAndTime(int memberId,
			String startTime, String endTime) throws Exception {
		String sql = "select b.Docentry,b.EventId,b.Memberid,b.UploadTime,b.TestTime,b.BsValue,b.timePeriod,b.AnalysisResult"
				+ " from omds a left join obsr b on a.eventId=b.eventId where a.MemberId=?  and   b.AnalysisResult>? and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc";
		List<Obsr> list = new ArrayList<Obsr>();
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId, 0,
				startTime, endTime });
		while (rowSet.next()) {
			Obsr osbr = new Obsr();
			osbr.setAnalysisResult(rowSet.getInt("AnalysisResult"));
			osbr.setBsValue(rowSet.getFloat("BsValue"));
			osbr.setEventId(rowSet.getLong("EventId"));
			osbr.setId(rowSet.getLong("Docentry"));
			osbr.setMemberId(memberId);
			osbr.setTestTime(TimeUtil.paserDatetime2(rowSet
					.getString("TestTime")));
			osbr.setTimePeriod(rowSet.getInt("timePeriod"));
			osbr.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			list.add(osbr);
		}
		return list;
	}

	public List<Oppg> findAnomalyPpgbyMemberIdAndTime(int memberId,
			String startTime, String endTime) throws Exception {
		String sql = "select b.BluetoothMacAddr, b.DeviceCode, b.fs, b.Docentry,b.EventId,b.TimeLength,b.UploadTime,b.MeasureTime,b.PulsebeatCount,b.SlowPulse,b.SlowTime,b.QuickPulse,b.QuickTime,b.PulseRate,b.Spo,b.SPO1,b.CO,b.SI,b.SV,b.CI,b.SPI,b.K,b.K1,b.V,b.TPR,b.PWTT,b.Pm,b.AC,"
				+ "  b.ImageNum,b.AbnormalData,b.Ppgrr,b.RawPPG,b.PPGResult,b.StatusTag"
				+ " from omds a left join oppg b on a.eventId=b.eventid where a.MemberId=?  and b.PPGResult=? and b.MeasureTime between ? and ? and b.delTag='0' order by b.MeasureTime desc  ";
		List<Oppg> list = new ArrayList<Oppg>();
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId, 1,
				startTime, endTime });
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
			oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("MeasureTime")));
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
			oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oppg.setV(rowSet.getFloat("V"));
			oppg.setMemberId(memberId);
			oppg.setDeviceCode(rowSet.getString("DeviceCode"));
			oppg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));

			list.add(oppg);
		}
		return list;
	}

	public List<Osbp> findAnomalySbpbyMemberIdAndTime(int memberId,
			String startTime, String endTime) throws Exception {
		String sql = "select b.Docentry,b.EventId,b.UploadTime,b.TestTime,b.Abnormal,b.timePeriod,b.SBP,b.DBP,b.PulseRate "
				+ "from omds a left join osbp b on a.eventId=b.eventid where a.MemberId=? and  b.Abnormal>? and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc ";
		List<Osbp> list = new ArrayList<Osbp>();
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { memberId, 0,
				startTime, endTime });
		while (rowSet.next()) {
			Osbp osbp = new Osbp();
			osbp.setAbnormal(rowSet.getString("Abnormal"));
			osbp.setDbp(rowSet.getInt("DBP"));
			osbp.setEventId(rowSet.getLong("EventId"));
			osbp.setId(rowSet.getLong("Docentry"));
			osbp.setPulseRate(rowSet.getInt("PulseRate"));
			osbp.setSbp(rowSet.getInt("SBP"));
			osbp.setTestTime(TimeUtil.paserDatetime2(rowSet
					.getString("TestTime")));
			osbp.setTimePeriod(rowSet.getInt("timePeriod"));
			osbp.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			osbp.setMemberId(memberId);
			list.add(osbp);
		}
		return list;
	}

	/**
	 * 添加会员活力积分
	 */
	public void addMemActivityScore(Mem8 mem8) throws Exception {
		String sql = "INSERT INTO mem8(memberId ,score ,uploadTime ) VALUES (?,?,?)";
		jdbcService.doExecuteSQL(
				sql,
				new Object[] { mem8.getMemberId(), mem8.getScore(),
						mem8.getUploadTime() });
	}

	public boolean findOecgByRawImg(String rawImage) throws Exception {
		String sql = "select  1 from oecg where (RawECG=? or RawECGImg=?) and  DeviceCode is not null";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { rawImage,
				rawImage });
		if (rowSet.next()) {
			return false;
		}
		return true;
	}

	public Oppg getPpgByEventId(long eventId) throws Exception {
		String sql = "select b.Docentry, b.BluetoothMacAddr, b.DeviceCode, b.fs, b.memberId,b.TimeLength,b.UploadTime,b.MeasureTime,b.PulsebeatCount,b.SlowPulse,b.SlowTime,b.QuickPulse,b.QuickTime,b.PulseRate,b.Spo,b.SPO1,b.CO,b.SI,b.SV,b.CI,b.SPI,b.K,b.K1,b.V,b.TPR,b.PWTT,b.Pm,b.AC,"
				+ "  b.ImageNum,b.AbnormalData,b.Ppgrr,b.RawPPG,b.PPGResult,b.StatusTag,b.kLevel,b.svLevel,b.coLevel,b.siLevel,b.vLevel,b.tprLevel,b.spoLevel,b.ciLevel,b.spiLevel,b.pwttLevel,b.acLevel,b.prLevel "
				+ " from oppg b  where b.EventId=? ";

		SqlRowSet rowSet = jdbcService.query(sql, new Object[] {eventId });
		if (rowSet.next()) {
			Oppg oppg = new Oppg();
			oppg.setFs(rowSet.getInt("fs"));
			oppg.setAbnormalData(rowSet.getInt("AbnormalData"));
			oppg.setAc(rowSet.getFloat("AC"));
			oppg.setCi(rowSet.getFloat("CI"));
			oppg.setCo(rowSet.getFloat("CO"));
			oppg.setEventId(eventId);
			oppg.setId(rowSet.getLong("Docentry"));
			oppg.setImageNum(rowSet.getInt("ImageNum"));
			oppg.setK(rowSet.getFloat("K"));
			oppg.setK1(rowSet.getFloat("K1"));
			oppg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("MeasureTime")));
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
			oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oppg.setV(rowSet.getFloat("V"));
			oppg.setMemberId(rowSet.getInt("memberId"));
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
			oppg.setDeviceCode(rowSet.getString("DeviceCode"));
			oppg.setPrLevel(rowSet.getInt("prLevel"));
			oppg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			return oppg;
		}
		return null;
	}

	public Oecg getEcgByEventId(long eventId) throws Exception {
		String sql = "select b.Docentry, b.sdnn,b.pnn50,b.hrvi, b.BluetoothMacAddr, b.DeviceCode, a.eventType ,b.fs, b.memberId, b.measTime,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
				+ " from  oecg b left join omds a on b.EventId=a.EventId  where b.EventId=?";
		SqlRowSet rowSet = jdbcService.query(sql, new Object[] { eventId });
		if (rowSet.next()) {
			Oecg oecg = new Oecg();
			oecg.setFs(rowSet.getInt("fs"));
			oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
			oecg.setEcgResult(rowSet.getInt("ECGResult"));
			oecg.setEventId(eventId);
			oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
			oecg.setFastestTime(rowSet.getInt("FastestTime"));
			oecg.setFrepPsd(rowSet.getString("FreqPSD"));
			oecg.setHeartNum(rowSet.getInt("HeartNum"));
			oecg.setId(rowSet.getLong("Docentry"));
			oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet
					.getString("measTime")));
			oecg.setRawEcg(rowSet.getString("RawECG"));
			oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
			oecg.setRrInterval(rowSet.getString("RRInterval"));
			oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
			oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
			oecg.setStatusTag(rowSet.getInt("StatusTag"));
			oecg.setTimeLength(rowSet.getInt("TimeLength"));
			oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet
					.getString("UploadTime")));
			oecg.setMemberId(rowSet.getInt("memberId"));
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
			oecg.setDeviceCode(rowSet.getString("DeviceCode"));
			oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
			oecg.setPnn50(rowSet.getFloat("pnn50"));
			oecg.setSdnn(rowSet.getFloat("sdnn"));
			oecg.setHrvi(rowSet.getFloat("hrvi"));
			return oecg;

		}
		return null;
	}

	public int deleteMeasureRecord(Integer memberId,Integer eventId,Integer dataType) throws Exception {
		String sql = null;
		String sqlOecg = null;
		String sqlOppg = null;
		
		if(dataType == 1){//血糖数据
			sql = "update obsr r set r.DelTag = 1 where r.Memberid = ? and r.EventId = ? and r.DelTag <> 1 ";
		}else if(dataType == 2){//血压数据
			sql = "update osbp p set p.DelTag = 1 where p.Memberid = ? and p.EventId = ? and p.DelTag <> 1 ";
		}else if(dataType == 3){//心电数据
			sql = "update oecg e set e.DelTag = 1 where e.Memberid = ? and e.EventId = ? and e.DelTag <> 1 ";
		}else if (dataType == 4){//脉搏数据
			sql = "update oppg g set g.DelTag = 1 where g.Memberid = ? and g.EventId = ? and g.DelTag <> 1 ";
		}
		//如果是三合一数据
		else if(dataType == 5)
		{
		    int oecg = 0;
		    sqlOecg = "update oecg e set e.DelTag = 1 where e.Memberid = ? and e.EventId = ? and e.DelTag <> 1 ";
		    oecg = jdbcService.doExecuteSQL(sqlOecg, new Object[] {memberId, eventId });
		    int oppg = 0;
		    sqlOppg = "update oppg g set g.DelTag = 1 where g.Memberid = ? and g.EventId = ? and g.DelTag <> 1 ";
		    oppg = jdbcService.doExecuteSQL(sqlOppg, new Object[] {memberId, eventId });
		    int count = oecg + oppg;
		    return count;
		}
		
		return jdbcService.doExecuteSQL(sql, new Object[] {memberId, eventId });
	}

	public Map<?, ?> queryMeasueChartData(ChartParam param) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(param.getDataType() == 1){
			map = queryOsbpChartData(param);			
		}else if(param.getDataType() == 2){
			map = queryObsrChartData(param);
		}else if(param.getDataType() == 3){
			map = queryThreeJionOneChartData(param);
		}else if(param.getDataType() == 4){
			map = queryMiniEcgChartData(param);
		}
		return map;		
	}

	
	/**
	 * 查询血压chart图数据
	 * @param param
	 * @return
	 */
	private Map<String,Object> queryOsbpChartData(ChartParam param){
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		StringBuffer commonsql = new StringBuffer("FROM omrr o ");
		commonsql.append("JOIN osbp os ON os.Memberid=o.Memberid AND os.TestTime>=o.MeasTime and os.TestTime<=o.MeasTermTime ");
		commonsql.append("WHERE o.Memberid = ? AND o.id = ? ");
		//---------------------------------------------
		sql.append("select os.Abnormal, count(os.Abnormal) as num ");
		sql.append(commonsql);
		sql.append("group by os.Abnormal");
		
		List<ChartOsbp> pieBarList = new ArrayList<ChartOsbp>();
		SqlRowSet rowSet = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		
		while (rowSet.next()) {
			ChartOsbp osbp = new ChartOsbp();
			osbp.setAbnormal(rowSet.getString("Abnormal"));
			osbp.setNum(rowSet.getInt("num"));
			pieBarList.add(osbp);
		}
		map.put("pie_bar", pieBarList);
		
		//-------------------------------------------
		sql = new StringBuffer("SELECT ");
		sql.append("(CASE WHEN ((DATE_FORMAT(os.TestTime,'%H')+0) > 0 and (DATE_FORMAT(os.TestTime,'%H')+0) <= 8) THEN '0' ");
		sql.append("WHEN ((DATE_FORMAT(os.TestTime,'%H')+0) > 8 and (DATE_FORMAT(os.TestTime,'%H')+0) <=12) THEN '1' ");
		sql.append("WHEN ((DATE_FORMAT(os.TestTime,'%H')+0) >12 and (DATE_FORMAT(os.TestTime,'%H')+0) <=18) THEN '2' ");
		sql.append("ELSE '3' END) TimeQ, ");
		sql.append("(CASE WHEN (os.SBP < 90 and os.DBP < 60) THEN '0' ");
		sql.append("WHEN (os.SBP >= 90 and os.SBP < 150 and os.DBP >= 60 and os.DBP < 90) THEN '1' ");
		sql.append("ELSE '2' END) XY, count(*) num ");
		sql.append(commonsql);
		sql.append("group by TimeQ, XY ");
		
		SqlRowSet rowSet2 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		
		List<ChartOsbp> foursubchartList = new ArrayList<ChartOsbp>();
		while (rowSet2.next()) {
			ChartOsbp osbp = new ChartOsbp();
			osbp.setTimeQ(rowSet2.getString("TimeQ"));
			osbp.setXY(rowSet2.getString("XY"));
			osbp.setNum(rowSet2.getInt("num"));
			foursubchartList.add(osbp);
		}
		map.put("foursubchart", foursubchartList);
		
		//---------------------------------------------
		sql = new StringBuffer("select DATE_FORMAT(os.TestTime,'%H:%i') TestTimes ,os.SBP, os.DBP ");
		sql.append(commonsql);
		List<ChartOsbp> scachart = new ArrayList<ChartOsbp>();
		SqlRowSet rowSet3 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		
		while (rowSet3.next()) {
			ChartOsbp osbp = new ChartOsbp();
			osbp.setTestTimes(rowSet3.getString("TestTimes"));
			osbp.setSbp(rowSet3.getInt("SBP"));
			osbp.setDbp(rowSet3.getInt("DBP"));
			scachart.add(osbp);
		}
		map.put("scachart", scachart);
		
		//---------------------------------------------
		sql = new StringBuffer("select DATE_FORMAT(os.TestTime,'%m-%d') TestTimes ,os.SBP, os.DBP ");
		sql.append(commonsql);
		sql.append(" ORDER BY os.TestTime");
		
		List<ChartOsbp> list4 = new ArrayList<ChartOsbp>();
		SqlRowSet rowSet4 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		
		while (rowSet4.next()) {
			ChartOsbp osbp = new ChartOsbp();
			osbp.setTestTimes(rowSet4.getString("TestTimes"));
			osbp.setSbp(rowSet4.getInt("SBP"));
			osbp.setDbp(rowSet4.getInt("DBP"));
			list4.add(osbp);
		}
		map.put("trend1", list4);
		
		//---------------------------------------------
		sql = new StringBuffer("select DATE_FORMAT(os.TestTime,'%m-%d') TestTimes ,os.SBP, os.DBP ");
		sql.append(commonsql);
		sql.append("and timePeriod = 1 ORDER BY os.TestTime");
		List<ChartOsbp> list5 = new ArrayList<ChartOsbp>();
		SqlRowSet rowSet5 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		
		while (rowSet5.next()) {
			ChartOsbp osbp = new ChartOsbp();
			osbp.setTestTimes(rowSet5.getString("TestTimes"));
			osbp.setSbp(rowSet5.getInt("SBP"));
			osbp.setDbp(rowSet5.getInt("DBP"));
			list5.add(osbp);
		}
		
		map.put("trend2", list5);
		//---------------------------------------------
		sql = new StringBuffer("select DATE_FORMAT(os.TestTime,'%m-%d') TestTimes ,os.SBP, os.DBP ");
		sql.append(commonsql);
		sql.append("and timePeriod = 2 ORDER BY os.TestTime");
		
		List<ChartOsbp> list6 = new ArrayList<ChartOsbp>();
		SqlRowSet rowSet6 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		
		while (rowSet6.next()) {
			ChartOsbp osbp = new ChartOsbp();
			osbp.setTestTimes(rowSet6.getString("TestTimes"));
			osbp.setSbp(rowSet6.getInt("SBP"));
			osbp.setDbp(rowSet6.getInt("DBP"));
			list6.add(osbp);
		}
		map.put("trend3", list6);

		return map;
	}
	
	
	
	/**
	 * 查询血糖chart图数据
	 * @param param
	 * @return
	 */
	private Map<String,Object> queryObsrChartData(ChartParam param){//"Docentry"
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer commonsql = new StringBuffer();
		commonsql.append("from omrr o JOIN obsr ob on ob.Memberid=o.Memberid and ob.TestTime>=o.MeasTime and ob.TestTime<=o.MeasTermTime ");
		commonsql.append("where ob.DelTag='0' AND o.Memberid = ? AND o.id = ? ");
		StringBuffer sql = new StringBuffer();
		
		//--图一--------------------------------------
		sql.append("select DATE_FORMAT(ob.TestTime,'%H:%i') TestTimes, ob.timePeriod, ob.BsValue ");
		sql.append(commonsql);
		SqlRowSet rowSet1 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		List<ChartObsr> list1 = new ArrayList<ChartObsr>();
		while (rowSet1.next()) {
			ChartObsr obsr = new ChartObsr();
			obsr.setTestTimes(rowSet1.getString("TestTimes"));
			obsr.setTimePeriod(rowSet1.getString("timePeriod"));
			obsr.setBsValue(rowSet1.getDouble("BsValue"));
			list1.add(obsr);
		}
		map.put("chart1", list1);

		//--图二--------------------------------------
		sql = new StringBuffer("SELECT ob.timePeriod, ob.BsValue ");
		sql.append(commonsql).append("ORDER BY ob.timePeriod ");
		List<ChartObsr> list2 = new ArrayList<ChartObsr>();
		SqlRowSet rowSet2 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		while (rowSet2.next()) {
			ChartObsr obsr = new ChartObsr();
			obsr.setTimePeriod(rowSet2.getString("timePeriod"));
			obsr.setBsValue(rowSet2.getDouble("BsValue"));
			list2.add(obsr);
		}
		map.put("chart2", list2);
		
		//--图三--------------------------------------
		sql = new StringBuffer("select DATE_FORMAT(ob.TestTime, '%Y-%m-%d') days, MAX(ob.BsValue) max, MIN(ob.BsValue) min ");
		sql.append(commonsql).append("GROUP BY days ");
		List<ChartObsr> list3 = new ArrayList<ChartObsr>();
		SqlRowSet rowSet3 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		while (rowSet3.next()) {
			ChartObsr obsr = new ChartObsr();
			obsr.setDays(rowSet3.getString("days"));
			obsr.setMax(rowSet3.getDouble("max"));
			obsr.setMin(rowSet3.getDouble("min"));
			list3.add(obsr);
		}
		map.put("chart3", list3);
		
		//--图四--------------------------------------
		sql = new StringBuffer("SELECT * FROM (");
		sql.append("SELECT timePeriod, CASE WHEN (AnalysisResult='0') THEN 'N' WHEN (AnalysisResult='2') THEN 'H' WHEN (AnalysisResult='3') THEN 'H' WHEN (AnalysisResult='5') THEN 'H' WHEN (AnalysisResult='1') THEN 'L' WHEN (AnalysisResult='4') THEN 'L' END NE, count(*) num ");
		sql.append(commonsql).append("GROUP BY ob.timePeriod, NE ORDER BY ob.timePeriod ");
		sql.append(") bsr ");
		List<ChartObsr> list4 = new ArrayList<ChartObsr>();
		SqlRowSet rowSet4 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		while (rowSet4.next()) {
			ChartObsr obsr = new ChartObsr();
			obsr.setTimePeriod(rowSet4.getString("timePeriod"));
			obsr.setNE(rowSet4.getString("NE"));
			obsr.setNum(rowSet4.getInt("num"));
			list4.add(obsr);
		}
		map.put("chart4", list4);
		
		//--图五--------------------------------------
		sql = new StringBuffer("SELECT ob.* ");
		sql.append(commonsql);
		sql.append(" ORDER BY ob.TestTime");
		
		List<ChartObsr> list5 = new ArrayList<ChartObsr>();
		SqlRowSet rowSet5 = jdbcService.query(sql.toString(), new Object[] {param.getMemberId(),param.getReportId()});
		while (rowSet5.next()) {
			ChartObsr obsr = new ChartObsr();
			obsr.setTestTime(rowSet5.getTimestamp("TestTime"));
			obsr.setBsValue(rowSet5.getDouble("BsValue"));
			obsr.setTimePeriod(rowSet5.getString("timePeriod"));
			list5.add(obsr);
		}
		map.put("trend", list5);
		
		return map;
	}
	
	/**
	 * 查询三合一的心电和脉搏 的chart图数据
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> queryThreeJionOneChartData(ChartParam param){
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		StringBuffer commonsql1 = new StringBuffer();
		StringBuffer commonsql2 = new StringBuffer();
		
		commonsql1.append("SELECT CASE ");
		commonsql1.append("WHEN (e2.AbnName='Polycardia') THEN '心动过速' WHEN (e2.AbnName='Bradycardia') THEN '心动过缓' ");
		commonsql1.append("WHEN (e2.AbnName='Arrest') THEN '停搏' WHEN (e2.AbnName='Missed') THEN '漏搏' ");
		commonsql1.append("WHEN (e2.AbnName='Wide') THEN '宽搏' WHEN (e2.AbnName='PVB') THEN '室性早搏' ");
		commonsql1.append("WHEN (e2.AbnName='PAB') THEN '房性早搏' WHEN (e2.AbnName='Insert_PVB') THEN '插入性室早搏' ");
		commonsql1.append("WHEN (e2.AbnName='VT') THEN '阵发性心动过速' WHEN (e2.AbnName='Bigeminy') THEN '二联律' ");
		commonsql1.append("WHEN (e2.AbnName='Trigeminy') THEN '三联律' WHEN (e2.AbnName='Arrhythmia') THEN '心律不齐' ");
		commonsql1.append("ELSE '未知' END aName, e2.abnName, e2.abnNum ");
		//commonsql2.append("FROM omrr o JOIN oppg op ON o.Memberid=op.Memberid AND op.Docentry>=o.MeasCorrNo AND op.Docentry<=o.MeasCorrTermNo ");
		commonsql2.append("FROM omrr o JOIN oppg op ON o.Memberid=op.Memberid AND op.MeasureTime>=o.MeasTime AND op.MeasureTime<=o.MeasTermTime ");
		commonsql2.append("JOIN oecg oe ON op.EventId=oe.EventId ");
		commonsql2.append("JOIN ecg2 e2 ON e2.Docentry=oe.Docentry ");
		commonsql2.append("JOIN omds om ON om.eventid=oe.EventId ");
		commonsql2.append("WHERE om.EventType = 3 AND o.Memberid="+param.getMemberId()+" AND o.id="+param.getReportId());
		
		//-图一--------------------------------------------------
		sql.append(commonsql1);
		sql.append(", DATE_FORMAT(oe.MeasTime,'%H:%i') measTimes ");
		sql.append(commonsql2);
		List<ChartEcg2> list1 = (ArrayList<ChartEcg2>) queryData(sql.toString(), ChartEcg2.class.getName(), "list1");

		//-图二--------------------------------------------------
		sql = new StringBuffer(commonsql1);
		sql.append(", sum(e2.abnNum) num ");	//总次数
		sql.append(commonsql2);
		sql.append(" GROUP BY aName ");
		List<ChartEcg2> list2 = (ArrayList<ChartEcg2>) queryData(sql.toString(), ChartEcg2.class.getName(), "list2");
		
		//-图三--------------------------------------------------
		StringBuffer commonsql3 = new StringBuffer();
		commonsql3.append("FROM omrr o JOIN oppg op ON op.Memberid=o.Memberid and op.MeasureTime>=o.MeasTime AND op.MeasureTime<=o.MeasTermTime ");
		commonsql3.append("JOIN omds om ON om.eventid=op.EventId ");
		commonsql3.append("WHERE om.EventType = 3 AND o.Memberid="+param.getMemberId()+" AND o.id=" + param.getReportId()).append(" ");
		
		sql = new StringBuffer("SELECT op.MeasureTime, ");
		//sql.append("op.PulseRate, op.CO, op.SV, op.Spo ");
		sql.append("op.PulseRate, op.K, op.CO, op.SV, op.AC, op.SI, op.V, op.TPR, op.Spo, op.CI, op.SPI, op.pm ");
		sql.append(commonsql3);
		sql.append("order by op.MeasureTime");
		List<ChartOppg> list3 = (ArrayList<ChartOppg>) queryData(sql.toString(), ChartOppg.class.getName(), "list3");
		
		//-图四-------------------------------------------------	
		sql = new StringBuffer("SELECT SUM(ppgbar.PR) PR, SUM(ppgbar.K) K, SUM(ppgbar.SV) SV, ");
		sql.append(" SUM(ppgbar.CO) CO, SUM(ppgbar.SI) SI, SUM(ppgbar.TPR) TPR, ");
		sql.append("SUM(ppgbar.SPO) SPO, SUM(ppgbar.AC) AC, SUM(ppgbar.V) V, SUM(ppgbar.CI) CI, ");
		sql.append("SUM(ppgbar.SPI) SPI, SUM(ppgbar.pm) PM FROM (");
		sql.append("SELECT CASE WHEN (op.PRLevel!=0) THEN 1 ELSE 0 END PR, ");
		sql.append("CASE WHEN (op.KLevel!=0) THEN 1 ELSE 0 END K, ");
		sql.append("CASE WHEN (op.SVLevel!=0) THEN 1 ELSE 0 END SV, ");
		sql.append("CASE WHEN (op.COLevel!=0) THEN 1 ELSE 0 END CO, ");
		sql.append("CASE WHEN (op.SILevel!=0) THEN 1 ELSE 0 END SI, ");
		sql.append("CASE WHEN (op.TPRLevel!=0) THEN 1 ELSE 0 END TPR, ");
		sql.append("CASE WHEN (op.SPOLevel!=0) THEN 1 ELSE 0 END SPO, ");
		sql.append("CASE WHEN (op.ACLevel!=0) THEN 1 ELSE 0 END AC, ");
		sql.append("CASE WHEN (op.VLevel!=0) THEN 1 ELSE 0 END V, ");
		sql.append("CASE WHEN (op.CILevel!=0) THEN 1 ELSE 0 END CI, ");
		sql.append("CASE WHEN (op.SPILevel!=0) THEN 1 ELSE 0 END SPI, ");
		sql.append("CASE WHEN (op.PM<70 or op.PM>105) THEN 1 ELSE 0 END PM ");
		
		sql.append(commonsql3);
		sql.append(" HAVING (PR != 0 or K !=0 or SV != 0 or CO != 0 or SI != 0 or TPR != 0 ");
		sql.append(" or SPO != 0 or AC != 0 or V != 0 or CI != 0 or SPI != 0 or PM != 0)");
		sql.append(") ppgbar ");
		List<ChartOppg> list4 = (ArrayList<ChartOppg>) queryData(sql.toString(), ChartOppg.class.getName(), "list4");
		
		map.put("chart1", list1);
		map.put("chart2", list2);
		map.put("chart3", list3);
		map.put("chart4", list4);
		
		return map;
	}
	
	
	/**
	 * 根据SQL查询三合一的心电或脉搏数据
	 * 
	 * @param sql
	 * @param className
	 * @param flag
	 * @return
	 */
	private List<?> queryData(String sql, String className, String flag) {
		SqlRowSet rowSet = jdbcService.query(sql.toString(), new Object[] {});
		if (rowSet !=null) {
			if (ChartEcg2.class.getName().equals(className)&& "list1".equals(flag)) {
				List<ChartEcg2> list = new ArrayList<ChartEcg2>();
				while (rowSet.next()) {
					ChartEcg2 e2 = new ChartEcg2();
					e2.setaName(rowSet.getString("aName"));
					e2.setAbnName(rowSet.getString("abnName"));
					e2.setAbnNum(rowSet.getInt("abnNum"));
					e2.setMeasTimes(rowSet.getString("measTimes"));
					list.add(e2);
				}
				return list;
			} else if (ChartEcg2.class.getName().equals(className)&& "list2".equals(flag)) {
				List<ChartEcg2> list = new ArrayList<ChartEcg2>();
				while (rowSet.next()) {
					ChartEcg2 e2 = new ChartEcg2();
					e2.setaName(rowSet.getString("aName"));
					e2.setAbnName(rowSet.getString("abnName"));
					e2.setAbnNum(rowSet.getInt("abnNum"));
					e2.setNum(rowSet.getInt("num"));
					list.add(e2);
				}
				return list;
			} else if (ChartOppg.class.getName().equals(className)&& "list3".equals(flag)) {
				List<ChartOppg> list = new ArrayList<ChartOppg>();
				while (rowSet.next()) {
					ChartOppg op = new ChartOppg();
					op.setMeasureTime(rowSet.getTimestamp("MeasureTime"));
					op.setPulseRate(rowSet.getShort("PulseRate"));
					op.setK(rowSet.getDouble("k"));
					op.setSv(rowSet.getDouble("SV"));
					op.setCo(rowSet.getDouble("CO"));
					op.setAc(rowSet.getDouble("AC"));
					op.setsI(rowSet.getDouble("SI"));
					op.setV(rowSet.getDouble("V"));
					op.setTpr(rowSet.getDouble("TPR"));
					op.setSpo(rowSet.getShort("Spo"));
					op.setCi(rowSet.getDouble("CI"));
					op.setSpi(rowSet.getDouble("SPI"));
					op.setPm(rowSet.getDouble("PM"));
					list.add(op);
				}
				return list;
			} else if (ChartOppg.class.getName().equals(className)
					&& "list4".equals(flag)) {
				List<ChartOppg> list = new ArrayList<ChartOppg>();
				while (rowSet.next()) {
					ChartOppg op = new ChartOppg();
					op.setPrNum(rowSet.getInt("PR"));
					op.setkNum(rowSet.getInt("K"));
					op.setSvNum(rowSet.getInt("SV"));
					op.setCoNum(rowSet.getInt("CO"));
					op.setAcNum(rowSet.getInt("AC"));
					op.setSiNum(rowSet.getInt("SI"));
					op.setvNum(rowSet.getInt("V"));
					op.setTprnum(rowSet.getInt("TPR"));
					op.setSpoNum(rowSet.getInt("SPO"));
					op.setCiNum(rowSet.getInt("CI"));
					op.setSpiNum(rowSet.getInt("SPI"));
					op.setPmNum(rowSet.getInt("PM"));
					list.add(op);
				}
				return list;
			}
		}
		return null;
	}

	
	/**
	 * 查询迷你 动态心电chart图数据
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> queryMiniEcgChartData(ChartParam param){
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		StringBuffer commonsql1 = new StringBuffer();
		StringBuffer commonsql2 = new StringBuffer();
		
		commonsql1.append("SELECT CASE ");
		commonsql1.append("WHEN (e2.AbnName='Polycardia') THEN '心动过速' WHEN (e2.AbnName='Bradycardia') THEN '心动过缓' ");
		commonsql1.append("WHEN (e2.AbnName='Arrest') THEN '停搏' WHEN (e2.AbnName='Missed') THEN '漏搏' ");
		commonsql1.append("WHEN (e2.AbnName='Wide') THEN '宽搏' WHEN (e2.AbnName='PVB') THEN '室性早搏' ");
		commonsql1.append("WHEN (e2.AbnName='PAB') THEN '房性早搏' WHEN (e2.AbnName='Insert_PVB') THEN '插入性室早搏' ");
		commonsql1.append("WHEN (e2.AbnName='VT') THEN '阵发性心动过速' WHEN (e2.AbnName='Bigeminy') THEN '二联律' ");
		commonsql1.append("WHEN (e2.AbnName='Trigeminy') THEN '三联律' WHEN (e2.AbnName='Arrhythmia') THEN '心律不齐' ");
		commonsql1.append("ELSE '未知' END aName, e2.abnName, e2.abnNum ");
		commonsql2.append("FROM omrr o JOIN oecg oe ON o.Memberid=oe.Memberid AND oe.MeasTime>=o.MeasTime AND oe.MeasTime<=o.MeasTermTime ");
		commonsql2.append("JOIN ecg2 e2 ON e2.Docentry=oe.Docentry ");
		commonsql2.append("JOIN omds om ON om.eventid=oe.EventId ");
		commonsql2.append("WHERE om.EventType = 4 AND o.Memberid="+param.getMemberId()+" AND o.id="+param.getReportId());
		
		//-图一--------------------------------------------------
		sql.append(commonsql1);
		sql.append(", DATE_FORMAT(oe.MeasTime,'%H:%i') measTimes ");
		sql.append(commonsql2);
		List<ChartEcg2> list1 = (ArrayList<ChartEcg2>) queryData(sql.toString(), ChartEcg2.class.getName(), "list1");
		
		//-图二--------------------------------------------------
		sql = new StringBuffer(commonsql1);
		sql.append(", sum(e2.abnNum) num ");	//总次数
		sql.append(commonsql2);
		sql.append(" GROUP BY aName ");
		List<ChartEcg2> list2 = (ArrayList<ChartEcg2>) queryData(sql.toString(), ChartEcg2.class.getName(), "list2");
		
		map.put("chart1", list1);
		map.put("chart2", list2);
		return map;
	}

    /** 
     * @Title: findOsbpByOmdsValueNew 
     * @Description: 根据参数获取血压list 
     * @liuxaioqin
     * @createDate 2016-01-28
     * @param param
     * @return
     * @throws Exception    
     * @retrun List<ReturnMeasureData>
     */
    public List<ReturnMeasureData> findOsbpByOmdsValueNew(OmdsParam param) throws Exception{
    	List<ReturnMeasureData> list = new ArrayList<ReturnMeasureData>();
    	String eventIds = param.getEventIds();
    	if(!StringUtils.isEmpty(eventIds)){
    		String newSql = " select b.Docentry,b.EventId,b.MemberId,b.UploadTime,b.TestTime,b.Abnormal,b.timePeriod,b.SBP,b.DBP,b.PulseRate from omds a left join osbp b "
    				+ "  on a.eventId = b.eventid where a.EventId in ("+eventIds+") and a.EventType = ? and a.MemberId = ? and b.delTag = '0' order by b.TestTime desc ";
    		SqlRowSet newSet = jdbcService.query(newSql,new Object[] {param.getEventType(),param.getMemberId()});
    		while (newSet.next()) {
    			ReturnMeasureData measureData = new ReturnMeasureData();
    			Osbp osbp = new Osbp();
    			osbp.setAbnormal(newSet.getString("Abnormal"));
    			osbp.setDbp(newSet.getInt("DBP"));
    			osbp.setEventId(newSet.getLong("EventId"));
    			osbp.setId(newSet.getLong("Docentry"));
    			osbp.setPulseRate(newSet.getInt("PulseRate"));
    			osbp.setSbp(newSet.getInt("SBP"));
    			osbp.setTestTime(TimeUtil.paserDatetime2(newSet.getString("TestTime")));
    			osbp.setTimePeriod(newSet.getInt("timePeriod"));
    			osbp.setUploadTime(TimeUtil.paserDatetime2(newSet.getString("UploadTime")));
    			osbp.setMemberId(newSet.getInt("MemberId"));
    			measureData.setData(osbp);
    			measureData.setDataType(param.getEventType());
    			measureData.setIsAbnormal(osbp.getAbnormal().equals("0")?0:1);
    			measureData.setMeasureTime(osbp.getTestTime());
    			list.add(measureData);
    		}
    	}else{
    		String sql = "select b.Docentry,b.EventId,b.UploadTime,b.TestTime,b.Abnormal,b.timePeriod,b.SBP,b.DBP,b.PulseRate "
    				+ "from omds a left join osbp b on a.eventId=b.eventid where a.MemberId=? and a.EventType=? and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc limit ? ";
    		SqlRowSet rowSet = jdbcService.query(sql,new Object[] { param.getMemberId(), param.getDataType(), param.getTimeStart(),
    				param.getTimeEnd(), param.getCount() });
    		while (rowSet.next()) {
    			ReturnMeasureData measureData = new ReturnMeasureData();
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
    			osbp.setMemberId(param.getMemberId());
    			measureData.setData(osbp);
    			measureData.setDataType(param.getDataType());
    			measureData.setIsAbnormal(osbp.getAbnormal().equals("0")?0:1);
    			measureData.setMeasureTime(osbp.getTestTime());
    			list.add(measureData);
    		}
    	}
        return list;
    }
    
    /** 
     * @Title: findObsrByOmdsValueNew 
     * @Description: 根据参数获取血糖list 
     * @liuxaioqin
     * @createDate 2016-01-28
     * @param param
     * @return
     * @throws Exception    
     * @retrun List<ReturnMeasureData>
     */
    public List<ReturnMeasureData> findObsrByOmdsValueNew(OmdsParam param) throws Exception{
    	List<ReturnMeasureData> list = new ArrayList<ReturnMeasureData>();
    	String eventIds = param.getEventIds();
    	if(!StringUtils.isEmpty(eventIds)){
    		String newSql = "select b.Docentry,b.EventId,b.Memberid,b.UploadTime,b.TestTime,b.BsValue,b.timePeriod,b.AnalysisResult from omds a left join obsr b "
    				      + " on a.eventId = b.eventid where a.EventId in ("+eventIds+") and a.EventType= ? and a.MemberId = ? and b.delTag = '0' order by b.TestTime desc ";
    		SqlRowSet newSet = jdbcService.query(newSql,new Object[] {param.getEventType(),param.getMemberId()});
    		while (newSet.next()) {
    			ReturnMeasureData measureData = new ReturnMeasureData();
    			Obsr osbr = new Obsr();
    			osbr.setAnalysisResult(newSet.getInt("AnalysisResult"));
    			osbr.setBsValue(newSet.getFloat("BsValue"));
    			osbr.setEventId(newSet.getLong("EventId"));
    			osbr.setId(newSet.getLong("Docentry"));
    			osbr.setMemberId(newSet.getInt("MemberId"));
    			osbr.setTestTime(TimeUtil.paserDatetime2(newSet.getString("TestTime")));
    			osbr.setTimePeriod(newSet.getInt("timePeriod"));
    			osbr.setUploadTime(TimeUtil.paserDatetime2(newSet.getString("UploadTime")));
    			measureData.setData(osbr);
    			measureData.setDataType(param.getEventType());
    			measureData.setIsAbnormal(osbr.getAnalysisResult() == 0?0:1);
    			measureData.setMeasureTime(osbr.getTestTime());
    			list.add(measureData);
    		}
    	}else{
    		String sql = "select b.Docentry,b.EventId,b.Memberid,b.UploadTime,b.TestTime,b.BsValue,b.timePeriod,b.AnalysisResult"
    				+ " from omds a left join obsr b on a.eventId=b.eventId where a.MemberId=? and a.EventType=? and b.TestTime between ? and ? and b.delTag='0' order by b.TestTime desc limit ? ";
    		SqlRowSet rowSet = jdbcService.query(sql,new Object[] { param.getMemberId(), param.getDataType(),param.getTimeStart(),
    				param.getTimeEnd(), param.getCount() });
    		while (rowSet.next()) {
    			ReturnMeasureData  measureData = new ReturnMeasureData(); 
    			Obsr osbr = new Obsr();
    			osbr.setAnalysisResult(rowSet.getInt("AnalysisResult"));
    			osbr.setBsValue(rowSet.getFloat("BsValue"));
    			osbr.setEventId(rowSet.getLong("EventId"));
    			osbr.setId(rowSet.getLong("Docentry"));
    			osbr.setMemberId(param.getMemberId());
    			osbr.setTestTime(TimeUtil.paserDatetime2(rowSet.getString("TestTime")));
    			osbr.setTimePeriod(rowSet.getInt("timePeriod"));
    			osbr.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
    			measureData.setData(osbr);
    			measureData.setDataType(param.getDataType());
    			measureData.setIsAbnormal(osbr.getAnalysisResult() == 0?0:1);
    			measureData.setMeasureTime(osbr.getTestTime());
    			list.add(measureData);
    		}
    	}
        return list;
    }
    
    /** 
     * @Title: findThreeJoinOneByParamNew 
     * @Description: 根据参数获取三合一list 
     * @liuxaioqin
     * @createDate 2016-01-28
     * @param param
     * @return
     * @throws Exception    
     * @retrun List<ReturnMeasureData>
     */
    public List<ReturnMeasureData> findThreeJoinOneByParamNew(OmdsParam param)throws Exception{
    	List<ReturnMeasureData> list = new ArrayList<ReturnMeasureData>();
    	String eventIds = param.getEventIds();
    	if(!StringUtils.isEmpty(eventIds)){
    		String newSql = " select a.eventType,b.Memberid,b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry, "
    				      + " b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime, "
    				      + " b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level, "
    				      + " b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel,c.BluetoothMacAddr as "
    				      + " oppgBluetoothMacAddr, c.fs as oppgfs, c.Docentry as oppgDocentry,c.TimeLength as oppgTimeLength,c.UploadTime as "
    				      + " oppgUploadTime,c.MeasureTime,c.PulsebeatCount,c.SlowPulse,c.SlowTime,c.QuickPulse,c.QuickTime,c.PulseRate,c.Spo, "
    				      + " c.SPO1,c.CO,c.SI,c.SV,c.CI,c.SPI,c.K,c.K1,c.V,c.TPR,c.PWTT,c.Pm,c.AC, c.ImageNum,c.AbnormalData,c.Ppgrr,c.RawPPG, "
    				      + " c.PPGResult,c.StatusTag as oppgStatusTag,c.kLevel,c.svLevel,c.coLevel,c.siLevel,c.vLevel,c.tprLevel,c.spoLevel, "
    				      + " c.ciLevel,c.spiLevel,c.pwttLevel,c.acLevel,c.prLevel "
    				      + " from omds a left join oecg b on a.eventId = b.eventid  LEFT JOIN oppg c on a.eventid = c.EventId "
    				      + " where a.EventId in ("+eventIds+") and a.EventType= ? and a.MemberId = ? and b.delTag = '0' and c.DelTag = '0' order by b.measTime desc ";
    		SqlRowSet newSet = jdbcService.query(newSql,new Object[] {param.getEventType(),param.getMemberId()});
    		while (newSet.next()) {
    		    ReturnMeasureData measureData = new ReturnMeasureData();
    			//封装心电对象
 	            Oecg oecg = new Oecg();
 	            oecg.setId(newSet.getLong("Docentry"));
 	            oecg.setEcgResult(newSet.getInt("ECGResult"));
 	            List<Ecg2> ecg2s = new ArrayList<Ecg2>();
 	            if(oecg.getEcgResult() == 1){
 	                ecg2s = findEcg2ById(oecg.getId() + "");
 	            }
 	            oecg.setEcg2s(ecg2s);
 	            oecg.setFs(newSet.getInt("fs"));
 	            oecg.setAverageHeart(newSet.getInt("AverageHeart"));
 	            oecg.setEventId(newSet.getLong("EventId"));
 	            oecg.setFastestBeat(newSet.getInt("FastestBeat"));
 	            oecg.setFastestTime(newSet.getInt("FastestTime"));
 	            oecg.setFrepPsd(newSet.getString("FreqPSD"));
 	            oecg.setHeartNum(newSet.getInt("HeartNum"));
 	            oecg.setId(newSet.getLong("Docentry"));
 	            oecg.setMeasureTime(TimeUtil.paserDatetime2(newSet.getString("measTime")));
 	            oecg.setRawEcg(newSet.getString("RawECG"));
 	            oecg.setRawEcgImg(newSet.getString("RawECGImg"));
 	            oecg.setRrInterval(newSet.getString("RRInterval"));
 	            oecg.setSlowestBeat(newSet.getInt("SlowestBeat"));
 	            oecg.setSlowestTime(newSet.getInt("SlowestTime"));
 	            oecg.setStatusTag(newSet.getInt("StatusTag"));
 	            oecg.setTimeLength(newSet.getInt("TimeLength"));
 	            oecg.setUploadTime(TimeUtil.paserDatetime2(newSet.getString("UploadTime")));
 	            oecg.setMemberId(newSet.getInt("MemberId"));
 	            oecg.setSdnnLevel(newSet.getInt("sdnnLevel"));
 	            oecg.setPnn50Level(newSet.getInt("pnn50Level"));
 	            oecg.setHrviLevel(newSet.getInt("hrviLevel"));
 	            oecg.setRmssdLevel(newSet.getInt("rmssdLevel"));
 	            oecg.setTpLevel(newSet.getInt("tplevel"));
 	            oecg.setVlfLevel(newSet.getInt("vlfLevel"));
 	            oecg.setLfLevel(newSet.getInt("lfLevel"));
 	            oecg.setHfLevel(newSet.getInt("hfLevel"));
 	            oecg.setLhrLevel(newSet.getInt("lhrLevel"));
 	            oecg.setHrLevel(newSet.getInt("hrLevel"));
 	            oecg.setDeviceCode(newSet.getString("DeviceCode"));
 	            oecg.setBluetoothMacAddr(newSet.getString("BluetoothMacAddr"));
 	            oecg.setPnn50(newSet.getFloat("pnn50"));
 	            oecg.setSdnn(newSet.getFloat("sdnn"));
 	            oecg.setHrvi(newSet.getFloat("hrvi"));
 	            oecg.setEventType(newSet.getInt("eventType"));
 	           
 	            //封装脉搏对象
 	            Oppg oppg = new Oppg();
 	            oppg.setFs(newSet.getInt("oppgfs"));
 	            oppg.setAbnormalData(newSet.getInt("AbnormalData"));
 	            oppg.setAc(newSet.getFloat("AC"));
 	            oppg.setCi(newSet.getFloat("CI"));
 	            oppg.setCo(newSet.getFloat("CO"));
 	            oppg.setEventId(newSet.getLong("EventId"));
 	            oppg.setId(newSet.getLong("oppgDocentry"));
 	            oppg.setImageNum(newSet.getInt("ImageNum"));
 	            oppg.setK(newSet.getFloat("K"));
 	            oppg.setK1(newSet.getFloat("K1"));
 	            oppg.setMeasureTime(TimeUtil.paserDatetime2(newSet.getString("MeasureTime")));
 	            oppg.setPm(newSet.getFloat("Pm"));
 	            oppg.setPpgResult(newSet.getInt("PPGResult"));
 	            oppg.setPpgrr(newSet.getString("Ppgrr"));
 	            oppg.setPulsebeatCount(newSet.getInt("PulsebeatCount"));
 	            oppg.setPulseRate(newSet.getInt("PulseRate"));
 	            oppg.setPwtt(newSet.getFloat("PWTT"));
 	            oppg.setQuickPulse(newSet.getInt("QuickPulse"));
 	            oppg.setQuickTime(newSet.getInt("QuickTime"));
 	            oppg.setRawPpg(newSet.getString("RawPPG"));
 	            oppg.setSi(newSet.getFloat("SI"));
 	            oppg.setSlowPulse(newSet.getInt("SlowPulse"));
 	            oppg.setSlowTime(newSet.getInt("SlowTime"));
 	            oppg.setSpi(newSet.getFloat("SPI"));
 	            oppg.setSpo(newSet.getInt("Spo"));
 	            oppg.setSpo1(newSet.getInt("SPO1"));
 	            oppg.setStatusTag(newSet.getInt("oppgStatusTag"));
 	            oppg.setSv(newSet.getFloat("SV"));
 	            oppg.setTimeLength(newSet.getInt("oppgTimeLength"));
 	            oppg.setTpr(newSet.getFloat("TPR"));
 	            oppg.setUploadTime(TimeUtil.paserDatetime2(newSet.getString("oppgUploadTime")));
 	            oppg.setV(newSet.getFloat("V"));
 	            oppg.setMemberId(newSet.getInt("MemberId"));
 	            oppg.setK1Level(newSet.getInt("kLevel"));
 	            oppg.setSvLevel(newSet.getInt("svLevel"));
 	            oppg.setCoLevel(newSet.getInt("coLevel"));
 	            oppg.setAcLevel(newSet.getInt("acLevel"));
 	            oppg.setSiLevel(newSet.getInt("siLevel"));
 	            oppg.setV1Level(newSet.getInt("vLevel"));
 	            oppg.setTprLevel(newSet.getInt("tprLevel"));
 	            oppg.setSpoLevel(newSet.getInt("spoLevel"));
 	            oppg.setCiLevel(newSet.getInt("ciLevel"));
 	            oppg.setSpiLevel(newSet.getInt("spiLevel"));
 	            oppg.setPwttLevel(newSet.getInt("pwttLevel"));
 	            oppg.setPrLevel(newSet.getInt("prLevel"));
 	            oppg.setDeviceCode(newSet.getString("DeviceCode"));
 	            oppg.setBluetoothMacAddr(newSet.getString("oppgBluetoothMacAddr"));
 	           
 	           if(oecg.getEcgResult() == 1 || oppg.getPpgResult() == 1){
 	               measureData.setIsAbnormal(1);
 	           }
 	           measureData.setMeasureTime(oecg.getMeasureTime());
 	           measureData.setDataType(param.getEventType());
 	           Map<String,Object> datamap = new HashMap<String,Object>();
 	           datamap.put("oecg", oecg);
 	           datamap.put("oppg", oppg);
 	           measureData.setData(datamap);
 	           list.add(measureData);
    		}
    	}else{
    		String sql = "select a.eventType, b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,"
                    +" b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel,"
                    +" c.BluetoothMacAddr as oppgBluetoothMacAddr, c.fs as oppgfs, c.Docentry as oppgDocentry,c.TimeLength as oppgTimeLength,c.UploadTime as oppgUploadTime,c.MeasureTime,c.PulsebeatCount,c.SlowPulse,"
                    +" c.SlowTime,c.QuickPulse,c.QuickTime,c.PulseRate,c.Spo,c.SPO1,c.CO,c.SI,c.SV,c.CI,c.SPI,c.K,c.K1,c.V,c.TPR,c.PWTT,c.Pm,c.AC, c.ImageNum,c.AbnormalData,c.Ppgrr,c.RawPPG,c.PPGResult,c.StatusTag as oppgStatusTag,"
                    +" c.kLevel,c.svLevel,c.coLevel,c.siLevel,c.vLevel,c.tprLevel,c.spoLevel,c.ciLevel,c.spiLevel,c.pwttLevel,c.acLevel,c.prLevel"
                    +" from omds a left join oecg b on a.eventId=b.eventid  LEFT JOIN oppg c on a.eventid = c.EventId"
                    +" where a.MemberId=? and a.EventType=?   and b.measTime between ? and ?  and b.delTag='0'  and c.DelTag='0' order by b.measTime desc limit ?";
       
	       SqlRowSet rowSet = jdbcService.query(sql,new Object[] { param.getMemberId(), param.getDataType(), param.getTimeStart(),
	                                            param.getTimeEnd(), param.getCount() });
	       while (rowSet.next()) {
	           ReturnMeasureData measureData =  new ReturnMeasureData();
	           //封装心电对象
	           Oecg oecg = new Oecg();
	           oecg.setId(rowSet.getLong("Docentry"));
	           oecg.setEcgResult(rowSet.getInt("ECGResult"));
	           List<Ecg2> ecg2s = new ArrayList<Ecg2>();
	           if(oecg.getEcgResult() == 1){
	               ecg2s = findEcg2ById(oecg.getId() + "");
	           }
	           oecg.setEcg2s(ecg2s);
	           oecg.setFs(rowSet.getInt("fs"));
	           oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
	           
	           oecg.setEventId(rowSet.getLong("EventId"));
	           oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
	           oecg.setFastestTime(rowSet.getInt("FastestTime"));
	           oecg.setFrepPsd(rowSet.getString("FreqPSD"));
	           oecg.setHeartNum(rowSet.getInt("HeartNum"));
	           oecg.setId(rowSet.getLong("Docentry"));
	           oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("measTime")));
	           oecg.setRawEcg(rowSet.getString("RawECG"));
	           oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
	           oecg.setRrInterval(rowSet.getString("RRInterval"));
	           oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
	           oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
	           oecg.setStatusTag(rowSet.getInt("StatusTag"));
	           oecg.setTimeLength(rowSet.getInt("TimeLength"));
	           oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
	           oecg.setMemberId(param.getMemberId());
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
	           oecg.setDeviceCode(rowSet.getString("DeviceCode"));
	           oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
	           oecg.setPnn50(rowSet.getFloat("pnn50"));
	           oecg.setSdnn(rowSet.getFloat("sdnn"));
	           oecg.setHrvi(rowSet.getFloat("hrvi"));
	           oecg.setEventType(rowSet.getInt("eventType"));
	           
	           //封装脉搏对象
	           Oppg oppg = new Oppg();
	           oppg.setFs(rowSet.getInt("oppgfs"));
	           oppg.setAbnormalData(rowSet.getInt("AbnormalData"));
	           oppg.setAc(rowSet.getFloat("AC"));
	           oppg.setCi(rowSet.getFloat("CI"));
	           oppg.setCo(rowSet.getFloat("CO"));
	           oppg.setEventId(rowSet.getLong("EventId"));
	           oppg.setId(rowSet.getLong("oppgDocentry"));
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
	           oppg.setStatusTag(rowSet.getInt("oppgStatusTag"));
	           oppg.setSv(rowSet.getFloat("SV"));
	           oppg.setTimeLength(rowSet.getInt("oppgTimeLength"));
	           oppg.setTpr(rowSet.getFloat("TPR"));
	           oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("oppgUploadTime")));
	           oppg.setV(rowSet.getFloat("V"));
	           oppg.setMemberId(param.getMemberId());
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
	           oppg.setPrLevel(rowSet.getInt("prLevel"));
	           oppg.setDeviceCode(rowSet.getString("DeviceCode"));
	           oppg.setBluetoothMacAddr(rowSet.getString("oppgBluetoothMacAddr"));
	           
	           if(oecg.getEcgResult() == 1 || oppg.getPpgResult() == 1){
	               measureData.setIsAbnormal(1);
	           }
	           measureData.setMeasureTime(oecg.getMeasureTime());
	           measureData.setDataType(param.getDataType());
	           Map<String,Object> datamap = new HashMap<String,Object>();
	           datamap.put("oecg", oecg);
	           datamap.put("oppg", oppg);
	           measureData.setData(datamap);
	           list.add(measureData);
	    	}
        }
        return list;
    }
    
    /** 
     * @Title: findOecgByOmdsValueNew 
     * @Description: 根据参数获取动态心电minilist 
     * @liuxaioqin
     * @createDate 2016-01-28
     * @param param
     * @return
     * @throws Exception    
     * @retrun List<ReturnMeasureData>
     */
    public List<ReturnMeasureData> findOecgByOmdsValueNew(OmdsParam param)throws Exception{
    	List<ReturnMeasureData> list = new ArrayList<ReturnMeasureData>();
    	String eventIds = param.getEventIds();
    	if(!StringUtils.isEmpty(eventIds)){
    		String newSql = " select a.MemberId,a.eventType, b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry, "
    				      + " b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime, "
    				      + " b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level, "
    				      + " b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel from omds a left join oecg b "
    				      + " on a.eventId = b.eventid where a.EventId in ("+eventIds+") and a.EventType= ? and a.MemberId = ? and b.delTag = '0' order by b.measTime desc ";
    		SqlRowSet newSet = jdbcService.query(newSql,new Object[] {param.getEventType(),param.getMemberId()});
    		while (newSet.next()) {
    			ReturnMeasureData measureData = new ReturnMeasureData();
    			Oecg oecg = new Oecg();
                oecg.setId(newSet.getLong("Docentry"));
                oecg.setEcgResult(newSet.getInt("ECGResult"));
                List<Ecg2> ecg2s = new ArrayList<Ecg2> ();
                if(oecg.getEcgResult() != 0){
                    ecg2s = findEcg2ById(oecg.getId() + "");
                }
                oecg.setEcg2s(ecg2s);
                oecg.setFs(newSet.getInt("fs"));
                oecg.setAverageHeart(newSet.getInt("AverageHeart"));
                oecg.setEventId(newSet.getLong("EventId"));
                oecg.setFastestBeat(newSet.getInt("FastestBeat"));
                oecg.setFastestTime(newSet.getInt("FastestTime"));
                oecg.setFrepPsd(newSet.getString("FreqPSD"));
                oecg.setHeartNum(newSet.getInt("HeartNum"));
                oecg.setId(newSet.getLong("Docentry"));
                oecg.setMeasureTime(TimeUtil.paserDatetime2(newSet.getString("measTime")));
                oecg.setRawEcg(newSet.getString("RawECG"));
                oecg.setRawEcgImg(newSet.getString("RawECGImg"));
                oecg.setRrInterval(newSet.getString("RRInterval"));
                oecg.setSlowestBeat(newSet.getInt("SlowestBeat"));
                oecg.setSlowestTime(newSet.getInt("SlowestTime"));
                oecg.setStatusTag(newSet.getInt("StatusTag"));
                oecg.setTimeLength(newSet.getInt("TimeLength"));
                oecg.setUploadTime(TimeUtil.paserDatetime2(newSet.getString("UploadTime")));
                oecg.setMemberId(newSet.getInt("MemberId"));
                oecg.setSdnnLevel(newSet.getInt("sdnnLevel"));
                oecg.setPnn50Level(newSet.getInt("pnn50Level"));
                oecg.setHrviLevel(newSet.getInt("hrviLevel"));
                oecg.setRmssdLevel(newSet.getInt("rmssdLevel"));
                oecg.setTpLevel(newSet.getInt("tplevel"));
                oecg.setVlfLevel(newSet.getInt("vlfLevel"));
                oecg.setLfLevel(newSet.getInt("lfLevel"));
                oecg.setHfLevel(newSet.getInt("hfLevel"));
                oecg.setLhrLevel(newSet.getInt("lhrLevel"));
                oecg.setHrLevel(newSet.getInt("hrLevel"));
                oecg.setDeviceCode(newSet.getString("DeviceCode"));
                oecg.setBluetoothMacAddr(newSet.getString("BluetoothMacAddr"));
                oecg.setPnn50(newSet.getFloat("pnn50"));
                oecg.setSdnn(newSet.getFloat("sdnn"));
                oecg.setHrvi(newSet.getFloat("hrvi"));
                oecg.setEventType(newSet.getInt("eventType"));
                measureData.setData(oecg);
                measureData.setDataType(param.getEventType());
                measureData.setIsAbnormal(oecg.getEcgResult());
                measureData.setMeasureTime(oecg.getMeasureTime());
                list.add(measureData);
    		}
    	}else{
    		String sql = "select a.eventType, b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel"
                    + " from omds a left join oecg b on a.eventId=b.eventid where a.MemberId=? and a.EventType=?   and b.measTime between ? and ? and b.delTag='0' order by b.measTime desc limit ? ";
            SqlRowSet rowSet = jdbcService.query(sql,new Object[] { param.getMemberId(), param.getDataType(), param.getTimeStart(),
                                                 param.getTimeEnd(), param.getCount() });
            while (rowSet.next()) {
                ReturnMeasureData measureData = new ReturnMeasureData();
                Oecg oecg = new Oecg();
                oecg.setId(rowSet.getLong("Docentry"));
                oecg.setEcgResult(rowSet.getInt("ECGResult"));
                List<Ecg2> ecg2s = new ArrayList<Ecg2> ();
                if(oecg.getEcgResult() != 0){
                    ecg2s = findEcg2ById(oecg.getId() + "");
                }
                oecg.setEcg2s(ecg2s);
                oecg.setFs(rowSet.getInt("fs"));
                oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
                oecg.setEventId(rowSet.getLong("EventId"));
                oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
                oecg.setFastestTime(rowSet.getInt("FastestTime"));
                oecg.setFrepPsd(rowSet.getString("FreqPSD"));
                oecg.setHeartNum(rowSet.getInt("HeartNum"));
                oecg.setId(rowSet.getLong("Docentry"));
                oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("measTime")));
                oecg.setRawEcg(rowSet.getString("RawECG"));
                oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
                oecg.setRrInterval(rowSet.getString("RRInterval"));
                oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
                oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
                oecg.setStatusTag(rowSet.getInt("StatusTag"));
                oecg.setTimeLength(rowSet.getInt("TimeLength"));
                oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
                oecg.setMemberId(param.getMemberId());
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
                oecg.setDeviceCode(rowSet.getString("DeviceCode"));
                oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
                oecg.setPnn50(rowSet.getFloat("pnn50"));
                oecg.setSdnn(rowSet.getFloat("sdnn"));
                oecg.setHrvi(rowSet.getFloat("hrvi"));
                measureData.setData(oecg);
                measureData.setDataType(param.getDataType());
                measureData.setIsAbnormal(oecg.getEcgResult());
                measureData.setMeasureTime(oecg.getMeasureTime());
                list.add(measureData);
            }
    	}
        return list;
    }
    
    /** 
     * @Title: findAllMeasureData 
     * @Description: 根据参数获取所有的测量数据
     * @liuxaioqin
     * @createDate 2016-02-02 
     * @param param
     * @return
     * @throws Exception    
     * @retrun List<ReturnMeasureData>
     */
    public List<ReturnMeasureData> findAllMeasureData(OmdsParam param)throws Exception{
        List<ReturnMeasureData> allRecordList = new ArrayList<ReturnMeasureData>();
        
        List<ReturnMeasureData> obsrRecordList = findAllObsrRecordList(param);
        if(obsrRecordList.size() > 0){
            allRecordList.addAll(obsrRecordList);
        }
        List<ReturnMeasureData> osbpRecordList = findAllOsbpRecordList(param);
        if(osbpRecordList.size() > 0){
            allRecordList.addAll(osbpRecordList);
        }
        List<ReturnMeasureData> oecgRecordList = findAllOecgRecordList(param);
        if(oecgRecordList.size() > 0){
            allRecordList.addAll(oecgRecordList);
        }
        List<ReturnMeasureData> threeJoinOneRecordList = findAllThreeJoinOneRecordList(param);
        if(threeJoinOneRecordList.size() > 0){
            allRecordList.addAll(threeJoinOneRecordList);
        }
        return allRecordList;
    }
    
    /** 
     * @Title: findAllObsrRecordList 
     * @Description: 根据会员id和查询时间查询所有血糖数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param param
     * @return
     * @throws Exception    
     * @retrun Obsr
     */
    public List<ReturnMeasureData> findAllObsrRecordList(OmdsParam param) throws Exception{
        List<ReturnMeasureData> obsrRecordList = new ArrayList<ReturnMeasureData>();
        String sql = " SELECT distinct a.Docentry,a.EventId,a.Memberid,a.UploadTime,a.TestTime,a.BsValue,a.timePeriod,a.AnalysisResult,a.DeviceCode,a.BluetoothMacAddr,b.EventType,b.WheAbnTag "
                   + " FROM obsr a LEFT JOIN omds b ON a.eventid = b.EventId WHERE a.Memberid = ? AND a.DelTag = '0' AND a.TestTime BETWEEN ? AND ? ORDER BY a.TestTime DESC ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{param.getMemberId(),param.getTimeStart(),param.getTimeEnd()});
        while (rowSet.next()) {
            ReturnMeasureData data = new ReturnMeasureData();
            int eventType = Integer.valueOf(rowSet.getString("EventType"));
            int isAbnormal = Integer.valueOf(rowSet.getString("WheAbnTag"));
            data.setDataType(eventType);
            data.setIsAbnormal(isAbnormal);
            Obsr osbr = new Obsr();
            osbr.setAnalysisResult(rowSet.getInt("AnalysisResult"));
            osbr.setBsValue(rowSet.getFloat("BsValue"));
            osbr.setEventId(rowSet.getLong("EventId"));
            osbr.setId(rowSet.getLong("Docentry"));
            osbr.setMemberId(rowSet.getInt("Memberid"));
            osbr.setTestTime(TimeUtil.paserDatetime2(rowSet.getString("TestTime")));
            osbr.setTimePeriod(rowSet.getInt("timePeriod"));
            osbr.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
            String bluetoothMacAddr = rowSet.getString("BluetoothMacAddr");
            if(!StringUtils.isEmpty(bluetoothMacAddr)){
                osbr.setBluetoothMacAddr(bluetoothMacAddr);
            }
            String deviceCode = rowSet.getString("DeviceCode");
            if(!StringUtils.isEmpty(deviceCode)){
                osbr.setBluetoothMacAddr(deviceCode);
            }
            data.setMeasureTime(osbr.getTestTime());
            data.setData(osbr);
            obsrRecordList.add(data);
        }
        return obsrRecordList;
    }
    
     /** 
     * @Title: findAllOsbpRecordList 
     * @Description:  根据会员id和查询时间查询所有血压数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param param
     * @return
     * @throws Exception    
     * @retrun Osbp
     */
    public List<ReturnMeasureData> findAllOsbpRecordList(OmdsParam param)throws Exception{
        List<ReturnMeasureData> osbpRecordList = new ArrayList<ReturnMeasureData>();
        String sql = " SELECT a.Docentry,a.EventId,a.Memberid,a.UploadTime,a.TestTime,a.Abnormal,a.timePeriod,a.SBP,a.DBP,a.PulseRate,a.BluetoothMacAddr,a.DeviceCode,b.EventType,b.WheAbnTag "
                   + " FROM osbp a LEFT JOIN omds b ON a.eventid = b.EventId WHERE a.Memberid = ? AND a.DelTag = '0' AND a.TestTime BETWEEN ? AND ? ORDER BY a.TestTime DESC ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{param.getMemberId(),param.getTimeStart(),param.getTimeEnd()});
        while (rowSet.next()) {
            ReturnMeasureData data = new ReturnMeasureData();
            int eventType = Integer.valueOf(rowSet.getString("EventType"));
            int isAbnormal = Integer.valueOf(rowSet.getString("WheAbnTag"));
            data.setDataType(eventType);
            data.setIsAbnormal(isAbnormal);
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
            osbp.setMemberId(rowSet.getInt("Memberid"));
            String bluetoothMacAddr = rowSet.getString("BluetoothMacAddr");
            if(!StringUtils.isEmpty(bluetoothMacAddr)){
                osbp.setBluetoothMacAddr(bluetoothMacAddr);
            }
            String deviceCode = rowSet.getString("DeviceCode");
            if(!StringUtils.isEmpty(deviceCode)){
                osbp.setBluetoothMacAddr(deviceCode);
            }
            data.setMeasureTime(osbp.getTestTime());
            data.setData(osbp);
            osbpRecordList.add(data);
        }
        return osbpRecordList;
    }
    
     /** 
     * @Title: findAllOecgRecordList 
     * @Description: 根据会员id和查询时间查询所有心电mini数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param param
     * @return
     * @throws Exception    
     * @retrun Oecg
     */
    public List<ReturnMeasureData> findAllOecgRecordList(OmdsParam param) throws Exception{
        List<ReturnMeasureData> oecgRecordList = new ArrayList<ReturnMeasureData>();
        String sql = " select b.Memberid,b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum, "
                + " b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel, "
                + " b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel,a.EventType,a.WheAbnTag "
                + " FROM oecg b LEFT JOIN omds a ON b.eventid = a.EventId WHERE b.Memberid = ? AND a.EventType = '4' AND b.DelTag = '0' AND b.measTime BETWEEN ? AND ? ORDER BY b.measTime DESC ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{param.getMemberId(),param.getTimeStart(),param.getTimeEnd()});
        while (rowSet.next()) {
            ReturnMeasureData data = new ReturnMeasureData();
            int eventType = Integer.valueOf(rowSet.getString("EventType"));
            int isAbnormal = Integer.valueOf(rowSet.getString("WheAbnTag"));
            data.setDataType(eventType);
            data.setIsAbnormal(isAbnormal);
            Oecg oecg = new Oecg();
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setEcgResult(rowSet.getInt("ECGResult"));
            List<Ecg2> ecg2s = new ArrayList<Ecg2> ();
            if(oecg.getEcgResult() != 0){
                ecg2s = findEcg2ById(oecg.getId() + "");
            }
            oecg.setEcg2s(ecg2s);
            oecg.setFs(rowSet.getInt("fs"));
            oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
            oecg.setEventId(rowSet.getLong("EventId"));
            oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
            oecg.setFastestTime(rowSet.getInt("FastestTime"));
            oecg.setFrepPsd(rowSet.getString("FreqPSD"));
            oecg.setHeartNum(rowSet.getInt("HeartNum"));
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("measTime")));
            oecg.setRawEcg(rowSet.getString("RawECG"));
            oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
            oecg.setRrInterval(rowSet.getString("RRInterval"));
            oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
            oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
            oecg.setStatusTag(rowSet.getInt("StatusTag"));
            oecg.setTimeLength(rowSet.getInt("TimeLength"));
            oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
            oecg.setMemberId(rowSet.getInt("Memberid"));
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
            oecg.setDeviceCode(rowSet.getString("DeviceCode"));
            oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
            oecg.setPnn50(rowSet.getFloat("pnn50"));
            oecg.setSdnn(rowSet.getFloat("sdnn"));
            oecg.setHrvi(rowSet.getFloat("hrvi"));
            data.setMeasureTime(oecg.getMeasureTime());
            data.setData(oecg);
            oecgRecordList.add(data);
        }
        return oecgRecordList;
    }
    
    /** 
     * @Title: findThreeJoinOneRecordOne 
     * @Description: 根据会员id和查询时间查询所有三合一数据 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param param
     * @return
     * @throws Exception    
     * @retrun Oppg
     */
    public List<ReturnMeasureData> findAllThreeJoinOneRecordList(OmdsParam param)throws Exception{
        List<ReturnMeasureData> threeJoinOneRecordList = new ArrayList<ReturnMeasureData>();
        String sql = " select b.Memberid,b.sdnn,b.pnn50,b.hrvi,  b.BluetoothMacAddr, b.DeviceCode, b.fs, b.measTime,b.Docentry,b.EventId,b.UploadTime,b.TimeLength,b.HeartNum,b.SlowestBeat,b.SlowestTime,b.FastestBeat,b.FastestTime,"
                     +" b.AverageHeart,b.RawECGImg,b.FreqPSD,b.RRInterval,b.RawECG,b.ECGResult,b.StatusTag,b.SDNNLevel,b.pnn50Level,b.hrviLevel,b.rmssdLevel,b.tplevel,b.vlfLevel,b.lfLevel,b.hfLevel,b.lhrLevel,b.hrLevel,"
                     +" c.BluetoothMacAddr as oppgBluetoothMacAddr, c.fs as oppgfs, c.Docentry as oppgDocentry,c.TimeLength as oppgTimeLength,c.UploadTime as oppgUploadTime,c.MeasureTime,c.PulsebeatCount,c.SlowPulse,"
                     +" c.SlowTime,c.QuickPulse,c.QuickTime,c.PulseRate,c.Spo,c.SPO1,c.CO,c.SI,c.SV,c.CI,c.SPI,c.K,c.K1,c.V,c.TPR,c.PWTT,c.Pm,c.AC, c.ImageNum,c.AbnormalData,c.Ppgrr,c.RawPPG,c.PPGResult,c.StatusTag as oppgStatusTag,"
                     +" c.kLevel,c.svLevel,c.coLevel,c.siLevel,c.vLevel,c.tprLevel,c.spoLevel,c.ciLevel,c.spiLevel,c.pwttLevel,c.acLevel,c.prLevel"
                     +" from oecg b LEFT JOIN oppg c on b.eventid = c.EventId"
                     +" where b.Memberid = ?  and b.delTag='0'  and c.DelTag='0'  AND b.measTime BETWEEN ? AND ? ORDER BY b.measTime DESC ";
        
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{param.getMemberId(),param.getTimeStart(),param.getTimeEnd()});
        while (rowSet.next()) {
            ReturnMeasureData measureData =  new ReturnMeasureData();
            //封装心电对象
            Oecg oecg = new Oecg();
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setEcgResult(rowSet.getInt("ECGResult"));
            List<Ecg2> ecg2s = new ArrayList<Ecg2>();
            if(oecg.getEcgResult() == 1){
                ecg2s = findEcg2ById(oecg.getId() + "");
            }
            oecg.setEcg2s(ecg2s);
            oecg.setFs(rowSet.getInt("fs"));
            oecg.setAverageHeart(rowSet.getInt("AverageHeart"));
            
            oecg.setEventId(rowSet.getLong("EventId"));
            oecg.setFastestBeat(rowSet.getInt("FastestBeat"));
            oecg.setFastestTime(rowSet.getInt("FastestTime"));
            oecg.setFrepPsd(rowSet.getString("FreqPSD"));
            oecg.setHeartNum(rowSet.getInt("HeartNum"));
            oecg.setId(rowSet.getLong("Docentry"));
            oecg.setMeasureTime(TimeUtil.paserDatetime2(rowSet.getString("measTime")));
            oecg.setRawEcg(rowSet.getString("RawECG"));
            oecg.setRawEcgImg(rowSet.getString("RawECGImg"));
            oecg.setRrInterval(rowSet.getString("RRInterval"));
            oecg.setSlowestBeat(rowSet.getInt("SlowestBeat"));
            oecg.setSlowestTime(rowSet.getInt("SlowestTime"));
            oecg.setStatusTag(rowSet.getInt("StatusTag"));
            oecg.setTimeLength(rowSet.getInt("TimeLength"));
            oecg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("UploadTime")));
            oecg.setMemberId(rowSet.getInt("Memberid"));
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
            oecg.setDeviceCode(rowSet.getString("DeviceCode"));
            oecg.setBluetoothMacAddr(rowSet.getString("BluetoothMacAddr"));
            oecg.setPnn50(rowSet.getFloat("pnn50"));
            oecg.setSdnn(rowSet.getFloat("sdnn"));
            oecg.setHrvi(rowSet.getFloat("hrvi"));
            oecg.setEventType(3);
            
            //封装脉搏对象
            Oppg oppg = new Oppg();
            oppg.setFs(rowSet.getInt("oppgfs"));
            oppg.setAbnormalData(rowSet.getInt("AbnormalData"));
            oppg.setAc(rowSet.getFloat("AC"));
            oppg.setCi(rowSet.getFloat("CI"));
            oppg.setCo(rowSet.getFloat("CO"));
            oppg.setEventId(rowSet.getLong("EventId"));
            oppg.setId(rowSet.getLong("oppgDocentry"));
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
            oppg.setStatusTag(rowSet.getInt("oppgStatusTag"));
            oppg.setSv(rowSet.getFloat("SV"));
            oppg.setTimeLength(rowSet.getInt("oppgTimeLength"));
            oppg.setTpr(rowSet.getFloat("TPR"));
            oppg.setUploadTime(TimeUtil.paserDatetime2(rowSet.getString("oppgUploadTime")));
            oppg.setV(rowSet.getFloat("V"));
            oppg.setMemberId(rowSet.getInt("Memberid"));
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
            oppg.setPrLevel(rowSet.getInt("prLevel"));
            oppg.setDeviceCode(rowSet.getString("DeviceCode"));
            oppg.setBluetoothMacAddr(rowSet.getString("oppgBluetoothMacAddr"));
            
            if(oecg.getEcgResult() == 1 || oppg.getPpgResult() == 1){
                measureData.setIsAbnormal(1);
            }
            Map<String,Object> datamap = new HashMap<String,Object>();
            datamap.put("oecg", oecg);
            datamap.put("oppg", oppg);
            measureData.setData(datamap);
            measureData.setMeasureTime(oecg.getMeasureTime());
            measureData.setDataType(3);
            threeJoinOneRecordList.add(measureData);
        }
        return threeJoinOneRecordList;
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
	public List<Map<String,Object>> getAllMeasureRecord(Integer memberId)throws Exception{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = " SELECT aa.EventType AS eventType, count(aa.Memberid) AS measureCount,SUM(aa.WheAbnTag) AS abnormalCount FROM ( "
				   + " SELECT a.Memberid,o.EventType,o.WheAbnTag from osbp a LEFT JOIN omds o ON a.eventid = o.eventid WHERE a.Memberid = ? "
				   + " AND a.DelTag = 0 AND (a.TestTime  <= CONCAT(CURDATE(),' 23:59:59' ) ) ) aa "
				   + " UNION "
				   + " SELECT bb.EventType AS eventType, count(bb.Memberid) AS measureCount,SUM(bb.WheAbnTag) AS abnormalCount FROM ( "
				   + " SELECT b.Memberid,o.EventType,o.WheAbnTag from obsr b LEFT JOIN omds o ON b.eventid = o.eventid WHERE b.Memberid = ?  "
				   + " AND b.DelTag = 0 AND (b.TestTime  <= CONCAT(CURDATE(),' 23:59:59' ) ) ) bb "
				   + " UNION "
				   + " SELECT cc.EventType AS eventType, count(cc.Memberid) AS measureCount,SUM(cc.WheAbnTag) AS abnormalCount FROM ( "
				   + " SELECT c.Memberid,o.EventType,o.WheAbnTag from oppg c LEFT JOIN omds o ON c.eventid = o.eventid WHERE c.Memberid = ?  "
				   + " AND c.DelTag = 0 AND (c.MeasureTime <= CONCAT(CURDATE(),' 23:59:59' ) ) ) cc "
				   + " UNION "
				   + " SELECT dd.EventType AS eventType, count(dd.Memberid) AS measureCount,SUM(dd.WheAbnTag) AS abnormalCount FROM ( "
				   + " SELECT d.Memberid,o.EventType,o.WheAbnTag from oecg d LEFT JOIN omds o ON d.eventid = o.eventid WHERE d.Memberid = ?   "
				   + " AND d.DelTag = 0 AND (d.MeasTime <= CONCAT(CURDATE(),' 23:59:59' ) ) AND o.EventType = 4) dd ";
		SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId,memberId,memberId,memberId});
		while(rowSet.next()){
			Map<String,Object> map = new HashMap<String, Object>();
			Integer eventType = rowSet.getInt("eventType");
			if(eventType != null && eventType != 0){
				map.put("eventType", eventType);
			}
			Integer measureCount = rowSet.getInt("measureCount");
			if(measureCount != null){
				map.put("measureCount", measureCount);
			}
			Integer abnormalCount = (int)rowSet.getDouble("abnormalCount");
			if(abnormalCount != null){
				map.put("abnormalCount", abnormalCount);
			}
			list.add(map);
		}
		return list;
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
	public List<Map<String,Object>> findLastestMonthMeasureCount(Integer memberId)throws Exception{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<String> dayList = findLastestMonthDays();
		for(String day : dayList){
			String sql = " SELECT SUM(abc.testCount)AS measureCount FROM( "
					   + " (SELECT count(*) AS testCount from (SELECT Date(a.TestTime) AS testTime FROM osbp a WHERE a.Memberid = ?  AND a.DelTag = 0  "
					   + " ORDER BY testTime DESC ) aa where aa.testTime = ? ) "
					   + " UNION "
					   + " (SELECT count(*) AS testCount from (SELECT Date(b.TestTime) AS testTime FROM obsr b WHERE b.Memberid = ?  AND b.DelTag = 0 "
					   + " ORDER BY testTime DESC ) bb where bb.testTime = ? ) "
					   + " UNION "
					   + " (SELECT count(*) AS testCount from (SELECT Date(c.MeasTime) AS testTime FROM oecg c WHERE c.Memberid = ?  AND c.DelTag = 0 "
					   + " ORDER BY testTime DESC ) cc where cc.testTime = ? )) abc ";
			SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId,day,memberId,day,memberId,day});
			while(rowSet.next()){
				Map<String,Object> map = new HashMap<String, Object>();
				String date = day.substring(5, 10);
				map.put("date", date);
				Integer measureCount = rowSet.getInt("measureCount");
				if(measureCount != null){
					map.put("measureCount", measureCount);
				}
				list.add(map);
			}
		}
		return list;
	}
	
	/** 
     * @Title: findLastestMonthDays 
     * @Description: 获取最近一个月的日期
     * @liuxaioqin
     * @createDate 2016-05-18 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    public List<String> findLastestMonthDays()throws Exception{
    	List<String> list = new ArrayList<String>();
    	Date date = new Date();
    	String today = TimeUtil.formatDate(date);
    	list.add(today);
        for(int i = 1;i<=30;i++){
        	Calendar c = Calendar.getInstance();
        	int day = c.get(Calendar.DATE);
        	c.set(Calendar.DATE, day - i);
        	String dayBefore = TimeUtil.formatDate(c.getTime());
        	list.add(dayBefore);
        }
        return list;
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
	public List<Map<String,Object>> findAllMeasureRecordByParam(Integer memberId,Integer eventType,Integer isAbnormal,
			String beginTime,String endTime,Integer pageNow,Integer pageSize)throws Exception{
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT * FROM ");
		sqlBuffer.append(" (SELECT o.EventType AS eventType,o.WheAbnTag AS isAbnormal,o.Memberid AS memberId,a.EventId AS eventId,a.TestTime AS testTime, ");
		sqlBuffer.append(" a.Docentry as id,a.SBP AS systolic,a.DBP AS diastolic,a.PulseRate AS pulseRate, ");
		sqlBuffer.append(" NULL AS bloodSugar,NULL AS timePeriod, a.Abnormal AS analysisResult,NULL AS averageHeart, NULL AS averagePulseRate,NULL AS analysisStatus ");
		sqlBuffer.append(" FROM osbp a LEFT JOIN omds o ON a.EventId = o.EventId WHERE a.DelTag = 0 AND a.Memberid = ? ");
		sqlBuffer.append(" UNION ");
		sqlBuffer.append(" SELECT o.EventType AS eventType,o.WheAbnTag AS isAbnormal,o.Memberid AS memberId,b.EventId AS eventId,b.TestTime AS testTime, ");
		sqlBuffer.append(" b.Docentry as id,NULL AS systolic, NULL AS diastolic, NULL AS pulseRate, ");
		sqlBuffer.append(" b.BsValue AS bloodSugar,b.timePeriod,b.AnalysisResult AS analysisResult,NULL AS averageHeart, NULL AS averagePulseRate,NULL AS analysisStatus ");
		sqlBuffer.append(" FROM obsr b LEFT JOIN omds o ON  b.EventId = o.EventId WHERE b.DelTag = 0 AND b.Memberid = ? ");
		sqlBuffer.append(" UNION ");
		sqlBuffer.append(" SELECT o.EventType AS eventType,o.WheAbnTag AS isAbnormal,o.Memberid AS memberId,c.EventId AS eventId,c.MeasTime AS testTime, ");
		sqlBuffer.append(" c.Docentry as id,NULL AS systolic, NULL AS diastolic, NULL AS pulseRate,NULL AS bloodSugar,NULL AS timePeriod, ");
		sqlBuffer.append(" CAST(c.ECGResult AS CHAR) AS analysisResult,c.AverageHeart AS averageHeart,d.PulseRate AS averagePulseRate,c.StatusTag AS analysisStatus ");
		sqlBuffer.append(" FROM oecg c LEFT JOIN omds o ON  c.EventId = o.EventId LEFT JOIN oppg d ON c.EventId = d.EventId WHERE c.DelTag = 0 AND c.Memberid = ? ");
		sqlBuffer.append(" ) aa ");
		sqlBuffer.append(" WHERE aa.memberId = ?  ");
		if(!StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)){
			sqlBuffer.append(" AND  (aa.testTime BETWEEN '"+beginTime+"' AND '"+endTime+"')  ");
		}
		if(isAbnormal != null && isAbnormal ==1){
			sqlBuffer.append(" AND aa.isAbnormal = 1 ");
		}
		if(isAbnormal != null && isAbnormal ==0){
			sqlBuffer.append(" AND aa.isAbnormal = 0 ");
		}
		if(eventType != null && eventType >0){
			sqlBuffer.append(" AND aa.eventType = "+eventType+" ");
		}
		int endRecord = pageSize;
        int beginRecord = (pageNow - 1)*endRecord;
		sqlBuffer.append(" ORDER BY aa.testTime DESC limit "+beginRecord+" ," +endRecord +" ");
		String sql = sqlBuffer.toString();
		SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId,memberId,memberId,memberId});
		while(rowSet.next()){
			Map<String,Object> map = new HashMap<String, Object>();
			Integer eventId = rowSet.getInt("eventId");
			Integer newEventType = rowSet.getInt("eventType");
			Integer newIsAbnormal = rowSet.getInt("isAbnormal");
			Integer id = rowSet.getInt("id");
			Integer abnormalCount = findOecgAbnormalCount(id);
			String testTime = "";
			Date date = rowSet.getDate("testTime");
			if(date != null){
				testTime = TimeUtil.formatDatetime(date);
			}
			Integer newMemberId = rowSet.getInt("memberId");
			map.put("eventId",eventId);
			map.put("eventType",newEventType);
			map.put("isAbnormal",newIsAbnormal);
			map.put("testTime",testTime);
			map.put("memberId",newMemberId);
			if(newEventType == 1){
				Integer systolic = rowSet.getInt("systolic");
				map.put("systolic",systolic);
				Integer diastolic = rowSet.getInt("diastolic");
				map.put("diastolic",diastolic);
				Integer pulseRate = rowSet.getInt("pulseRate");
				map.put("pulseRate",pulseRate);
				String analysisResult = rowSet.getString("analysisResult");
				Integer analysisResultNew = Integer.valueOf(analysisResult);
				map.put("analysisResult",analysisResultNew);
			}else if(newEventType == 2){
				Double bloodSugar = rowSet.getDouble("bloodSugar");
				map.put("bloodSugar",bloodSugar);
				Integer timePeriod = rowSet.getInt("timePeriod");
				map.put("timePeriod",timePeriod);
				String analysisResult = rowSet.getString("analysisResult");
                Integer analysisResultNew = Integer.valueOf(analysisResult);
                map.put("analysisResult",analysisResultNew);
			}else if(newEventType == 3){
			    String analysisResult = rowSet.getString("analysisResult");
                Integer analysisResultNew = Integer.valueOf(analysisResult);
                map.put("analysisResult",analysisResultNew);
				Integer averageHeart = rowSet.getInt("averageHeart");
				map.put("averageHeart",averageHeart);
				Integer averagePulseRate = rowSet.getInt("averagePulseRate");
				map.put("averagePulseRate",averagePulseRate);
				Integer oppgAbnormalCount = getOppgAbnormalCount(eventId,averagePulseRate);
				abnormalCount += oppgAbnormalCount;
				map.put("abnormalCount",abnormalCount);
				Integer analysisStatus = rowSet.getInt("analysisStatus");
				map.put("statusTag",analysisStatus);
			}else{
			    String analysisResult = rowSet.getString("analysisResult");
                Integer analysisResultNew = Integer.valueOf(analysisResult);
                map.put("analysisResult",analysisResultNew);
				Integer averageHeart = rowSet.getInt("averageHeart");
				map.put("averageHeart",averageHeart);
				map.put("abnormalCount",abnormalCount);
				Integer analysisStatus = rowSet.getInt("analysisStatus");
				map.put("statusTag",analysisStatus);
			}
			mapList.add(map);
		}
		return mapList;
	}
    
	/** 
     * @Title: getOppgAbnormalCount 
     * @Description: 获取脉搏异常个数
     * @liuxaioqin
     * @createDate 2016-05-30 
     * @param request
     * @param response
     * @throws Exception    
     * @retrun Integer
     */
	public Integer getOppgAbnormalCount(Integer eventId,Integer averagePulseRate)throws Exception{
		Integer count = 0;
		String sql = " SELECT  CAST((CONCAT(SPILevel,PPGResult)) AS CHAR) AS SPILevelStr,CAST((CONCAT(CILevel,PPGResult)) AS CHAR) AS CILevelstr,"
				   + " CAST((CONCAT(SPOLevel,PPGResult)) AS CHAR) AS SPOLevelStr, "
				   + " CAST((CONCAT(TPRLevel,PPGResult)) AS CHAR) AS TPRLevelStr,CAST((CONCAT(VLevel,PPGResult)) AS CHAR) AS VLevelStr,CAST((CONCAT(SILevel,PPGResult)) AS CHAR) AS SILevelStr, "
				   + " CAST((CONCAT(ACLevel,PPGResult)) AS CHAR) AS ACLevelStr,CAST((CONCAT(COLevel,PPGResult)) AS CHAR) AS COLevelStr,CAST((CONCAT(SVLevel,PPGResult)) AS CHAR) AS SVLevelStr, "
				   + " CAST((CONCAT(KLevel,PPGResult)) AS CHAR) AS KLevelStr,PPGResult,StatusTag,Docentry,Pm FROM oppg WHERE EventId = ? AND DelTag = 0 ";
		SqlRowSet rowSet = jdbcService.query(sql,new Object[]{eventId});
		while(rowSet.next()){
			Integer statusTag = rowSet.getInt("StatusTag");
			if(statusTag != null && statusTag == 2){
				Double pm = rowSet.getDouble("Pm");
				if((pm != null && pm >= 105) || (pm != null && pm <= 70)){
					count += 1;
				}
				if(averagePulseRate < 55 || averagePulseRate > 100){
					count += 1;
				}
				String sPILevelStr = rowSet.getString("SPILevelStr");
				if(!sPILevelStr.contains("0")){
					count += 1;
				}
				String cILevelstr = rowSet.getString("CILevelstr");
				if(!cILevelstr.contains("0")){
					count += 1;
				}
				String sPOLevelStr = rowSet.getString("SPOLevelStr");
				if(!sPOLevelStr.contains("0")){
					count += 1;
				}
				String tPRLevelStr = rowSet.getString("TPRLevelStr");
				if(!tPRLevelStr.contains("0")){
					count += 1;
				}
				String vLevelStr = rowSet.getString("VLevelStr");
				if(!vLevelStr.contains("0")){
					count += 1;
				}
				String sILevelStr = rowSet.getString("SILevelStr");
				if(!sILevelStr.contains("0")){
					count += 1;
				}
				String aCLevelStr = rowSet.getString("ACLevelStr");
				if(!aCLevelStr.contains("0")){
					count += 1;
				}
				String cOLevelStr = rowSet.getString("COLevelStr");
				if(!cOLevelStr.contains("0")){
					count += 1;
				}
				String sVLevelStr = rowSet.getString("SVLevelStr");
				if(!sVLevelStr.contains("0")){
					count += 1;
				}
				String kLevelStr = rowSet.getString("KLevelStr");
				if(!kLevelStr.contains("0")){
					count += 1;
				}
			}
		}
		return count;
	}
	
	/** 
     * @Title: findOecgAbnormalCount 
     * @Description: 获取mini心电异常个数
     * @liuxaioqin
     * @createDate 2016-05-30 
     * @param request
     * @param response
     * @throws Exception    
     * @retrun Integer
     */
	public Integer findOecgAbnormalCount(Integer oecgId) throws Exception {
		Integer count = 0;
		String sql = " SELECT COUNT(Docentry) AS COUNT FROM ecg2 WHERE Docentry = ? ";
		count = jdbcService.queryForInt(sql, new Object[]{oecgId});
		return count;
	}
	
	/** 
     * @Title: checkHasObsrRecord 
     * @Description: 检测该会员的该测量时间是否已存在血糖数据
     * @liuxaioqin
     * @createDate 2016-12-08 
     * @param memberId
     * @param testTime
     * @throws Exception    
     * @retrun Integer
     */
    public Integer checkHasObsrRecord(Integer memberId,String testTime) throws Exception {
        Integer count = 0;
        Date time = TimeUtil.parseDatetime(testTime, "yyyyMMddHHmmss");
        String sql = " SELECT Docentry FROM obsr WHERE Memberid = ? and TestTime = ? and DelTag = '0' ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId,time});
        while(rowSet.next()){
            count = 1;
        }
        return count;
    }
    
    /** 
     * @Title: checkHasOsbpRecord 
     * @Description: 检测该会员的该测量时间是否已存在血压数据
     * @liuxaioqin
     * @createDate 2016-12-08 
     * @param memberId
     * @param testTime
     * @throws Exception    
     * @retrun Integer
     */
    public Integer checkHasOsbpRecord(Integer memberId,String testTime) throws Exception {
        Integer count = 0;
        Date time = TimeUtil.parseDatetime(testTime, "yyyyMMddHHmmss");
        String sql = " SELECT Docentry FROM osbp WHERE Memberid = ? and TestTime = ? and DelTag = '0' ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId,time});
        while(rowSet.next()){
            count = 1;
        }
        return count;
    }
    
    /** 
     * @Title: checkHasOecgRecord 
     * @Description: 检测该会员的该测量时间是否已存在mini心电数据
     * @liuxaioqin
     * @createDate 2016-12-08 
     * @param memberId
     * @param testTime
     * @throws Exception    
     * @retrun Integer
     */
    public Integer checkHasOecgRecord(Integer memberId,String testTime) throws Exception {
        Integer count = 0;
        Date time = TimeUtil.parseDatetime(testTime, "yyyyMMddHHmmss");
        String sql = " SELECT Docentry FROM oecg WHERE Memberid = ? and MeasTime = ? and DelTag = '0' ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId,time});
        while(rowSet.next()){
            count = 1;
        }
        return count;
    }
    
    /** 
     * @Title: checkHasThreeInOneRecord 
     * @Description: 检测该会员的该测量时间是否已存在三合一数据
     * @liuxaioqin
     * @createDate 2016-12-08 
     * @param memberId
     * @param testTime
     * @throws Exception    
     * @retrun Integer
     */
    public Integer checkHasThreeInOneRecord(Integer memberId,String testTime) throws Exception {
        Integer count = 0;
        Date time = TimeUtil.parseDatetime(testTime, "yyyyMMddHHmmss");
        String sql = " SELECT Docentry FROM oppg WHERE Memberid = ? and MeasureTime = ? and DelTag = '0' ";
        SqlRowSet rowSet = jdbcService.query(sql,new Object[]{memberId,time});
        while(rowSet.next()){
            count = 1;
        }
        return count;
    }
	
    /** 
     * @Title: insertOmdsReturnPrimaryKey 
     * @Description: 新增测量数据保存到omds自动返回主键key
     * @liuxaioqin
     * @createDate 2017-02-08 
     * @param omds
     * @throws Exception    
     * @retrun Long
     */
    public Long insertOmdsReturnPrimaryKey(Omds omds) throws Exception{
        Long eventId = null;
        String sql = "INSERT INTO omds ( Memberid ,UploadTime ,EventType ,WheAbnTag ,StatusTag,TimeLength ) VALUES (?,?,?,?,?,?)";
        eventId = (long)jdbcService.doExecuteSQLReturnId(sql,new Object[] { omds.getMemberId(),omds.getUploadTime(), omds.getDataType(),
                        omds.getWheAbnTag(), omds.getStatusTag(),omds.getTimeLength() },"omds","eventid");
        return eventId;
    }
    
    /** 
     * @Title: insertOsbpReturnPrimaryKey 
     * @Description: 新增血压数据保存到osbp自动返回主键key
     * @liuxaioqin
     * @createDate 2017-02-08 
     * @param osbp
     * @throws Exception    
     * @retrun Long
     */
    public Long insertOsbpReturnPrimaryKey(Osbp osbp) throws Exception{
        Long docentry = null;
        String sql = "INSERT INTO osbp (EventId ,Memberid ,UploadTime ,TestTime ,DelTag ,Abnormal ,timePeriod ,SBP ,DBP ,PulseRate ,BluetoothMacAddr ,DeviceCode ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        docentry = (long)jdbcService.doExecuteSQLReturnId(sql, new Object[] { osbp.getEventId(), osbp.getMemberId(), osbp.getUploadTime(),osbp.getTestTime(), 0, osbp.getAbnormal(),
                        osbp.getTimePeriod(), osbp.getSbp(), osbp.getDbp(),osbp.getPulseRate(), osbp.getBluetoothMacAddr(),osbp.getDeviceCode() },"osbp","Docentry");
        return docentry;
    }
    
    /** 
     * @Title: insertObsrReturnPrimaryKey 
     * @Description: 新增血糖数据保存到osbp自动返回主键key
     * @liuxaioqin
     * @createDate 2017-02-08 
     * @param obsr
     * @throws Exception    
     * @retrun Long
     */
    public Long insertObsrReturnPrimaryKey(Obsr obsr) throws Exception{
        Long docentry = null;
        String sql = "INSERT INTO obsr (EventId ,Memberid ,UploadTime ,TestTime ,DelTag ,BsValue ,timePeriod ,AnalysisResult ,DeviceCode ,BluetoothMacAddr ) VALUES (?,?,?,?,?,?,?,?,?,?)";
        docentry = (long)jdbcService.doExecuteSQLReturnId(sql,new Object[] { obsr.getEventId(),obsr.getMemberId(), obsr.getUploadTime(),obsr.getTestTime(), 0, obsr.getBsValue(),
                        obsr.getTimePeriod(), obsr.getAnalysisResult(),obsr.getDeviceCode(), obsr.getBluetoothMacAddr() },"obsr","Docentry");
        return docentry;
    }
    
    /** 
     * @Title: insertOecgReturnPrimaryKey 
     * @Description: 新增心电mini数据保存到oecg自动返回主键key
     * @liuxaioqin
     * @createDate 2017-02-08 
     * @param oecg
     * @throws Exception    
     * @retrun Long
     */
    public Long insertOecgReturnPrimaryKey(Oecg oecg) throws Exception{
        Long docentry = null;
        String sql = "INSERT INTO oecg ( EventId ,Memberid ,UploadTime ,DeviceCode ,BluetoothMacAddr ,RawECG ,DelTag ,StatusTag,fs,TimeLength,MeasTime,addValue) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        docentry = (long)jdbcService.doExecuteSQLReturnId(sql, new Object[] { oecg.getEventId(),oecg.getMemberId(), oecg.getUploadTime(),oecg.getDeviceCode(), oecg.getBluetoothMacAddr(),
                        oecg.getRawEcg(), 0, 0, oecg.getFs(),oecg.getTimeLength(), oecg.getMeasureTime(),oecg.getAddValue() },"oecg","Docentry");
        return docentry;
    }
    
    /** 
     * @Title: insertOppgReturnPrimaryKey 
     * @Description: 新增脉搏数据保存到oppg自动返回主键key
     * @liuxaioqin
     * @createDate 2017-02-08 
     * @param oppg
     * @throws Exception    
     * @retrun Long
     */
    public Long insertOppgReturnPrimaryKey(Oppg oppg) throws Exception{
        Long docentry = null;
        String sql = " INSERT INTO oppg ( EventId ,Memberid ,UploadTime ,RawPPG ,BluetoothMacAddr ,DeviceCode ,StatusTag ,DelTag,FS,TimeLength,MeasureTime,addValue ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        docentry = (long)jdbcService.doExecuteSQLReturnId(sql,new Object[] { oppg.getEventId(),oppg.getMemberId(), oppg.getUploadTime(),oppg.getRawPpg(), 
                oppg.getBluetoothMacAddr(),oppg.getDeviceCode(), 0, 0, oppg.getFs(),oppg.getTimeLength(), oppg.getMeasureTime(),oppg.getAddValue() },"oppg","Docentry");
        return docentry;
    }
    
}
