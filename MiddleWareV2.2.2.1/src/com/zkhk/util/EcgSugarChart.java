package com.zkhk.util;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;

import java.util.Random;

import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.ui.TextAnchor;

public class EcgSugarChart {

	public EcgSugarChart() {

		this.createChart();

	}

	// 获得数据集 （这里的数据是为了测试我随便写的一个自动生成数据的例子）

	public static DefaultCategoryDataset createDataset() {

		DefaultCategoryDataset linedataset = new DefaultCategoryDataset();

		// 曲线名称

		String series = "心电值"; // series指的就是报表里的那条数据线

		// 因此 对数据线的相关设置就需要联系到serise

		// 比如说setSeriesPaint 设置数据线的颜色

		// 横轴名称(列名称)

		String[] time = new String[30];

		String[] timeValue = { "6-1日", "6-2日", "6-3日", "6-4日", "6-5日", "6-6日",

		"6-7日", "6-8日", "6-9日", "6-10日", "6-11日", "6-12日", "6-13日",

		"6-14日", "6-15日","6-1日", "6-2日", "6-3日", "6-4日", "6-5日", "6-6日",

		"6-7日", "6-8日", "6-9日", "6-10日", "6-11日", "6-12日", "6-13日",

		"6-14日", "6-15日" };

		for (int i = 0; i < 30; i++) {

			time[i] = timeValue[i];

		}

		// 随机添加数据值

		for (int i = 0; i <1000; i++) {

			int max=256;
	        int min=-128;
	        Random random = new Random();

	        int s = random.nextInt(max)%(max-min+1) + min;


			linedataset.addValue(s, // 值

					series, // 哪条数据线

					i+""); // 对应的横轴

		}

		return linedataset;

	}

	// 生成图标对象JFreeChart

	/*
	 * 
	 * 整个大的框架属于JFreeChart
	 * 
	 * 坐标轴里的属于 Plot 其常用子类有：CategoryPlot, MultiplePiePlot, PiePlot , XYPlot
	 * 
	 * 
	 * 
	 * **
	 */

	public static JFreeChart createChart() {

		try {

			// 定义图标对象
			JFreeChart chart = ChartFactory.createLineChart(null,// 报表题目，字符串类型

					"心电值", // 横轴

					"采集时间", // 纵轴

					createDataset(), // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					true, // 不用生成工具

					false // 生成URL地址

					);

			// 整个大的框架属于chart 可以设置chart的背景颜色

			// 生成图形

			CategoryPlot plot = chart.getCategoryPlot();

			// 图像属性部分

			plot.setBackgroundPaint(Color.white);

			plot.setDomainGridlinesVisible(true); // 设置背景网格线是否可见

			plot.setDomainGridlinePaint(Color.red); // 设置背景网格线颜色

			plot.setRangeGridlinePaint(Color.GRAY);

			plot.setNoDataMessage("没有数据");// 没有数据时显示的文字说明。

			// 数据轴属性部分
			

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			rangeAxis.setAutoRangeIncludesZero(true); // 自动生成

			rangeAxis.setUpperMargin(0.20);

			rangeAxis.setLabelAngle(Math.PI / 2.0);

			rangeAxis.setAutoRange(true);

			// 数据渲染部分 主要是对折线做操作

			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot

			.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.blue); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBaseItemLabelsVisible(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));

			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

			/*
			 * 
			 * 这里的StandardCategoryItemLabelGenerator()我想强调下：当时这个地*方被搅得头很晕，Standard
			 * **ItemLabelGenerator是通用的 因为我创建*的是CategoryPlot 所以很多设置都是Category相关
			 * 
			 * 而XYPlot 对应的则是 ： StandardXYItemLabelGenerator
			 */

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); // 设置提示折点数据形状

			plot.setRenderer(renderer);

			// 区域渲染部分

//			double lowpress = 4.5;
//
//			double uperpress = 8; // 设定正常心电值的范围
//
//			IntervalMarker inter = new IntervalMarker(lowpress, uperpress);
//
//			inter.setLabelOffsetType(LengthAdjustmentType.EXPAND); // 范围调整——扩张
//
//			inter.setPaint(Color.LIGHT_GRAY);// 域顏色
//
//			inter.setLabelFont(new Font("SansSerif", 10, 1));
//
//			inter.setLabelPaint(Color.RED);
//
//			//inter.setLabel("正常心电值范围"); // 设定区域说明文字
//
//			plot.addRangeMarker(inter, Layer.BACKGROUND); // 添加mark到图形
//															// BACKGROUND使得数据折线在区域的前端
            
//			// 创建文件输出流

			File fos_jpg = new File("D://apache-tomcat-7.0.57//webapps//MiddleWare//temp//line_20150417191829.jpeg ");

			// 输出到哪个输出流

			ChartUtilities.saveChartAsJPEG(fos_jpg, chart, // 统计图表对象
			                                         700, // 宽
					                                 450 // 高

				);
			return chart;

		} catch (Exception e) {
			
		}
		return null;

	}

	//设置X轴部分基本信息
	private static void setXLineBaseInfo(CategoryAxis domainAxis){

		Font xFont = new Font("宋体", Font.BOLD, 16);    		 //X轴字体
		Font xLabelFont = new Font("宋体", Font.BOLD, 16);    //X轴标题字体
		domainAxis.setLabelFont(xFont); // 设置横轴字体  
		domainAxis.setTickLabelFont(xLabelFont);// 设置坐标轴标尺值字体  
		//domainAxis.setTickLabelPaint(Color.gray);
		domainAxis.setLowerMargin(0.01);// 左边距 边框距离  
		domainAxis.setUpperMargin(0.01);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。  
		//domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);  //设置字体倾斜度
		domainAxis.setCategoryLabelPositionOffset(10);// 图表横轴与标签的距离(10像素)  
		//domainAxis.setCategoryMargin(0.8);// 横轴标签之间的距离20%  
		
	}
	//设置Y轴部分基本信息
	private static void setYLineBaseInfo(ValueAxis rangeAxis){
		Font yFont = new Font("宋体", Font.BOLD, 16);    		 //Y轴字体
		Font yLabelFont = new Font("宋体", Font.BOLD, 16);    //Y轴标题字体
		rangeAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)   
        rangeAxis.setUpperMargin(0.1);// 设置柱体与图片边框下相距
        rangeAxis.setTickLabelFont(yFont); // 设置Y坐标上文子字体          
        rangeAxis.setLabelFont(yLabelFont);  // 设置Y轴标题字体
        rangeAxis.setTickMarkStroke(new BasicStroke(1.0f));     // 设置坐标标记大小  
        rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色  
        //rangeAxis.setLabelAngle(3.0);    //调整字体方向
		
	}
	
	
	// 测试类

	public static void main(String[] args) {

		EcgSugarChart my = new EcgSugarChart();
		   //这里为了方便观察结果，将chart放在ChartFrame中显示
		 		
	}

}
