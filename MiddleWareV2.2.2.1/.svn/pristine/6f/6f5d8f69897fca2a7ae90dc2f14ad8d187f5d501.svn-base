package com.zkhk.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.PieDataset;

import org.jfree.data.xy.XYDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.ui.TextAnchor;



public final class JfreeChartUtil {

	// 设置标题
	private static TextTitle setChartTile(String title) {
		Font titleFont = new Font("宋体", Font.PLAIN, 16); // 标题字体
		TextTitle textTitle = new TextTitle(title, titleFont);
		textTitle.setPaint(Color.BLUE);
		return textTitle;
	}

	// 设置JFreeChart部分基本信息
	private static void setJFreeChartBaseInfo(JFreeChart chart) {
		Font legendTitleFont = new Font("宋体", Font.BOLD,15); // 报表底部标题字体
		chart.getLegend().setItemFont(legendTitleFont);
		chart.getLegend().setBorder(0, 0, 0, 0);
		//chart.getLegend().S
		//chart.getLegend().
		
		// chart.setBorderPaint(Color.red); // 设置报表边框颜色
		// chart.setBorderVisible(true);
		// 设置底部标题 字体
		 //chart.getLegend().setItemFont(legendTitleFont);
		chart.getLegend().setItemPaint(Color.black);
		//chart.getLegend().
		// chart.setBackgroundPaint(Color.GREEN);// 设置背景色
	}

	// 设置图表区域基本信息
	private static void setPlotInfo(Plot plot) {
		// plot.setBackgroundPaint(Color.BLACK); //设置网格背景色
		// 设置柱的透明度
		// plot.setForegroundAlpha(1.0f);
		// 背景色 透明度
		// plot.setBackgroundAlpha(0.5f);
		// 无数据展现
		Font noDataFont = new Font("宋体", Font.BOLD, 40); // 饼图字体
	    plot.setNoDataMessage("无对应的数据，请重新查询！");
		plot.setNoDataMessageFont(noDataFont);// 字体的大小
		plot.setNoDataMessagePaint(Color.RED);// 字体颜色
		// plot.setBackgroundPaint(Color.BLACK); //设置网格背景色
		// plot.setDomainGridlinePaint(Color.white); //设置垂直网格线颜色
		// plot.setDomainGridlinesVisible(true); // 设置垂直网格线是否可见
		// plot.setRangeGridlinePaint(Color.red); // 设置水平网格线颜色
		// 指定饼图轮廓线的颜色
		// plot.setBaseSectionOutlinePaint(Color.green);
		// plot.setBaseSectionPaint(Color.green);

	}

	// 设置X轴部分基本信息
	private static void setXLineBaseInfo(CategoryAxis domainAxis) {
		// Font xFont = new Font("宋体", Font.BOLD, 16); // X轴字体
		// Font xLabelFont = new Font("宋体", Font.BOLD, 16); // X轴标题字体
		// domainAxis.setLabelFont(xFont); // 设置横轴字体
		// domainAxis.setTickLabelFont(xLabelFont);// 设置坐标轴标尺值字体
		//
		// // domainAxis.setTickLabelPaint(Color.gray);
		// domainAxis.set
		domainAxis.setVisible(false);

		domainAxis.setLowerMargin(0.001);// 左边距 边框距离
		domainAxis.setUpperMargin(0.001);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
		// domainAxis.setAxisLineStroke(stroke);
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// // // //设置字体倾斜度
		domainAxis.setCategoryLabelPositionOffset(20);// 图表横轴与标签的距离(10像素)
		domainAxis.setCategoryMargin(0);// 横轴标签之间的距离20%

	}

	// 设置Y轴部分基本信息
	private static void setYLineBaseInfo(ValueAxis rangeAxis) {
		// rangeAxis.setVisible(false);//设置坐标联可见
		Font yFont = new Font("宋体", Font.BOLD, 16); // Y轴字体
		Font yLabelFont = new Font("宋体", Font.BOLD, 16); // Y轴标题字体
		rangeAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)
		rangeAxis.setUpperMargin(0.1);// 设置柱体与图片边框下相距
		rangeAxis.setTickLabelFont(yFont); // 设置Y坐标上文子字体
		rangeAxis.setLabelFont(yLabelFont); // 设置Y轴标题字体
		rangeAxis.setTickMarkStroke(new BasicStroke(1.0f)); // 设置坐标标记大小
		rangeAxis.setTickMarkPaint(Color.BLACK); // 设置坐标标记颜色
		// // rangeAxis.setLabelAngle(3.0); //调整字体方向

	}

	/**
	 * 数据库结果转报表数据类型（柱状图、折线图）
	 * 
	 * @param data
	 *            List<Map<String,String>> data Map 对象必须包含 <KEY,VALUE>对
	 *            <"NUM",数量> <"SERIES_INDEX",子维度> <"CATEGORY",对象>
	 * @return CategoryDataset 报表数据
	 */
	private static CategoryDataset converListToCategoryDataset(
			List<Map<String, String>> data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Map<String, String> item : data) {
			dataset.addValue(Double.valueOf(item.get("NUM")),
					item.get("SERIES_INDEX"), item.get("CATEGORY"));
		}
		return dataset;
	}

	/**
	 * 数据库结果转报表数据类型（饼图）
	 * 
	 * @param data
	 *            List<Map<String,String>> data Map 对象必须包含 <KEY,VALUE>对
	 *            <"NUM",数量> <"OBJ",对象>
	 * @return PieDataset 报表数据
	 */
	private static PieDataset converListToPieDataset(
			List<Map<String, String>> data) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map<String, String> item : data) {
			dataset.setValue(item.get("COND"), Double.valueOf(item.get("OBJ")));
		}
		return dataset;
	}

	/**
	 * 生成柱状图
	 * 
	 * @param dataset
	 *            报表数据
	 * @param title
	 *            报表标题
	 * @param xtitle
	 *            X轴描述
	 * @param ytitle
	 *            Y轴描述
	 * @param is3D
	 *            是否3D效果
	 * @param url
	 *            连接路径
	 * @return JFreeChart
	 */
	public static JFreeChart createBarChart(CategoryDataset dataset,
			String title, String xtitle, String ytitle, boolean is3D, String url) {
		JFreeChart chart = ChartFactory.createBarChart3D(title,// 图表标题
				xtitle,// 目录轴的显示标签(x轴)
				ytitle,// 数值轴的显示标签（y轴）
				dataset, PlotOrientation.VERTICAL,// 图表方向：水平、垂直
				true,// 是否显示图例(对于简单的柱状图必须是false)
				true,// 是否tooltip
				true// 是否生成URL链接
				);
		chart.setTitle(setChartTile(title)); // 设置标题
		setJFreeChartBaseInfo(chart);
		CategoryPlot plot = chart.getCategoryPlot();// 获得图表区域对象
		setPlotInfo(plot);
		plot.setRangeGridlinesVisible(true); // 设置水平网格线是否可见
		// 设置柱的透明度
		plot.setForegroundAlpha(1.0f);
		// 背景色 透明度
		plot.setBackgroundAlpha(0.5f);
		// x轴设置
		CategoryAxis domainAxis = plot.getDomainAxis();
		setXLineBaseInfo(domainAxis); // X轴基本信息
		// 设定柱子的属性 （Y轴）
		ValueAxis rangeAxis = plot.getRangeAxis();
		setYLineBaseInfo(rangeAxis); // X轴基本信息

		// 数据精度
		// NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();
		// numberAxis.setAutoRangeStickyZero(true);

		// 设置图表的颜色
		BarRenderer renderer;
		if (is3D) {
			renderer = new BarRenderer3D();
		} else {
			renderer = new BarRenderer();
		}
		// 设置鼠标移动展现内容
		// renderer.setBaseToolTipGenerator(new
		// StandardCategoryToolTipGenerator("{2}",NumberFormat.getNumberInstance()));
		// 设置Url
		if (null != url && !"".equals(url)) {
			renderer.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
					url));
		}
		// 显示每个柱的数值，并修改该数值的字体属性
		renderer.setIncludeBaseInRange(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
				"{0}={2}", NumberFormat.getNumberInstance()));
		renderer.setBaseItemLabelsVisible(true);

		// 设置柱子上显示的数据旋转90度,最后一个参数为旋转的角度值/3.14
		ItemLabelPosition itemLabelPosition = new ItemLabelPosition(
				ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT,
				TextAnchor.CENTER_RIGHT, -1.57D);

		renderer.setBasePositiveItemLabelPosition(itemLabelPosition);
		renderer.setBaseNegativeItemLabelPosition(itemLabelPosition);

		// 设置柱体字在柱体外显示,当某个柱子比例太小,自动在柱外展现信息
		ItemLabelPosition itemLabelPositionFallback = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT,
				TextAnchor.HALF_ASCENT_LEFT, -1.57D);
		renderer.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);
		renderer.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);

		// java.lang.String labelFormat:"{0}={1}({2})":会显示成：apple=120(5%)
		// java.text.NumberFormat类有三个方法可以产生下列数据的标准格式化器：
		// 数字：NumberFormat.getNumberInstance();
		// 货币：NumberFormat.getCurrencyInstance();
		// 百分数：NumberFormat.getPercentInstance();

		// 修改柱体颜色,设置柱体颜色不同
		renderer.setBaseOutlinePaint(Color.red);
		renderer.setSeriesPaint(0, new Color(0, 255, 255));// 计划柱子的颜色为青色
		renderer.setSeriesOutlinePaint(0, Color.BLACK);// 边框为黑色
		renderer.setSeriesPaint(1, new Color(0, 255, 0));// 实报柱子的颜色为绿色
		renderer.setSeriesOutlinePaint(1, Color.red);// 边框为红色
		// renderer.setItemMargin(-4);// 组内柱子间隔为组宽的10% 可以 调整柱子大小，本身柱子被其他柱子遮盖了
		// renderer.setMaximumBarWidth(1.0);
		// .setMinimumBarLength();
		// 设置底部 LegendLabel内容
		renderer.setLegendItemLabelGenerator(new StandardCategorySeriesLabelGenerator(
				"{0}"));
		plot.setRenderer(renderer);// 设置Renderer

		// 设置纵横坐标的显示位置
		// plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);//
		// 学校显示在下端(柱子竖直)或左侧(柱子水平)
		// plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT); //
		// 人数显示在下端(柱子水平)或左侧(柱子竖直)

		return chart;
	}

	/**
	 * 生成饼图
	 * 
	 * @param dataset
	 *            报表数据
	 * @param title
	 *            报表标题
	 * @param is3D
	 *            是否3D效果
	 * @param url
	 *            连接路径
	 * @return JFreeChart
	 */
	public static JFreeChart createPieChart(PieDataset dataset, String title,
			boolean is3D, String url) {

		PiePlot plot = null;
		if (is3D) {
			plot = new PiePlot3D(dataset);// 3D饼图
		} else {
			plot = new PiePlot(dataset);
		}
		if (null != url && !"".equals(url)) {
			plot.setURLGenerator(new StandardPieURLGenerator(url));// 设定热区超链接
		}
		plot.setPieIndex(1);
		setPlotInfo(plot);
		JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
				plot, true);
		setJFreeChartBaseInfo(chart);
		// 设置背景色为白色
		// chart.setBackgroundPaint(Color.green);
		// 设置鼠标移动显示信息
		plot.setToolTipGenerator(new StandardPieToolTipGenerator(
				"{0}({1},{2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));

		// 图片中显示百分比:默认方式
		// plot.setLabelGenerator(new
		// StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1}({2})", NumberFormat.getNumberInstance(), // "{0}={1}({2})"
				new DecimalFormat("0.00%")));
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}"));
		// 指定图片的透明度(0.0-1.0)
		plot.setForegroundAlpha(0.8f);
		// 指定显示的饼图上圆形(true)还椭圆形(false)
		plot.setCircular(true);
		// plot.setBackgroundPaint(Color.gray); //设置背景颜色
		// 设置第一个 饼块section 的开始位置，默认是12点钟方向
		plot.setStartAngle(90);
		Font pieLabelFont = new Font("宋体", Font.BOLD, 16); // 饼图字体
		plot.setLabelFont(pieLabelFont);
		return chart;
	}

	/**
	 * 生成折线图
	 * 
	 * @param dataset
	 *            报表数据
	 * @param title
	 *            报表标题
	 * @param xtitle
	 *            X轴描述
	 * @param ytitle
	 *            Y轴描述
	 * @param is3D
	 *            是否3D效果
	 * @return JFreeChart
	 */
	public static JFreeChart createXYLineChart(DefaultCategoryDataset dataSet,
			String title, String xtitle, String ytitle, boolean is3D) {
		JFreeChart chart = ChartFactory.createLineChart(title, xtitle, ytitle,
				dataSet, PlotOrientation.VERTICAL, // 绘制方向
				true, // 显示图例
				true, // 采用标准生成器
				false // 是否生成超链接
				);
		if (is3D) {
			chart = ChartFactory.createLineChart3D(title, xtitle, ytitle,
					dataSet, PlotOrientation.VERTICAL, // 绘制方向
					true, // 显示图例
					true, // 采用标准生成器
					false // 是否生成超链接
					);
		}
		// chart.setTitle(setChartTile(title)); // 设置标题

		setJFreeChartBaseInfo(chart); // 设置chart 基本信息
		// 获取绘图区对象
		CategoryPlot plot = chart.getCategoryPlot();
		setPlotInfo(plot);

		plot.setBackgroundPaint(Color.white);

		plot.setDomainGridlinesVisible(true); // 设置背景网格线是否可见

		plot.setRangeGridlinesVisible(true);// 数据轴网格可见

		plot.setDomainGridlinePaint(Color.red); // 设置背景网格线颜色

		// 数据轴属性部分

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		rangeAxis.setAutoRangeIncludesZero(true); // 自动生成

		rangeAxis.setUpperMargin(0.20);

		rangeAxis.setLabelAngle(Math.PI / 2.0);

		rangeAxis.setAutoRange(true);

		// // X轴设置
		CategoryAxis domainAxis = plot.getDomainAxis();
		setXLineBaseInfo(domainAxis); // 设置X轴基本信息
		domainAxis.setMaximumCategoryLabelLines(1);
		//
		// // Y轴设置
		// NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		//
		// rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		//
		// rangeAxis.setAutoRangeIncludesZero(true); // 自动生成轴坐标
		//
		// rangeAxis.setUpperMargin(0.50);
		//
		// rangeAxis.setLabelAngle(Math.PI / 2.0);
		//
		// rangeAxis.setAutoRange(true);
		//
		setYLineBaseInfo(rangeAxis); // 设置Y轴基本信息

		// 获取折线对象
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();

		renderer.setBaseItemLabelsVisible(false);// 设置折点是否显示数据

		renderer.setSeriesPaint(0, Color.blue); // 设置折线的颜色

		renderer.setBaseShapesFilled(true);
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

		// renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); //
		// 设置提示折点数据形状
		return chart;
	}

	/**
	 * 生成 仪表图
	 * 
	 * @param dataset
	 *            报表数据
	 * @param title
	 *            报表标题
	 * @param xtitle
	 *            X轴描述
	 * @param ytitle
	 *            Y轴描述
	 * @param is3D
	 *            是否3D效果
	 * @return JFreeChart
	 */
	public static String createMeterChart(HttpSession session, PrintWriter pw)
			throws Exception {
		DefaultValueDataset dataset = new DefaultValueDataset();
		dataset = new DefaultValueDataset(40);
		DialPlot dialplot = new DialPlot();
		dialplot.setDataset(dataset);
		// 开始设置显示框架结构
		StandardDialFrame standarddialframe = new StandardDialFrame();
		standarddialframe.setBackgroundPaint(Color.black);
		standarddialframe.setForegroundPaint(Color.darkGray);// 圆边的颜色
		dialplot.setDialFrame(standarddialframe);
		// 结束设置显示框架结构
		GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(
				255, 255, 255), new Point(), new Color(170, 170, 220));
		DialBackground dialbackground = new DialBackground(gradientpaint);
		dialbackground
				.setGradientPaintTransformer(new StandardGradientPaintTransformer(
						GradientPaintTransformType.VERTICAL));
		dialplot.setBackground(dialbackground);
		// 设置显示在表盘中央位置的信息
		DialTextAnnotation dialtextannotation = new DialTextAnnotation("成本执行");
		dialtextannotation.setFont(new Font("Dialog", 17, 17));
		dialtextannotation.setRadius(0.6D);// 字体距离圆心的距离
		dialplot.addLayer(dialtextannotation);
		DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
		dialplot.addLayer(dialvalueindicator);
		// 根据表盘的直径大小（0.88），设置总刻度范围
		StandardDialScale standarddialscale = new StandardDialScale(0.0D,
				100.0D, -120.0D, -300.0D, 10D, 9);
		standarddialscale.setTickRadius(0.9D);
		standarddialscale.setTickLabelOffset(0.1D);// 显示数字 距离圆边的距离
		standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
		// 主意是 dialplot.addScale（）不是dialplot.addLayer（）
		dialplot.addScale(0, standarddialscale);
		// 设置刻度范围（红色）
		StandardDialRange standarddialrange = new StandardDialRange(0D, 50D,
				Color.green);
		standarddialrange.setInnerRadius(0.6D);
		standarddialrange.setOuterRadius(0.62D);
		dialplot.addLayer(standarddialrange);
		// 设置刻度范围（橘黄色）
		StandardDialRange standarddialrange1 = new StandardDialRange(50D, 80D,
				Color.orange);
		standarddialrange1.setInnerRadius(0.6D);// 半径返回 两条线
		standarddialrange1.setOuterRadius(0.62D);
		dialplot.addLayer(standarddialrange1);
		// 设置刻度范围（绿色）
		StandardDialRange standarddialrange2 = new StandardDialRange(80D, 100D,
				Color.red);
		standarddialrange2.setInnerRadius(0.6D);
		standarddialrange2.setOuterRadius(0.62D);
		dialplot.addLayer(standarddialrange2);
		// 设置指针
		org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer();
		dialplot.addLayer(pointer);
		// 实例化DialCap
		DialCap dialcap = new DialCap();
		dialcap.setRadius(0.1D);// 指针中心圆的大小
		dialplot.setCap(dialcap);
		// 生成chart对象
		JFreeChart jfreechart = new JFreeChart(dialplot);

		// 设置标题
		jfreechart.setTitle("目标成本执行情况分析");

		String filename = ServletUtilities.saveChartAsPNG(jfreechart, 400, 400,
				session);

		return filename;
	}

	public static JFreeChart createLineChart(DefaultCategoryDataset dataSet,
			String title, String xtitle, String ytitle, boolean is3D) {
		JFreeChart chart = ChartFactory.createLineChart(title, xtitle, ytitle,
				dataSet, PlotOrientation.VERTICAL, // 绘制方向
				true, // 显示图例
				true, // 采用标准生成器
				false // 是否生成超链接
				);

		// 整个大的框架属于chart 可以设置chart的背景颜色

		chart.setBorderVisible(false);
		// 生成图形

		CategoryPlot plot = chart.getCategoryPlot();

		// 图像属性部分

		// plot.setDomainGridlineStroke(new BasicStroke());

		plot.setRangeGridlineStroke(new BasicStroke());

		plot.setBackgroundPaint(Color.white);// 设置背景颜色

		plot.setDomainGridlinesVisible(true); // 设置背景网格线是否可见

		plot.setDomainGridlinePaint(Color.red); // 设置横网格线颜色

		plot.setRangeGridlinePaint(Color.red);// 设置垂直网格线颜色

		plot.setNoDataMessage("没有数据");// 没有数据时显示的文字说明。

		// 数据轴属性部分

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		// rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		rangeAxis.setAutoRangeIncludesZero(false); // 自动生成
		rangeAxis.setAutoTickUnitSelection(false);
		double unit = 160d;// 刻度的长度
		NumberTickUnit ntu = new NumberTickUnit(unit);
		rangeAxis.setTickUnit(ntu);

		// rangeAxis.setUpperMargin(0.20);
		//
		// rangeAxis.setLabelAngle(Math.PI / 2.0);

		rangeAxis.setAutoRange(true);
		rangeAxis.setVisible(false);

		// // X轴设置
		// DateAxis axis=plot.getDomainAxis();
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setVisible(false);

		domainAxis.setLowerMargin(0.001);// 左边距 边框距离
		domainAxis.setUpperMargin(0.001);// 右边距 边框距离,防止最后边的一个数据靠近了坐标
		// setXLineBaseInfo(domainAxis); // 设置X轴基本信息
		// domainAxis.setMinorTickMarkInsideLength(16);//设置最小刻度
		domainAxis.setMinorTickMarksVisible(false);

		// 数据渲染部分 主要是对折线做操作

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();

		renderer.setBaseItemLabelsVisible(false);

		renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色

		renderer.setBaseShapesFilled(true);

		renderer.setBaseItemLabelsVisible(false);// 设置折点是否显示数据

		// renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
		//
		// ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));
		//
		// renderer.setBaseItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator());
		// renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); //
		// 设置提示折点数据形状

		plot.setRenderer(renderer);

		return chart;

	}

	/**
	 * 画心电图
	 * 
	 * @param dataSet
	 * @param title
	 * @param xtitle
	 * @param ytitle
	 * @param is3D
	 * @param fs
	 * @return
	 */
	public static JFreeChart createXYChart(XYDataset dataSet, String title,
			String xtitle, String ytitle, boolean is3D, int fs) {

		try {

			// 定义图标对象
			JFreeChart chart = ChartFactory.createXYLineChart(title,// 报表题目，字符串类型

					xtitle, // 横轴

					ytitle, // 纵轴

					dataSet, // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					true, // 不用生成工具

					false // 生成URL地址

					);
         
			chart.setBorderVisible(false);
			// 生成图形
			setJFreeChartBaseInfo(chart);
			if(dataSet.getItemCount(0)<1){
				chart.getLegend().setVisible(false);
			}
			XYPlot plot = chart.getXYPlot();

			 plot.setDomainGridlineStroke(new BasicStroke());
			
			 plot.setRangeGridlineStroke(new BasicStroke());
			// 图像属性部分

			plot.setBackgroundPaint(Color.white);
			plot.setDomainGridlinesVisible(true); // 设置背景网格线是否可见
			plot.setRangeGridlinesVisible(true);
			

			plot.setDomainGridlinePaint(new Color(255, 192, 203)); // 设置背景网格线颜色

			plot.setRangeGridlinePaint(new Color(255, 192, 203));

			// plot.setNoDataMessage("没有数据");// 没有数据时显示的文字说明。
			setPlotInfo(plot);

			// 数据轴属性部分

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

			rangeAxis.setAutoRangeIncludesZero(false); // 自动生成
			// rangeAxis .setAutoTickUnitSelection(true);
			double unit = 20d;// 刻度的长度
			NumberTickUnit ntu = new NumberTickUnit(unit);
			rangeAxis.setTickUnit(ntu);
			rangeAxis.setVisible(false);
			

			//
			// ValueAxis valueAxis = plot.getDomainAxis();
			// valueAxis.setAutoRange(false);
			// valueAxis.setFixedAutoRange(6*fs);
			// valueAxis.setAutoRangeMinimumSize(20);
			// valueAxis.

			// X轴设置
			NumberAxis axis = new NumberAxis();
			NumberTickUnit ntu2 = new NumberTickUnit(15d);
			axis.setTickUnit(ntu2);
			axis.setVisible(false);
			axis.setLowerMargin(0);
			axis.setUpperMargin(0);
//			axis.setLabel(title);
//			axis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
//			 if(fs>200){
//			 axis.setFixedAutoRange(6*fs);
//			 }
			
			plot.setDomainAxis(axis);
			// 数据渲染部分 主要是对折线做操作
//			String imgPath = "C:\\Users\\bit\\Desktop\\getEcgInfo.png";  
//			Image image = ImageIO.read(new FileInputStream(imgPath)); 
//            plot.setBackgroundImage(image);
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
					.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); // 设置提示折点数据形状

			plot.setRenderer(renderer);
			return chart;

		} catch (Exception e) {

		}
		return null;

	}

	/**
	 * 画脉搏图
	 * 
	 * @param xyDataset
	 * @param timeString
	 * @param object
	 * @param object2
	 * @param b
	 * @param fs
	 * @return
	 */
	public static JFreeChart createPPgChart(XYDataset xyDataset, String title,
			String xtitle, String ytitle, boolean b, int fs) {

		try {

			// 定义图标对象
			JFreeChart chart = ChartFactory.createXYLineChart(title,// 报表题目，字符串类型

					xtitle, // 横轴

					ytitle, // 纵轴

					xyDataset, // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					true, // 不用生成工具

					false // 生成URL地址

					);

			chart.setBorderVisible(false);
			// 生成图形
			setJFreeChartBaseInfo(chart);
			if(xyDataset.getItemCount(0)<1){
				chart.getLegend().setVisible(false);
			}
			XYPlot plot = chart.getXYPlot();

			// 图像属性部分

			plot.setBackgroundPaint(new Color(239, 255, 239));
			plot.setDomainGridlinesVisible(false); // 设置背景网格线是否可见
			plot.setRangeGridlinesVisible(false);
			setPlotInfo(plot);

			// 数据轴属性部分

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setAutoRangeIncludesZero(false); // 自动生成
			double unit = 22d;// 刻度的长度
			NumberTickUnit ntu = new NumberTickUnit(unit);
			rangeAxis.setTickUnit(ntu);
			rangeAxis.setUpperBound(88);
			rangeAxis.setLowerBound(0.1);
			// Random

			// X轴设置
			NumberAxis axis = new NumberAxis();
			NumberTickUnit ntu2 = new NumberTickUnit(1d);
			axis.setTickUnit(ntu2);
			axis.setLowerMargin(0);
			axis.setUpperMargin(0);
			plot.setDomainAxis(axis);
			// 数据渲染部分 主要是对折线做操作
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
					.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBaseItemLabelsVisible(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); // 设置提示折点数据形状

			plot.setRenderer(renderer);

			return chart;

		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 画瞬时心电图
	 * 
	 * @param xyDataset
	 * @param title
	 * @param xtitle
	 * @param ytitle
	 * @param b
	 * @param fs
	 * @return
	 */
	public static JFreeChart createHrecgChart(XYDataset xyDataset,
			String title, String xtitle, String ytitle, boolean b, int fs) {
		try {

			// 定义图标对象
			JFreeChart chart = ChartFactory.createXYLineChart(title,// 报表题目，字符串类型

					xtitle, // 横轴

					ytitle, // 纵轴

					xyDataset, // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					true, // 不用生成工具

					false // 生成URL地址

					);

			chart.setBorderVisible(false);
			
			// 生成图形
			setJFreeChartBaseInfo(chart);
			if(xyDataset!=null&&xyDataset.getItemCount(0)<1){
				chart.getLegend().setVisible(false);
			}
			XYPlot plot = chart.getXYPlot();

			// 图像属性部分

			plot.setBackgroundPaint(new Color(239, 255, 239));
			plot.setDomainGridlinesVisible(false); // 设置背景网格线是否可见
			plot.setRangeGridlinesVisible(false);

			// plot.setNoDataMessage("没有数据");// 没有数据时显示的文字说明。
			setPlotInfo(plot);

			// 数据轴属性部分

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setAutoRangeIncludesZero(false); // 自动生成
			
			double unit = 25d;// 刻度的长度
			NumberTickUnit ntu = new NumberTickUnit(unit);
			rangeAxis.setTickUnit(ntu);
			//rangeAxis.setAutoRangeStickyZero(true);
			rangeAxis.setUpperBound(175);
			rangeAxis.setLowerBound(0.1);

			// X轴设置
			NumberAxis axis = new NumberAxis();
			NumberTickUnit ntu2 = new NumberTickUnit(10d);
			axis.setTickUnit(ntu2);
			axis.setLowerMargin(0);
			axis.setUpperMargin(0);
			//axis.setLabel(xtitle);
			plot.setDomainAxis(axis);
			// DateAxis axis2=new DateAxis();
			// axis2.setRange(new Date(2013, 3, 4),new Date(2015, 3, 5));//
			// 日期轴范围
			// plot.setDomainAxis(axis2);
			// 数据渲染部分 主要是对折线做操作
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBaseItemLabelsVisible(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); // 设置提示折点数据形状

			plot.setRenderer(renderer);
			if(xyDataset!=null&&xyDataset.getItemCount(0)>1){
			double lowpress = 55;

			double uperpress = 100; // 设定正常脉搏值的范围

			IntervalMarker inter = new IntervalMarker(lowpress, uperpress);

			inter.setLabelOffsetType(LengthAdjustmentType.EXPAND); // 范围调整——扩张

			inter.setPaint(new Color(239, 255, 239));// 域顏色
			// inter.set
			inter.setLabelFont(new Font("SansSerif", 1000, 20));

			inter.setLabelPaint(Color.RED);
			inter.setOutlinePaint(Color.red);
		    inter.setLabel("正常值范围"); // 设定区域说明文字
		    inter.setLabelAnchor(RectangleAnchor.CENTER);
		   // inter.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
			plot.addRangeMarker(inter, Layer.BACKGROUND); // 添加mark到图形
			}											// BACKGROUND使得数据折线在区域的前端
			return chart;

		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 画瞬时脉搏图
	 * 
	 * @param xyDataset
	 * @param title
	 * @param xtitle
	 * @param ytitle
	 * @param b
	 * @param fs
	 * @return
	 */
	public static JFreeChart createHrppgChart(XYDataset xyDataset,
			String title, String xtitle, String ytitle, boolean b, int fs) {
		try {

			// 定义图标对象
			JFreeChart chart = ChartFactory.createXYLineChart(title,// 报表题目，字符串类型

					xtitle, // 横轴

					ytitle, // 纵轴

					xyDataset, // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					true, // 不用生成工具

					false // 生成URL地址

					);

			chart.setBorderVisible(false);
			// 生成图形
			setJFreeChartBaseInfo(chart);
			if(xyDataset.getItemCount(0)<1){
				chart.getLegend().setVisible(false);
			}
			XYPlot plot = chart.getXYPlot();

			// 图像属性部分

			plot.setBackgroundPaint(new Color(239, 255, 239));
			plot.setDomainGridlinesVisible(false); // 设置背景网格线是否可见
			plot.setRangeGridlinesVisible(false);
			setPlotInfo(plot);

			// 数据轴属性部分

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setAutoRangeIncludesZero(true); // 自动生成
			double unit = 25d;// 刻度的长度
			NumberTickUnit ntu = new NumberTickUnit(unit);
			rangeAxis.setTickUnit(ntu);
			rangeAxis.setLowerBound(0.1);
			rangeAxis.setUpperBound(175);
			//rangeAxis.S
			//rangeAxis.setAutoRangeStickyZero(true);
			// Random

			// X轴设置
			NumberAxis axis = new NumberAxis();
			NumberTickUnit ntu2 = new NumberTickUnit(10d);
			axis.setTickUnit(ntu2);
			axis.setLowerMargin(0);
			axis.setUpperMargin(0);
			plot.setDomainAxis(axis);
			// 数据渲染部分 主要是对折线做操作
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
					.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBaseItemLabelsVisible(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); // 设置提示折点数据形状

			plot.setRenderer(renderer);
			if(xyDataset!=null&&xyDataset.getItemCount(0)>1){
				double lowpress = 55;

				double uperpress = 100; // 设定正常脉搏值的范围

				IntervalMarker inter = new IntervalMarker(lowpress, uperpress);

				inter.setLabelOffsetType(LengthAdjustmentType.EXPAND); // 范围调整——扩张

				inter.setPaint(new Color(239, 255, 239));// 域顏色
				// inter.set
				 inter.setLabelFont(new Font("SansSerif", 150, 20));
				 inter.setLabelPaint(Color.RED);
				 inter.setOutlinePaint(Color.red);
				 inter.setLabel("正常值范围"); // 设定区域说明文字
				 inter.setLabelAnchor(RectangleAnchor.CENTER);
				plot.addRangeMarker(inter, Layer.BACKGROUND); // 添加mark到图形
				}	
			return chart;

		} catch (Exception e) {

		}
		return null;
	}

	public static JFreeChart createAbecgChart(XYDataset xyDataset,
			String title, String xtitle, String ytitle, boolean b, int fs) {
		try {

			// 定义图标对象
			JFreeChart chart = ChartFactory.createXYLineChart(title,// 报表题目，字符串类型

					xtitle, // 横轴

					ytitle, // 纵轴

					xyDataset, // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					true, // 不用生成工具

					false // 生成URL地址

					);

			chart.setBorderVisible(false);
			// 生成图形
			setJFreeChartBaseInfo(chart);
			if(xyDataset.getItemCount(0)<1){
				chart.getLegend().setVisible(false);
			}
			XYPlot plot = chart.getXYPlot();

			// plot.setDomainGridlineStroke(new BasicStroke());
			//
			// plot.setRangeGridlineStroke(new BasicStroke());
			// 图像属性部分

			plot.setBackgroundPaint(Color.white);

			plot.setDomainGridlinesVisible(true); // 设置背景网格线是否可见

			plot.setDomainGridlinePaint(Color.red); // 设置背景网格线颜色

			plot.setRangeGridlinePaint(Color.red);

			// plot.setNoDataMessage("没有数据");// 没有数据时显示的文字说明。
			setPlotInfo(plot);

			// 数据轴属性部分

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

			rangeAxis.setAutoRangeIncludesZero(false); // 自动生成

			double unit = 20d;// 刻度的长度
			NumberTickUnit ntu = new NumberTickUnit(unit);
			rangeAxis.setTickUnit(ntu);
			rangeAxis.setVisible(false);

			// X轴设置
			NumberAxis axis = new NumberAxis();
			NumberTickUnit ntu2 = new NumberTickUnit(15d);
			axis.setTickUnit(ntu2);
			axis.setVisible(false);
			axis.setLowerMargin(0);
			axis.setUpperMargin(0);
			plot.setDomainAxis(axis);
			// 数据渲染部分 主要是对折线做操作

			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
					.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBaseItemLabelsVisible(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); // 设置提示折点数据形状

			plot.setRenderer(renderer);
			return chart;

		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * 画心电图
	 * 
	 * @param dataSet
	 * @param title
	 * @param xtitle
	 * @param ytitle
	 * @param is3D
	 * @param fs
	 * @return
	 */
	public static JFreeChart createMiEcgChart(XYDataset dataSet, String title,
			String xtitle, String ytitle, boolean is3D, int fs) {

		try {

			// 定义图标对象
			JFreeChart chart = ChartFactory.createXYLineChart(title,// 报表题目，字符串类型

					xtitle, // 横轴

					ytitle, // 纵轴

					dataSet, // 获得数据集

					PlotOrientation.VERTICAL, // 图标方向垂直

					true, // 显示图例

					true, // 不用生成工具

					false // 生成URL地址

					);
         
			chart.setBorderVisible(false);
			// 生成图形
			setJFreeChartBaseInfo(chart);
			if(dataSet.getItemCount(0)<1){
				chart.getLegend().setVisible(false);
			}
			XYPlot plot = chart.getXYPlot();

			 plot.setDomainGridlineStroke(new BasicStroke());
			
			 plot.setRangeGridlineStroke(new BasicStroke());
			// 图像属性部分

			plot.setBackgroundPaint(Color.white);
			plot.setDomainGridlinesVisible(true); // 设置背景网格线是否可见
			plot.setRangeGridlinesVisible(true);
			

			plot.setDomainGridlinePaint(new Color(255, 192, 203)); // 设置背景网格线颜色

			plot.setRangeGridlinePaint(new Color(255, 192, 203));

			// plot.setNoDataMessage("没有数据");// 没有数据时显示的文字说明。
			setPlotInfo(plot);

			// 数据轴属性部分

			NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

			rangeAxis.setAutoRangeIncludesZero(false); // 自动生成
			// rangeAxis .setAutoTickUnitSelection(true);
			double unit = 10d;// 刻度的长度
			NumberTickUnit ntu = new NumberTickUnit(unit);
			rangeAxis.setTickUnit(ntu);
			rangeAxis.setVisible(false);
			

			//
			// ValueAxis valueAxis = plot.getDomainAxis();
			// valueAxis.setAutoRange(false);
			// valueAxis.setFixedAutoRange(6*fs);
			// valueAxis.setAutoRangeMinimumSize(20);
			// valueAxis.

			// X轴设置
			NumberAxis axis = new NumberAxis();
			NumberTickUnit ntu2 = new NumberTickUnit(0.2d);
			axis.setTickUnit(ntu2);
			axis.setVisible(false);
			axis.setLowerMargin(0);
			axis.setUpperMargin(0);
//			axis.setLabel(title);
//			axis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
//			 if(fs>200){
//			 axis.setFixedAutoRange(6*fs);
//			 }
			
			plot.setDomainAxis(axis);
			// 数据渲染部分 主要是对折线做操作
//			String imgPath = "C:\\Users\\bit\\Desktop\\getEcgInfo.png";  
//			Image image = ImageIO.read(new FileInputStream(imgPath)); 
//            plot.setBackgroundImage(image);
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
					.getRenderer();

			renderer.setBaseItemLabelsVisible(true);

			renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色

			renderer.setBaseShapesFilled(true);

			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(

			ItemLabelAnchor.OUTSIDE1, TextAnchor.BASELINE_LEFT));

			renderer.setBaseItemLabelFont(new Font("Dialog", 1, 7)); // 设置提示折点数据形状

			plot.setRenderer(renderer);
			return chart;

		} catch (Exception e) {

		}
		return null;

	}

}
