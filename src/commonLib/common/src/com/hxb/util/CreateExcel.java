package com.hxb.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.Workbook;
import jxl.write.*;
import jxl.format.*;
import jxl.format.Colour;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.biff.DisplayFormat;


public class CreateExcel {
	protected ByteArrayOutputStream out;
	protected WritableWorkbook workbook;


	public CreateExcel() throws IOException {
		out = new ByteArrayOutputStream();
		workbook = Workbook.createWorkbook(out);
	}

	
	/**
	 * �õ�excel�ļ�����������
	 */
	public InputStream getExcelInputStream() throws Exception {
		workbook.write();
		workbook.close();
		return new ByteArrayInputStream(out.toByteArray());
	}
		


	/**
	 * ����ͷ��ʽ
	 * @param fontSize ���ִ�С
	 * @param hasBorder �Ƿ���ʾ��Ԫ��
	 * @return
	 */
	public WritableCellFormat getHeadFormat(
			int fontSize, 
			boolean underLine , 
			boolean hasBorder , 
			String noBorder) {
		WritableFont headerFont = null ;
		if(underLine){
			headerFont = new WritableFont(WritableFont.ARIAL, fontSize,
				WritableFont.BOLD, false, UnderlineStyle.SINGLE,
				Colour.BLACK);
		}else{
			headerFont = new WritableFont(WritableFont.ARIAL, fontSize,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
		}
		WritableCellFormat format = new WritableCellFormat(headerFont);
		try {
			format.setAlignment(Alignment.CENTRE);
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			if(hasBorder){
				format.setBorder(Border.ALL, BorderLineStyle.THIN); 
				if(noBorder == null || noBorder.equals("none")){
				}else if(noBorder.equals("all")){
					format.setBorder(Border.ALL, BorderLineStyle.NONE); 
				}else if(noBorder.equals("left")){
					format.setBorder(Border.LEFT, BorderLineStyle.NONE); 
				}else if(noBorder.equals("right")){
					format.setBorder(Border.RIGHT, BorderLineStyle.NONE); 
				}else if(noBorder.equals("top")){
					format.setBorder(Border.TOP, BorderLineStyle.NONE); 
				}else if(noBorder.equals("bottom")){
					format.setBorder(Border.BOTTOM, BorderLineStyle.NONE); 
				}
			}else{
				format.setBorder(Border.ALL, BorderLineStyle.NONE); 
			}
		} catch (WriteException e) {
			return format;
		}
		return format;

	}

	/**
	 * �������ĸ�ʽ
	 * @param fontSize ���ִ�С
	 * @param align ���ֶ��뷽ʽ
	 * @param hasBorder �Ƿ���ʾ��Ԫ��
	 * @param canWrap �Ƿ�������ʾ
	 * @return
	 */
	public WritableCellFormat getStringFormat(
			int fontSize, 
			boolean underLine , 
			String align , 
			String valign ,
			boolean hasBorder , 
			String noBorder,
			boolean canWrap){
		WritableFont plainFont = null ;
		if(underLine){
			plainFont = new WritableFont(WritableFont.ARIAL, fontSize,
				WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE,
				Colour.BLACK);
		}else{
			plainFont = new WritableFont(WritableFont.ARIAL, fontSize,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
		}
		WritableCellFormat format = new WritableCellFormat(plainFont);
		try {
			if(align== null || align.equals("center")){
				format.setAlignment(Alignment.CENTRE);
			}else if(align.equals("left")){
				format.setAlignment(Alignment.LEFT);
			}else if(align.equals("right")){
				format.setAlignment(Alignment.RIGHT);
			}
			if(valign== null || valign.equals("middle")){
				format.setVerticalAlignment(VerticalAlignment.CENTRE) ;
			}else if(valign.equals("top")){
				format.setVerticalAlignment(VerticalAlignment.TOP) ;
			}else if(valign.equals("bottom")){
				format.setVerticalAlignment(VerticalAlignment.BOTTOM) ;
			}
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			if(hasBorder){
				format.setBorder(Border.ALL, BorderLineStyle.THIN); 
				if(noBorder != null && !noBorder.trim().equals("")){
					String[] noBorders = noBorder.split(",") ;
					for(int i = 0 ; i < noBorders.length ; i++){
						if(noBorders[i].equals("all")){
							format.setBorder(Border.ALL, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("left")){
							format.setBorder(Border.LEFT, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("right")){
							format.setBorder(Border.RIGHT, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("top")){
							format.setBorder(Border.TOP, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("bottom")){
							format.setBorder(Border.BOTTOM, BorderLineStyle.NONE); 
						}
					}
				}
			}else{
				format.setBorder(Border.ALL, BorderLineStyle.NONE); 
			}
			if(canWrap){
				format.setWrap(true);//�Զ�����
			}else{
				format.setWrap(false);//
			}
		} catch (WriteException e) {
			return format;
		}
		return format;
	}

	/**
	 * 
	 * @param fontSize ���ִ�С
	 * @param align ���ֶ��뷽ʽ
	 * @param hasBorder �Ƿ���ʾ��Ԫ��
	 * @param canWrap �Ƿ�������ʾ
	 * @param numberType ��������(float , int , percent)
	 * @return
	 */
	public WritableCellFormat getNumberFormat(
			int fontSize ,
			boolean underLine, 
			String align ,
			String valign ,
			boolean hasBorder , 
			String noBorder,
			boolean canWrap , 
			String numberType){
		WritableFont plainFont = null ;
		if(underLine){
			plainFont = new WritableFont(WritableFont.ARIAL, fontSize,
				WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE,
				Colour.BLACK);
		}else{
			plainFont = new WritableFont(WritableFont.ARIAL, fontSize,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
		}

		DisplayFormat df = null ;
		if(numberType==null || numberType.equals("float")){
			df = NumberFormats.FLOAT ;
		}else if(numberType.equals("int")){
			df = NumberFormats.INTEGER ;
		}else if(numberType.equals("percent")){
			df = NumberFormats.PERCENT_FLOAT ;
		}else{
			df = NumberFormats.FLOAT ;
		}
		WritableCellFormat format = new WritableCellFormat(plainFont , df);
		try {
			if(align== null || align.equals("center")){
				format.setAlignment(Alignment.CENTRE);
			}else if(align.equals("left")){
				format.setAlignment(Alignment.LEFT);
			}else if(align.equals("right")){
				format.setAlignment(Alignment.RIGHT);
			}
			if(valign== null || valign.equals("middle")){
				format.setVerticalAlignment(VerticalAlignment.CENTRE) ;
			}else if(valign.equals("top")){
				format.setVerticalAlignment(VerticalAlignment.TOP) ;
			}else if(valign.equals("bottom")){
				format.setVerticalAlignment(VerticalAlignment.BOTTOM) ;
			}
			format.setVerticalAlignment(VerticalAlignment.CENTRE);
			if(hasBorder){
				format.setBorder(Border.ALL, BorderLineStyle.THIN); 
				if(noBorder != null && !noBorder.trim().equals("")){
					String[] noBorders = noBorder.split(",") ;
					for(int i = 0 ; i < noBorders.length ; i++){
						if(noBorders[i].equals("all")){
							format.setBorder(Border.ALL, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("left")){
							format.setBorder(Border.LEFT, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("right")){
							format.setBorder(Border.RIGHT, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("top")){
							format.setBorder(Border.TOP, BorderLineStyle.NONE); 
						}else if(noBorders[i].equals("bottom")){
							format.setBorder(Border.BOTTOM, BorderLineStyle.NONE); 
						}
					}
				}
			}else{
				format.setBorder(Border.ALL, BorderLineStyle.NONE); 
			}
			if(canWrap){
				format.setWrap(true);//�Զ�����
			}else{
				format.setWrap(false);//
			}
		} catch (WriteException e) {
			return format;
		}
		return format;
	}

}
