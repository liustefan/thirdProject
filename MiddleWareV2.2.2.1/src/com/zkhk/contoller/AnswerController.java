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
import com.zkhk.services.AnswerService;
import com.zkhk.util.GzipUtil;
import com.zkhk.util.SystemUtils;

@Controller
@RequestMapping("answer")
public class AnswerController {
	private Logger logger = Logger.getLogger(AnswerController.class);
	@Resource(name = "answerService")
	private AnswerService answerService;

	/**
	 * 获取会员答卷1
	 * 
	 * @param request
	 * @param response
	 *            Return 的json格式数据 state成功0，1表示查询不到数据，2表示出现异常
	 *            content表示成功以后的List<Ouai>对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("memAnswer")
	public void memAnswer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = answerService.findOuaiByParam(request);
			GzipUtil.write(response, resPString);	
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员答卷信息查询运行异常:"+e);
		    
			response.getWriter().write(JSON.toJSONString(re));
		
		}

	}
	
	

	/**
	 * 获取会员组合答卷信息
	 * 
	 * @param request
	 * @param response
	 * Return 的json格式数据 state成功0，1表示查询不到数据，2表示出现异常
	 * content表示成功以后的List<Ouai>对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("combAnswer")
	public void combAnswer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = answerService.findOcamByParam(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re=new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员组合答卷信息查询运行异常:"+e);
			 
			response.getWriter().write(JSON.toJSONString(re));
		
		
	
		}

	}
	
	/**
	 * 获取会员答卷2（获取组合答卷的级联答卷）
	 * 
	 * @param request
	 * @param response
	 *            Return 的json格式数据 state成功0，1表示查询不到数据，2表示出现异常
	 *            content表示成功以后的List<Ouai>对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("relationAnswer")
	public void relationAnswer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = answerService.findOuaiByParam2(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("会员答卷信息查询运行异常:"+e);
			 
			response.getWriter().write(JSON.toJSONString(re));
			
		
		}

	}

	/**
	 * 获取问卷详细信息
	 * 
	 * @param request
	 * @param response
	 *  Return 的json格式数据 state成功0，1表示查询不到数据，2表示出现异常
	 *  content表示成功以后的List<Omfq>对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("qust")
	public void qust(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = answerService.findOmfqByParam(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("查询问卷信息查询运行异常:"+e);
			
			response.getWriter().write(JSON.toJSONString(re));
			
		}

	}
	
	
	/**
	 * 获取问题答案
	 * 
	 * @param request
	 * @param response
	 *  Return 的json格式数据 state成功0，1表示查询不到数据，2表示出现异常
	 *  content表示成功以后的List<Omfq>对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("result")
	public void result(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = answerService.findResultByParam(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
			logger.error("问卷答案信息查询运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
		
		
		}

	}
	
	
	/**
	 * 上传问题答案
	 * 
	 * @param request
	 * @param response
	 *  Return 的json格式数据 state成功0，1表示查询不到数据，2表示出现异常 ，9没有操作权限 10传递参数有错误
	 *  content表示成功以后的List<Omfq>对象的json格式 ，不成功没数据
	 * @throws IOException 
	 */
	@RequestMapping("uploadResult")
	public void uploadResult(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			String resPString = answerService.saveResultByParam(request);
			GzipUtil.write(response, resPString);
		} catch (Exception e) {
			ReturnValue re = new ReturnValue();
			re.setState(2);
			re.setMessage(SystemUtils.getValue(Constants.ADD_DATA_EXCEPTION));
			logger.error("会员答卷保存运行异常:"+e);
			response.getWriter().write(JSON.toJSONString(re));
			
		}

	}
	
	/**
	  * @Description 获取会员单份答卷（已答和未答）
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	@RequestMapping(value="findMemSingleAnswer", method = RequestMethod.POST)
	public void findMemSingleAnswer(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
	    try 
	    {
            String resPString = answerService.findMemSingleAnswer(request);
            GzipUtil.write(response, resPString);   
        }
	    catch (Exception e)
	    {
            ReturnValue re = new ReturnValue();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("获取会员单份答卷异常："+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
	}
	
	/**
	  * @Description 获取会员单份问卷详细信息(会员未作答)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	@RequestMapping(value="findMemSingleQuestionInfo", method = RequestMethod.POST)
    public void findMemSingleQuestionInfo(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
	    try 
        {
            String resPString = answerService.findMemSingleQuestionInfo(request);
            GzipUtil.write(response, resPString);   
        }
        catch (Exception e)
        {
            ReturnValue re = new ReturnValue();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("获取会员单份问卷详细信息异常："+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
	
	/**
	  * @Description 获取会员单份答卷总得分，结论和审核建议(已审核)
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月12日
	*/
	@RequestMapping(value="findMemSingleAnswerSummary", method = RequestMethod.POST)
    public void findMemSingleAnswerSummary(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
	    try 
        {
            String resPString = answerService.findMemSingleAnswerSummary(request);
            GzipUtil.write(response, resPString);   
        }
        catch (Exception e)
        {
            ReturnValue re = new ReturnValue();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("获取会员单份答卷详细信息异常："+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
    }
	
	/**
	  * @Description 获取会员已答单份答卷的答案。
	  * @author liuxiaoqin
	  * @CreateDate 2015年10月13日
	*/
	@RequestMapping(value="findSingleAnswerHasSubmit", method = RequestMethod.POST)
	public void findSingleAnswerHasSubmit(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
	    try 
        {
            String resPString = answerService.findSingleAnswerHasSubmit(request);
            GzipUtil.write(response, resPString);   
        }
        catch (Exception e)
        {
            ReturnValue re = new ReturnValue();
            re.setState(2);
            re.setMessage(SystemUtils.getValue(Constants.GET_DATA_EXCEPTION));
            logger.error("获取会员单份答卷详细信息异常："+e);
            response.getWriter().write(JSON.toJSONString(re));
        }
	}
	
	/**
     * @Description 更新已审核的会员答卷为已读("0")。
     * @author liuxiaoqin
     * @CreateDate 2015年10月13日
   */
   @RequestMapping(value="updateAnserReadStatus", method = RequestMethod.POST)
   public void updateMemSingleAnswerHasApproved(HttpServletRequest request,HttpServletResponse response) throws Exception
   {
       try 
       {
           String resPString = answerService.updateMemSingleAnswerHasApproved(request);
           GzipUtil.write(response, resPString);   
       }
       catch (Exception e)
       {
           ReturnValue re = new ReturnValue();
           re.setState(2);
           re.setMessage(SystemUtils.getValue(Constants.MODIFY_DATA_EXCEPTION));
           logger.error("更新已审核的会员答卷为已读异常："+e);
           response.getWriter().write(JSON.toJSONString(re));
       }
   }
}
