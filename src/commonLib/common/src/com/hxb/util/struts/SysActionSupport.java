package com.hxb.util.struts ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Action class for the Tutorial package.
 */
public abstract class SysActionSupport extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3515078654701117288L;

	private String rlt_message;//������ʾ��Ϣ
	private String excelName;//����excel�ļ�����
	private File[] upload ;//��װ�ϴ��ļ�������
	private String[] uploadContentType ;//�ϴ��ļ�����������
	private String[] uploadFileName ;//�ϴ��ļ�������
	private String del_fileIds;//ɾ������id�����id��,�ָ�
	/**������**/
	private File uploadSingle;//�ϴ�������
	private String uploadSingleContentType;//�ϴ�����������
	private String uploadSingleFileName;//�ϴ�����������
	private String del_fileId;//ɾ��������id
	
	//�洢action�е��������Ƽ�ֵ�ԣ�����ҳ��form�����
	public Hashtable<String, String> allProperties = new Hashtable<String, String>() ;//ȫ����ִ��
	public Hashtable<String,String> formProperties = new Hashtable<String, String>() ; //��form��ִ��
	public static final String _FORMPROPERTY_ANNO = "_formProperty_anno";//��form��ִ���־
	public static final String _PRE_FORMPROPERTY = "form_";//������ִ����form��ִ��ʶ��ͬʱallPropertiesҲ��ִ
	
	/**
	 * @param addProperties ���ӵ��������Ƽ�ֵ��
	 * �����Ƹ�ʽΪ[_PRE_FORMPROPERTY*]�ģ���Ҫ����formProperties�л�ִ
	 */
	protected void getActionProperties(Class<?> clazz , Hashtable<String, String> addProperties) {
		Class<?> superclass = clazz.getSuperclass() ;
		if(superclass != null){
			this.getActionProperties(superclass , null) ;
		}
		Field[] fs = clazz.getFields();
		if (fs != null) {
			Class<?> type = null;
			PropertieType anno = null ;
			Object value = null ;
			for (int i = 0; i < fs.length; i++) {
				try {
					type = fs[i].getType();
					anno = fs[i].getAnnotation(PropertieType.class);
					if(anno != null){
						value = fs[i].get(this) ;
						if(value != null){
							if (type.getSimpleName().equals("String")) {
								allProperties.put(fs[i].getName(), (String)value);
								if(anno.value().equals(_FORMPROPERTY_ANNO)){
									formProperties.put(fs[i].getName(), (String)value);
								}
							}else if(type.getSimpleName().equals("Double")){
								allProperties.put(fs[i].getName(), ((Double)value).toString());
								if(anno.value().equals(_FORMPROPERTY_ANNO)){
									formProperties.put(fs[i].getName(), ((Double)value).toString());
								}
							}else if(type.getSimpleName().equals("Float")){
								allProperties.put(fs[i].getName(), ((Float)value).toString());
								if(anno.value().equals(_FORMPROPERTY_ANNO)){
									formProperties.put(fs[i].getName(), ((Float)value).toString());
								}
							}else if(type.getSimpleName().equals("Long") || type.getSimpleName().equals("long")){
								allProperties.put(fs[i].getName(), ((Long)value).toString());
								if(anno.value().equals(_FORMPROPERTY_ANNO)){
									formProperties.put(fs[i].getName(), ((Long)value).toString());
								}
							}else if(type.getSimpleName().equals("Integer") || type.getSimpleName().equals("int")){
								allProperties.put(fs[i].getName(), ((Integer)value).toString());
								if(anno.value().equals(_FORMPROPERTY_ANNO)){
									formProperties.put(fs[i].getName(), ((Integer)value).toString());
								}
							}else if(type.getSimpleName().equals("Boolean") || type.getSimpleName().equals("boolean")){
								allProperties.put(fs[i].getName(), ((Boolean)value).toString());
								if(anno.value().equals(_FORMPROPERTY_ANNO)){
									formProperties.put(fs[i].getName(), ((Boolean)value).toString());
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {}
			}
		}
		
		//�����������Ƽ�ֵ��
		if(addProperties != null){
			Set<Map.Entry<String , String>> set = addProperties.entrySet() ;
			Iterator<Map.Entry<String , String>> it = set.iterator() ;
			Map.Entry<String , String> entry = null ;
			String key = null ;
			while(it.hasNext()){
				entry = it.next() ;
				key = entry.getKey() ;
				allProperties.put(key, entry.getValue()) ;
				if(key.startsWith(_PRE_FORMPROPERTY)){
					formProperties.put(key, entry.getValue());
				}
			}
		}
	}




	public String getExcelName() {
		return excelName;
	}


	/**
	 * ���õ���excel�ļ���
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	public void setExcelName(String excelName) throws UnsupportedEncodingException {
		if(excelName == null || excelName.equals("")){
			excelName = "excel" + System.currentTimeMillis() + ".xls";
		}
		if(!excelName.endsWith(".xls") && !excelName.endsWith(".xlsx")){
			excelName += ".xls";
		}
		this.excelName = new String(excelName.getBytes(),"ISO-8859-1");
	}

	/**
	 * ����ajax result
	 * @param rlt,status
	 * @return
	 */
	protected String createAjaxResult(boolean status,String rlt){
		JSONObject obj = new JSONObject();
		obj.put("message", rlt);
		obj.put("success", status);
		
		return obj.toString();
	}
	/**
	 * ����ajax result
	 * @param rlt
	 * @return
	 */
	protected String createAjaxResult(String rlt){
		JSONObject obj = new JSONObject();
		obj.put("message", rlt);
		
		return obj.toString();
	}
	/**
	 * @param xml
	 * @param encoding��Ĭ�ϱ���Ϊ��UTF-8
	 */
	protected void responseXml(Object xml,String encoding){
		responseContent(xml,encoding,null);
	}	
	
	/**
	 * @param html
	 * @param encoding��Ĭ�ϱ���Ϊ��UTF-8
	 */
	protected void responseHtml(Object html,String encoding){
		responseContent(html,encoding,"text/html");
	}
	/**
	 * @param content
	 * @param encoding
	 * @param type
	 */
	private void responseContent(Object content,String encoding,String type){
		/***/
		if(encoding == null || encoding.equals("")){//Ĭ�ϱ��뷽ʽ
			encoding = "UTF-8";
		}
		if(type == null || type.equals("")){
			type = "text/xml";
		}
		ActionContext ctx = ActionContext.getContext();       
		HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
		response.setContentType(type + ";charset=" + encoding);
		response.setHeader("Pragma","No-Cache");
		response.setHeader("Cache-Control","No-Cache");
		response.setDateHeader("Expires", 0);
		try {
			PrintWriter out = response.getWriter();
			out.print(content);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{;}
		/***/
	}
	 /** 
	* @Title: createWorkBook 
	* @Description: TODO(ת������excel�ļ�) 
	* @return Workbook    �������� 
	* @throws 
	*/ 
	protected Workbook createWorkBook(File file,String fileName) throws IOException{
		 if(file == null){
			 return null;
		 }
		 if(fileName == null || (!fileName.endsWith("xls") 
				 && !fileName.endsWith("xlsx"))){//�ļ���ʽУ�飬֧��excel��xls��xlsx
			 return null;
		 }
		 
	     if(fileName.toLowerCase().endsWith("xls")){  
	          return new HSSFWorkbook(new FileInputStream(file));  
	     }  
	     if(fileName.toLowerCase().endsWith("xlsx")){  
	         return new XSSFWorkbook(new FileInputStream(file));  
	     }  
	     return null;  
	  }
	
	public File[] getUpload() {
		return upload;
	}
	
	public void setUpload(File[] upload) {
		this.upload = upload;
	}


	public String[] getUploadContentType() {
		return uploadContentType;
	}


	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


	public String[] getUploadFileName() {
		return uploadFileName;
	}


	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}


	public Hashtable<String, String> getAllProperties() {
		return allProperties;
	}

	public void setAllProperties(Hashtable<String, String> allProperties) {
		this.allProperties = allProperties;
	}

	public Hashtable<String, String> getFormProperties() {
		return formProperties;
	}

	public void setFormProperties(Hashtable<String, String> formProperties) {
		this.formProperties = formProperties;
	}

	public String getRlt_message() {
		return rlt_message;
	}

	public void setRlt_message(String rlt_message) {
		this.rlt_message = rlt_message;
	}

	public String getDel_fileIds() {
		return del_fileIds;
	}

	public void setDel_fileIds(String del_fileIds) {
		this.del_fileIds = del_fileIds;
	}

	public File getUploadSingle() {
		return uploadSingle;
	}

	public void setUploadSingle(File uploadSingle) {
		this.uploadSingle = uploadSingle;
	}

	public String getUploadSingleContentType() {
		return uploadSingleContentType;
	}

	public void setUploadSingleContentType(String uploadSingleContentType) {
		this.uploadSingleContentType = uploadSingleContentType;
	}

	public String getUploadSingleFileName() {
		return uploadSingleFileName;
	}

	public void setUploadSingleFileName(String uploadSingleFileName) {
		this.uploadSingleFileName = uploadSingleFileName;
	}

	public String getDel_fileId() {
		return del_fileId;
	}

	public void setDel_fileId(String del_fileId) {
		this.del_fileId = del_fileId;
	}

}

