package com.zkhk.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkhk.dao.PackageDao;
import com.zkhk.dao.PushMessageDao;
import com.zkhk.entity.CallValue;
import com.zkhk.entity.Mem5;
import com.zkhk.entity.Message;
import com.zkhk.entity.Opsp;
import com.zkhk.entity.ReturnValue;
import com.zkhk.util.TimeUtil;

@Service("packageService")
public class PackageServcieImpl implements PackageService{
	private Logger logger=Logger.getLogger(PackageServcieImpl.class);
    @Resource(name="packageDao")
    private PackageDao packageDao;
	@Resource(name = "pushMessageDao")
	private PushMessageDao messageDao;
	public String findPackageByParam(HttpServletRequest request)throws Exception {
		String params = request.getParameter("params");
		CallValue callValue = JSON.parseObject(params, CallValue.class);
		int memberId=callValue.getMemberId();
		 if(callValue.getParam()!=null&&!"".equals(callValue.getParam())){
			 JSONObject object=JSON.parseObject(callValue.getParam());
			 memberId=object.getIntValue("memberId");
		 }
		ReturnValue re = new ReturnValue();
			List<Opsp> opsps= packageDao.findPackagebyParam(memberId);
			if (opsps.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(opsps));
				logger.info("查询套餐信息成功");
			} else {
				re.setState(1);
				re.setMessage("查询不到套餐信息");
				logger.info("查询不到套餐信息");
			}
		return JSON.toJSONString(re);
	}
	public String findMemPackageByParam(HttpServletRequest request)throws Exception {
		    ReturnValue re = new ReturnValue();
			String params = request.getParameter("params");
			CallValue callValue = JSON.parseObject(params, CallValue.class);
			String param=callValue.getParam();
			List<Mem5> mem5s=null;
			if(param!=null){
				JSONObject object=JSON.parseObject(param);
				int id=object.getIntValue("memberId");
				 mem5s= packageDao.findMemPackagebyParam(id);
					if(id!=callValue.getMemberId()){
                     messageDao.updateMarkTagByCreateId(id, callValue.getMemberId(), 6);
					}
			}else{
				
			 mem5s= packageDao.findMemPackagebyParam(callValue.getMemberId());
			 
			}
			if (mem5s.size()>0) {
				re.setState(0);
				re.setMessage("成功");
				re.setContent(JSON.toJSONString(mem5s));
				logger.info("查询会员订购套餐信息成功");
				
			} else {
				re.setState(1);
				re.setMessage("查询不到该会员订购套餐信息");
				logger.info("查询不到该会员订购套餐信息");
			}
		return JSON.toJSONString(re);
	}
}
