package com.zkhk.contoller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.zkhk.constants.Constants;
import com.zkhk.entity.ReturnResult;
import com.zkhk.services.PcService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

/**
 * @ClassName:     PcController.java 
 * @Description:   pc端操作(上传mini等)
 * @author         liuxiaoqin  
 * @version        V1.0   
 * @Date           2016年1月25日 下午2:39:50
*****/
@Controller
@RequestMapping("pc")
public class PcController {
    
	private Logger logger = Logger.getLogger(PcController.class);
	
	@Resource(name = "pcService")
	private PcService pcService;
	
	/** 
     * @Title: docLogin 
     * @Description: 医生在pc端登录
     * @author liuxiaoqin 
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="/docLogin",method = RequestMethod.POST)
    public void docLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try{
            String resPString = pcService.docLogin(request);
            GzipUtil.write(response, resPString);   
        }catch (Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(3);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("医生在pc端登录运行异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
	
	 /** 
	 * @Title: addPcMiniRecord 
	 * @Description: 医生在pc端发放一条mini记录
	 * @author liuxiaoqin 
	 * @param request
	 * @param response
	 * @throws IOException    
	 * @retrun void
	 */
	@RequestMapping(value="/addPcMiniRecord",method = RequestMethod.POST)
	public void addPcMiniRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try{
			String resPString = pcService.addPcMiniRecord(request);
			GzipUtil.write(response, resPString);	
		}catch (Exception e){
			ReturnResult re = new ReturnResult();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.ADD_DATA_EXCEPTION));
			logger.error("医生在pc端发放一条mini记录运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	 /** 
	 * @Title: deletePcMiniRecord 
	 * @Description: 删除未上传的mini记录 
	 * @author liuxiaoqin
	 * @param request
	 * @param response
	 * @throws IOException    
	 * @retrun void
	 */
	@RequestMapping(value="/deletePcMiniRecord",method=RequestMethod.POST)
	public void deletePcMiniRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try{
			String resPString = pcService.deletePcMiniRecord(request);
			GzipUtil.write(response, resPString);
		}catch (Exception e){
		    ReturnResult re = new ReturnResult();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.DELETE_DATA_EXCEPTION));
			logger.error("删除未上传的mini记录运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	 /** 
	 * @Title: updatePcMiniRecord 
	 * @Description: 修改mini记录 
	 * @author liuxiaoqin
	 * @param request
	 * @param response
	 * @throws IOException    
	 * @retrun void
	 */
	@RequestMapping(value="/updatePcMiniRecord",method=RequestMethod.POST)
	public void updatePcMiniRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = pcService.updatePcMiniRecord(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
		    ReturnResult re = new ReturnResult();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
			logger.error("更新mini上传时间运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}

	 /** 
	 * @Title: findPcMiniRecordByParam 
	 * @Description: 根据参数分页查询mini记录 
	 * @author liuxiaoqin
	 * @param request
	 * @param response
	 * @throws IOException    
	 * @retrun void
	 */
	@RequestMapping(value="/findPcMiniRecordByParam",method=RequestMethod.POST)
	public void findPcMiniRecordByParam(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try{
			String resPString = pcService.findPcMiniRecordByParam(request);
			GzipUtil.write(response, resPString);
		}catch (Exception e){
		    ReturnResult re = new ReturnResult();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("根据参数分页查询mini记录运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	 /** 
	 * @Title: findOmemByUserId 
	 * @Description: 根据用户id获取用户基本资料 
	 * @author liuxiaoqin
	 * @param request
	 * @param response
	 * @throws IOException    
	 * @retrun void
	 */
	@RequestMapping(value="/findOmemByUserId",method=RequestMethod.POST)
	public void findOmemByUserId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try{
			String resPString = pcService.findOmemByUserId(request);
			GzipUtil.write(response, resPString);
		}catch (Exception e){
		    ReturnResult re = new ReturnResult();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("根据用户id获取用户基本资料运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	/** 
     * @Title: findMemListByParam 
     * @Description: 根据医生id和参数分页查询会员列表 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="/findMemListByParam",method=RequestMethod.POST)
    public void findMemListByParam(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try{
            String resPString = pcService.findMemListByParam(request);
            GzipUtil.write(response, resPString);
        }catch (Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("根据医生id和参数分页查询会员列表运行异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }

    /** 
     * @Title: uploadMiniRecord 
     * @Description: 医生上传mini记录 
     * @author liuxiaoqin
     * @createDate 2016-01-29
     * @param request
     * @param response
     * @throws IOException    
     * @retrun void
     */
    @RequestMapping(value="/uploadMiniRecord",method=RequestMethod.POST)
    public void uploadMiniRecord(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try{
            String resPString = pcService.uploadMiniRecord(request);
            GzipUtil.write(response, resPString);
        }catch (Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.ADD_DATA_EXCEPTION));
            logger.error("医生上传mini文件运行异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
    
    
    /** 
     * @Title: checkDocOwnMem 
     * @Description: 根据医生id和会员id，检查会员是否属于医生管理 
     * @author liuxiaoqin
     * @createDate 2016-02-01
     * @param request
     * @param response
     * @return
     * @throws Exception    
     */
    @RequestMapping(value="/checkDocOwnMem",method=RequestMethod.POST)
    public void checkDocOwnMem(HttpServletRequest request,HttpServletResponse response) throws IOException {
        try{
            String resPString = pcService.checkDocOwnMem(request);
            GzipUtil.write(response, resPString);
        }catch (Exception e){
            ReturnResult re = new ReturnResult();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("验证用户是否属于医生管理运行异常:"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
}