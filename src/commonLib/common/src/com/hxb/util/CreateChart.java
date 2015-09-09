package com.hxb.util;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.servlet.ServletUtilities;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.title.*;
import org.jfree.data.category.*;
import org.jfree.data.general.*;
import org.jfree.data.xy.*;

public class CreateChart {
	
	private static Color chartBgColor = Color.white ;
	private static Color plotBgColor = new Color(236 , 233 , 216) ; ;

	/**
	 * 得到图URL
	 * @throws IOException
	 */
	public String imageUrl(
			HttpServletRequest request ,
			JFreeChart chart ,
			int width ,
			int height) throws IOException {
		String filename = ServletUtilities.saveChartAsPNG(chart, width, height ,request.getSession());
		// 调用DisplayChart（配置在web.xml中），生成图形
		return request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
	}
	
	
	/**
	 * 饼图
	 * @param title 标题
	 * @param dataset 数据集
	 * @param is3D 是否为3D图
	 * @param showLegend 是否显示图例 
	 * @return
	 */
	public JFreeChart pieChart(
			String title , 
			DefaultPieDataset dataset ,
			boolean is3D , 
			boolean showLegend){
		JFreeChart chart = null ;
		if(is3D){
		    chart = ChartFactory.createPieChart3D(
				title, //图表标题
				dataset, //数据集合
				showLegend, //是否显示图例
				false, //是否显示工具提示
				false //是否生成URL
				) ;
		}else{
		    chart = ChartFactory.createPieChart(
					title, //图表标题
					dataset, //数据集合
					showLegend, //是否显示图例
					false, //是否显示工具提示
					false //是否生成URL
					) ;
		}
		//重新设置图表标题
		chart.setTitle(new TextTitle(title , new Font("黑体" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		//得到图标第一个图例
		LegendTitle legend = chart.getLegend(0) ;
		//修改图例的字体
		legend.setItemFont(new Font("宋体" , Font.BOLD , 10)) ;
		legend.setBorder(0.0d, 0.0d, 0.0d, 0.0d) ;
		
		//获得饼图的Plot对象
		PiePlot plot = (PiePlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//设置饼各部分的标签字体
		plot.setLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		//设置背景透明度(0至1.0之间)
		plot.setBackgroundAlpha(1.0f) ;
		//设置前景透明度(0至1.0之间)
		plot.setForegroundAlpha(1.0f) ;
		
		plot.setLabelBackgroundPaint(Color.white) ;
//		plot.setLabelPaint(Color.gray) ;
//		plot.setLabelOutlinePaint(Color.white) ;
//		plot.setBackgroundPaint(Color.white) ;
		
		return chart ;
		
	}
	/**
	 * 柱图
	 * @param title标题
	 * @param xName 水平轴名称
	 * @param yName 垂直轴名称
	 * @param dataset 数据集
	 * @param is3D 是否为3D图
	 * @param showLegend 是否显示图例
	 * @return
	 */
	public JFreeChart barChart(
			String title , 
			String xName ,
			String yName ,
			DefaultCategoryDataset dataset ,
			boolean is3D, 
			boolean showLegend){
		JFreeChart chart = null ;
		if(is3D){
			chart = ChartFactory.createBarChart3D(
					title, //图表标题
					xName,//目录轴的显示标签
					yName,//数据值轴的显示标签
					dataset, //数据集合
					PlotOrientation.VERTICAL,//图片方向:垂直
					showLegend, //是否显示图例
					false, //是否显示工具提示
					false //是否生成URL
					) ;
		}else{
			chart = ChartFactory.createBarChart(
					title, //图表标题
					xName,//目录轴的显示标签
					yName,//数据值轴的显示标签
					dataset, //数据集合
					PlotOrientation.VERTICAL,//图片方向:垂直
					showLegend, //是否显示图例
					false, //是否显示工具提示
					false //是否生成URL
					) ;
		}
		//重新设置图表标题
		chart.setTitle(new TextTitle(title , new Font("黑体" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		
		if(showLegend){
			//得到图标第一个图例
			LegendTitle legend = chart.getLegend(0) ;
			//修改图例的字体
			legend.setItemFont(new Font("宋体" , Font.BOLD , 10)) ;
		}
		
		//获得柱图的Plot对象
		CategoryPlot plot = (CategoryPlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//取得横轴
		CategoryAxis x = plot.getDomainAxis() ;
		//设置横轴显示标签字体
		x.setLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		//设置横轴显示标签倾斜角度
		x.setCategoryLabelPositions(CategoryLabelPositions.UP_45) ;
		//????
		x.setTickLabelFont(new Font("宋体" , Font.BOLD , 10)) ;

		
		//取是纵轴
		NumberAxis y = (NumberAxis)plot.getRangeAxis() ;
		//设置横轴显示标签字体
		y.setLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		//????
		y.setTickLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		
		//设置背景透明度(0至1.0之间)
		plot.setBackgroundAlpha(1.0f) ;
		//设置前景透明度(0至1.0之间)
		plot.setForegroundAlpha(1.0f) ;
		
		return chart ;
	}
	
	/**
	 * 折线图
	 * @param title标题
	 * @param xName 水平轴名称
	 * @param yName 垂直轴名称
	 * @param dataset 数据集
	 * @param is3D 是否为3D图
	 * @param showLegend 是否显示图例
	 * @return
	 */
	public JFreeChart lineChart(
			String title , 
			String xName ,
			String yName ,
			DefaultCategoryDataset dataset ,
			boolean is3D, 
			boolean showLegend){
		JFreeChart chart = null ;
		if(is3D){
			chart = ChartFactory.createLineChart3D(
					title, //图表标题
					xName,//目录轴的显示标签
					yName,//数据值轴的显示标签
					dataset, //数据集合
					PlotOrientation.VERTICAL,//图片方向:垂直
					showLegend, //是否显示图例
					false, //是否显示工具提示
					false //是否生成URL
					) ;
		}else{
			chart = ChartFactory.createLineChart(
					title, //图表标题
					xName,//目录轴的显示标签
					yName,//数据值轴的显示标签
					dataset, //数据集合
					PlotOrientation.VERTICAL,//图片方向:垂直
					showLegend, //是否显示图例
					false, //是否显示工具提示
					false //是否生成URL
					) ;
		}
		//重新设置图表标题
		chart.setTitle(new TextTitle(title , new Font("黑体" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		
		if(showLegend){
			//得到图标第一个图例
			LegendTitle legend = chart.getLegend(0) ;
			//修改图例的字体
			legend.setItemFont(new Font("宋体" , Font.BOLD , 10)) ;
		}
		
		//获得柱图的Plot对象
		CategoryPlot plot = (CategoryPlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//取得横轴
		CategoryAxis x = plot.getDomainAxis() ;
		//设置横轴显示标签字体
		x.setLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		//设置横轴显示标签倾斜角度
		x.setCategoryLabelPositions(CategoryLabelPositions.UP_45) ;
		//????
		x.setTickLabelFont(new Font("宋体" , Font.BOLD , 10)) ;

		
		//取是纵轴
		NumberAxis y = (NumberAxis)plot.getRangeAxis() ;
		//设置横轴显示标签字体
		y.setLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		//????
		y.setTickLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		
		
		//设置背景透明度(0至1.0之间)
		plot.setBackgroundAlpha(1.0f) ;
		//设置前景透明度(0至1.0之间)
		plot.setForegroundAlpha(1.0f) ;
		
		return chart ;
	}
//	public static String yyyy() {
//		return new SimpleDateFormat("yyyy").;
//	}
	
	public JFreeChart timeChart(
			String title , 
			String xName ,
			String yName ,
			XYDataset dataset ,
			boolean showLegend
			){
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
					title, //图表标题
					xName,//目录轴的显示标签
					yName,//数据值轴的显示标签
					dataset, //数据集合
					showLegend, //是否显示图例
					false, //是否显示工具提示
					false //是否生成URL
					) ;
		//重新设置图表标题
		chart.setTitle(new TextTitle(title , new Font("黑体" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		
		if(showLegend){
			//得到图标第一个图例
			LegendTitle legend = chart.getLegend(0) ;
			//修改图例的字体
			legend.setItemFont(new Font("宋体" , Font.BOLD , 10)) ;
		}
		
		//获得柱图的Plot对象
		XYPlot plot = (XYPlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//取得横轴
		ValueAxis x = plot.getDomainAxis() ;
		//设置横轴显示标签字体
		x.setLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		//?
		//x.setLabelAngle(45.0) ;
		//????
		x.setTickLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		x.setAutoRange(false) ;


		//取是纵轴
		NumberAxis y = (NumberAxis)plot.getRangeAxis() ;
		//设置横轴显示标签字体
		y.setLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		//????
		y.setTickLabelFont(new Font("宋体" , Font.BOLD , 10)) ;
		
		
		//设置背景透明度(0至1.0之间)
		plot.setBackgroundAlpha(1.0f) ;
		//设置前景透明度(0至1.0之间)
		plot.setForegroundAlpha(1.0f) ;
		
		return chart ;

		
	}

}
