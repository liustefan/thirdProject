package com.zkhk.filter;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;



import com.zkhk.task.DeleteJfreecharePng;
import com.zkhk.task.DeletePushMessage;
import com.zkhk.util.TimeUtil;



/**
 * 用于初始化
 * @author rjm
 *
 */

@SuppressWarnings("serial")
public class Initialization extends HttpServlet implements Servlet {


	public void init() {
	
                Timer timer=new Timer();
                Date date=getDay();
              //  timer.schedule(new DeletePushMessage(TimeUtil.formatDatetime2(date)) , 0, 1000 * 5);
//             
// 	      //定时删除图片，每天凌晨3点清除
// 	      timer.schedule(new DeleteJfreecharePng() , date, 1000 * 60 * 60 * 24);
//         
           timer.schedule(new DeletePushMessage(TimeUtil.formatDatetime2(addDay(date, -7))) , date, 1000 * 60 * 60 * 24);

}
	
	
	   // 增加或减少天数   
	 public Date addDay(Date date, int num) {  
	       Calendar startDT = Calendar.getInstance();  
	       startDT.setTime(date);  
	       startDT.add(Calendar.DAY_OF_MONTH, num);  
	       return startDT.getTime();  
	}  

    public Date getDay(){
	     Calendar calendar = Calendar.getInstance();  
	     calendar.set(Calendar.HOUR_OF_DAY, 2); //凌晨2点   
	     calendar.set(Calendar.MINUTE, 0);  
	     calendar.set(Calendar.SECOND, 0);  
	     Date date=calendar.getTime(); 
	    //第一次执行定时任务的时间   
	    //如果第一次执行定时任务的时间 小于当前的时间   
        //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。   
	        if (date.before(new Date())) {  
	            date = this.addDay(date, 1);  
	        } 
	        return date;
   }
	
}
