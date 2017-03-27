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
import com.zkhk.entity.ReturnValue;
import com.zkhk.services.MsgManageService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

@Controller
@RequestMapping("message")
public class MessageController {

	
	private Logger logger = Logger.getLogger(MessageController.class);
	@Resource(name = "msgManageService")
	private MsgManageService msgManageService;

	/**
	 * 添加会员推送消息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("saveMessage")
	public void putMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = msgManageService.saveMessageByParam(request);
			GzipUtil.write(response, resPString);	
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(1);
			re.setMessage(SystemUtils.getValue(Constants.ADD_DATA_EXCEPTION));
			logger.error("推送信息保存异常"+e);
			response.getWriter().write(JSON.toJSONString(re));
		
		}

	}
	
	/**
	  * @Description 查询所有消息列表
	  * @author liuxiaoqin
	  * @CreateDate 2015年7月9日
	*/
	@RequestMapping("findMsgList")
	public void findMsgList(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		try{
			String resPString = msgManageService.findMsgListByParam(request);
			GzipUtil.write(response, resPString);
		}
		catch(Exception e){
			ReturnValue re = new ReturnValue();
			re.setState(1);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("查询未读消息列表异常!"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	/**
	  * @Description 查看某条消息内容
	  * @author liuxiaoqin
	  * @CreateDate 2015年7月9日
	*/
	@RequestMapping("findMsgByMsgId")
	public void findMsgByMsgId(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		try
		{
			String resPString = msgManageService.findMsgById(request);
			GzipUtil.write(response, resPString);
		}
		catch(Exception e)
		{
			ReturnValue re = new ReturnValue();
			re.setState(1);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("修改该条信息异常!"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	/**
	  * @Description 删除消息
	  * @author liuxiaoqin
	  * @CreateDate 2015年7月9日
	*/
	@RequestMapping("deleteMsg")
	public void deleteMsg(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		try
		{
			String resPString = msgManageService.updateMsgIsInvalid(request);
			GzipUtil.write(response, resPString);
		}
		catch(Exception e)
		{
			ReturnValue re = new ReturnValue();
			re.setState(1);
			re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
			logger.error("删除该条信息异常!"+e);
			response.getWriter().write(JSON.toJSONString(re));
		}
	}
	
	/**
     * @Description 查询新的所有未读信息
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
     */
	@RequestMapping(value="/findNewMsgList",method=RequestMethod.POST)
    public void findNewMsgList(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        try
        {
            String resPString = msgManageService.findNewMsgList(request);
            GzipUtil.write(response, resPString);
        }
        catch(Exception e)
        {
            ReturnValue re = new ReturnValue();
            re.setState(1);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("读取新信息异常!"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
   
    /**
     * @Description 读取某条未读信息
     * @author liuxiaoqin
     * @CreateDate 2015年9月16日
     */
    @RequestMapping(value="/findNewMsgByMsgId",method=RequestMethod.POST)
    public void findNewMsgByMsgId(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        try
        {
            String resPString = msgManageService.findNewMsgByMsgId(request);
            GzipUtil.write(response, resPString);
        }
        catch(Exception e)
        {
            ReturnValue re = new ReturnValue();
            re.setState(1);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("读取新信息异常!"+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
	
}
