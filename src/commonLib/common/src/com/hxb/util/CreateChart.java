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
	 * �õ�ͼURL
	 * @throws IOException
	 */
	public String imageUrl(
			HttpServletRequest request ,
			JFreeChart chart ,
			int width ,
			int height) throws IOException {
		String filename = ServletUtilities.saveChartAsPNG(chart, width, height ,request.getSession());
		// ����DisplayChart��������web.xml�У�������ͼ��
		return request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
	}
	
	
	/**
	 * ��ͼ
	 * @param title ����
	 * @param dataset ���ݼ�
	 * @param is3D �Ƿ�Ϊ3Dͼ
	 * @param showLegend �Ƿ���ʾͼ�� 
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
				title, //ͼ�����
				dataset, //���ݼ���
				showLegend, //�Ƿ���ʾͼ��
				false, //�Ƿ���ʾ������ʾ
				false //�Ƿ�����URL
				) ;
		}else{
		    chart = ChartFactory.createPieChart(
					title, //ͼ�����
					dataset, //���ݼ���
					showLegend, //�Ƿ���ʾͼ��
					false, //�Ƿ���ʾ������ʾ
					false //�Ƿ�����URL
					) ;
		}
		//��������ͼ�����
		chart.setTitle(new TextTitle(title , new Font("����" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		//�õ�ͼ���һ��ͼ��
		LegendTitle legend = chart.getLegend(0) ;
		//�޸�ͼ��������
		legend.setItemFont(new Font("����" , Font.BOLD , 10)) ;
		legend.setBorder(0.0d, 0.0d, 0.0d, 0.0d) ;
		
		//��ñ�ͼ��Plot����
		PiePlot plot = (PiePlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//���ñ������ֵı�ǩ����
		plot.setLabelFont(new Font("����" , Font.BOLD , 10)) ;
		//���ñ���͸����(0��1.0֮��)
		plot.setBackgroundAlpha(1.0f) ;
		//����ǰ��͸����(0��1.0֮��)
		plot.setForegroundAlpha(1.0f) ;
		
		plot.setLabelBackgroundPaint(Color.white) ;
//		plot.setLabelPaint(Color.gray) ;
//		plot.setLabelOutlinePaint(Color.white) ;
//		plot.setBackgroundPaint(Color.white) ;
		
		return chart ;
		
	}
	/**
	 * ��ͼ
	 * @param title����
	 * @param xName ˮƽ������
	 * @param yName ��ֱ������
	 * @param dataset ���ݼ�
	 * @param is3D �Ƿ�Ϊ3Dͼ
	 * @param showLegend �Ƿ���ʾͼ��
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
					title, //ͼ�����
					xName,//Ŀ¼�����ʾ��ǩ
					yName,//����ֵ�����ʾ��ǩ
					dataset, //���ݼ���
					PlotOrientation.VERTICAL,//ͼƬ����:��ֱ
					showLegend, //�Ƿ���ʾͼ��
					false, //�Ƿ���ʾ������ʾ
					false //�Ƿ�����URL
					) ;
		}else{
			chart = ChartFactory.createBarChart(
					title, //ͼ�����
					xName,//Ŀ¼�����ʾ��ǩ
					yName,//����ֵ�����ʾ��ǩ
					dataset, //���ݼ���
					PlotOrientation.VERTICAL,//ͼƬ����:��ֱ
					showLegend, //�Ƿ���ʾͼ��
					false, //�Ƿ���ʾ������ʾ
					false //�Ƿ�����URL
					) ;
		}
		//��������ͼ�����
		chart.setTitle(new TextTitle(title , new Font("����" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		
		if(showLegend){
			//�õ�ͼ���һ��ͼ��
			LegendTitle legend = chart.getLegend(0) ;
			//�޸�ͼ��������
			legend.setItemFont(new Font("����" , Font.BOLD , 10)) ;
		}
		
		//�����ͼ��Plot����
		CategoryPlot plot = (CategoryPlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//ȡ�ú���
		CategoryAxis x = plot.getDomainAxis() ;
		//���ú�����ʾ��ǩ����
		x.setLabelFont(new Font("����" , Font.BOLD , 10)) ;
		//���ú�����ʾ��ǩ��б�Ƕ�
		x.setCategoryLabelPositions(CategoryLabelPositions.UP_45) ;
		//????
		x.setTickLabelFont(new Font("����" , Font.BOLD , 10)) ;

		
		//ȡ������
		NumberAxis y = (NumberAxis)plot.getRangeAxis() ;
		//���ú�����ʾ��ǩ����
		y.setLabelFont(new Font("����" , Font.BOLD , 10)) ;
		//????
		y.setTickLabelFont(new Font("����" , Font.BOLD , 10)) ;
		
		//���ñ���͸����(0��1.0֮��)
		plot.setBackgroundAlpha(1.0f) ;
		//����ǰ��͸����(0��1.0֮��)
		plot.setForegroundAlpha(1.0f) ;
		
		return chart ;
	}
	
	/**
	 * ����ͼ
	 * @param title����
	 * @param xName ˮƽ������
	 * @param yName ��ֱ������
	 * @param dataset ���ݼ�
	 * @param is3D �Ƿ�Ϊ3Dͼ
	 * @param showLegend �Ƿ���ʾͼ��
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
					title, //ͼ�����
					xName,//Ŀ¼�����ʾ��ǩ
					yName,//����ֵ�����ʾ��ǩ
					dataset, //���ݼ���
					PlotOrientation.VERTICAL,//ͼƬ����:��ֱ
					showLegend, //�Ƿ���ʾͼ��
					false, //�Ƿ���ʾ������ʾ
					false //�Ƿ�����URL
					) ;
		}else{
			chart = ChartFactory.createLineChart(
					title, //ͼ�����
					xName,//Ŀ¼�����ʾ��ǩ
					yName,//����ֵ�����ʾ��ǩ
					dataset, //���ݼ���
					PlotOrientation.VERTICAL,//ͼƬ����:��ֱ
					showLegend, //�Ƿ���ʾͼ��
					false, //�Ƿ���ʾ������ʾ
					false //�Ƿ�����URL
					) ;
		}
		//��������ͼ�����
		chart.setTitle(new TextTitle(title , new Font("����" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		
		if(showLegend){
			//�õ�ͼ���һ��ͼ��
			LegendTitle legend = chart.getLegend(0) ;
			//�޸�ͼ��������
			legend.setItemFont(new Font("����" , Font.BOLD , 10)) ;
		}
		
		//�����ͼ��Plot����
		CategoryPlot plot = (CategoryPlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//ȡ�ú���
		CategoryAxis x = plot.getDomainAxis() ;
		//���ú�����ʾ��ǩ����
		x.setLabelFont(new Font("����" , Font.BOLD , 10)) ;
		//���ú�����ʾ��ǩ��б�Ƕ�
		x.setCategoryLabelPositions(CategoryLabelPositions.UP_45) ;
		//????
		x.setTickLabelFont(new Font("����" , Font.BOLD , 10)) ;

		
		//ȡ������
		NumberAxis y = (NumberAxis)plot.getRangeAxis() ;
		//���ú�����ʾ��ǩ����
		y.setLabelFont(new Font("����" , Font.BOLD , 10)) ;
		//????
		y.setTickLabelFont(new Font("����" , Font.BOLD , 10)) ;
		
		
		//���ñ���͸����(0��1.0֮��)
		plot.setBackgroundAlpha(1.0f) ;
		//����ǰ��͸����(0��1.0֮��)
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
					title, //ͼ�����
					xName,//Ŀ¼�����ʾ��ǩ
					yName,//����ֵ�����ʾ��ǩ
					dataset, //���ݼ���
					showLegend, //�Ƿ���ʾͼ��
					false, //�Ƿ���ʾ������ʾ
					false //�Ƿ�����URL
					) ;
		//��������ͼ�����
		chart.setTitle(new TextTitle(title , new Font("����" , Font.BOLD , 14))) ;
		chart.setBackgroundPaint(chartBgColor) ;
		//chart.setBorderVisible(true) ;
		//chart.setBorderPaint(Color.red) ;
		
		if(showLegend){
			//�õ�ͼ���һ��ͼ��
			LegendTitle legend = chart.getLegend(0) ;
			//�޸�ͼ��������
			legend.setItemFont(new Font("����" , Font.BOLD , 10)) ;
		}
		
		//�����ͼ��Plot����
		XYPlot plot = (XYPlot)chart.getPlot() ;
		plot.setBackgroundPaint(plotBgColor) ;
		//ȡ�ú���
		ValueAxis x = plot.getDomainAxis() ;
		//���ú�����ʾ��ǩ����
		x.setLabelFont(new Font("����" , Font.BOLD , 10)) ;
		//?
		//x.setLabelAngle(45.0) ;
		//????
		x.setTickLabelFont(new Font("����" , Font.BOLD , 10)) ;
		x.setAutoRange(false) ;


		//ȡ������
		NumberAxis y = (NumberAxis)plot.getRangeAxis() ;
		//���ú�����ʾ��ǩ����
		y.setLabelFont(new Font("����" , Font.BOLD , 10)) ;
		//????
		y.setTickLabelFont(new Font("����" , Font.BOLD , 10)) ;
		
		
		//���ñ���͸����(0��1.0֮��)
		plot.setBackgroundAlpha(1.0f) ;
		//����ǰ��͸����(0��1.0֮��)
		plot.setForegroundAlpha(1.0f) ;
		
		return chart ;

		
	}

}
