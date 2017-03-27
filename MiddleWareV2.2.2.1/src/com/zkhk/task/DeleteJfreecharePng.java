package com.zkhk.task;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 定时清空jfreechare产生的缓存图片信息（10分钟前的所有图片）
 * 
 * @author Dell
 * 
 */
public class DeleteJfreecharePng extends TimerTask {
	private static String path;
	static{
		path=DeleteJfreecharePng.class.getResource("/").getPath();
		int lastpath=path.lastIndexOf("WEB-INF/");
		path=path.substring(0,lastpath)+"temp";
	
   }
	@Override
	public void run() {
		File dir = new File(path);
		File[] lst = dir.listFiles();
		for (File f : lst) {
			if (new Date().getTime() - f.lastModified() > 10 * 60 * 1000) {
				f.delete();
			}
		}

	}

	public static void main(String[] args) {
		  Timer timer=new Timer();      
 	      //定时清除添加信息信息，每天凌晨3点清除
 	      timer.schedule(new DeleteJfreecharePng() , 0, 1000 * 5);

	}
		
	
}
