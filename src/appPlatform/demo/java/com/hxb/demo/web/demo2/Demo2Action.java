package com.hxb.demo.web.demo2;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hxb.global.common.tb.TableDatas;
import com.hxb.global.po.TbDemoStudent;
import com.hxb.util.struts.PageYMDActionSupport;
import com.hxb.util.struts.PropertieType;

/**
 * @author Administrator
 * --Aspect������springAOP,��ʵ�����淽ʽȨ����֤��������־�洢����
 * --Controller������spring annotation��ʽע��Action������ֵΪAction��ע��id����struts�����ļ���ָ��spring����
 * --Scope������Ϊע��������÷�Χ������request��Ч
 */
@Aspect
@Controller("demo2Action")
@Scope("request")
public class Demo2Action extends PageYMDActionSupport {

	
	/** 
	* @Fields serialVersionUID : TODO(��һ�仰�������������ʾʲô) 
	*/ 
	private static final long serialVersionUID = -7301386938142197364L;
	/**
	 * ��ȡlog4j��־����
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * ��������.��д��ʽ��һ��Ŀ¼Ŀ¼>>����Ŀ¼>>����Ŀ¼
	 */
	private String titleName1 = "��ǰλ�ã�����>����2>";
	/**
	 * ������ѯ������������������con_[conditionName];
	 * ����ʹ��public����
	 * ������������getter��setter����������֧��struts2��form���ݽ���
	 * */
	/**
	 * ��ѯ����-[Ժϵ����]
	 */
	@PropertieType
	public String con_deptName;
	/**
	 * ��ѯ����-[ѧ������]
	 */
	@PropertieType
	public String con_name;
	
	/** 
	* @Fields param_id : TODO(��������¼id) 
	*/ 
	private String param_id;
	
	/**
	 * ����busi�����
	 * --Autowired��spring annotation��ʽע�����
	 * �����Բ�������setter��getter������annotation��ʽע���Ѿ�������
	 * ע�⣺�˶���������ע��ѡ��@Service����ע�롣�Ҵ����ͱ�������context��û���ظ�����
	 */
	@Autowired
	private Demo2Busi busi;
	
	/**
	 * �������ؽ����������������rlt_[resultName]
	 * ������������getter��setter����������֧��struts2��ҳ�����ݽ���
	 * **/
	/**
	 * ���ؽ��-[action����]
	 */
	private String actionName = this.getClass().getSimpleName().substring(0,1).toLowerCase() 
		+ this.getClass().getSimpleName().substring(1);
		
	/**
	 * ���ؽ��-[���ؽ��˵��]
	 */
	private List<Object> rlt_list;
	/** 
	* @Fields po : TODO(ѧ����Ϣ) 
	*/ 
	private TbDemoStudent po;
	
	/**
	 * ���з��ʷ�������������form�ύ����
	 * **/
	
	
	/** 
	* @Title: initCondition 
	* @Description: TODO(��ʼ��������Ϣ) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws 
	*/ 
	private void initCondition() {
		// TODO Auto-generated method stub
		if(this.firstTime){
			//��ʼ������
			
			this.firstRequest();
		}
	}
	
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 */
//	@CheckPriv("-1")
	public String toList() throws Exception{
		//��ѯ�������죬��ѯ������ʼ��
		initCondition();		
		this.getActionProperties(this.getClass(), null);				
		//����ָ��
		return "list";
	}
	/**
	 * ��ȡtable ���ݼ���
	 * @return
	 */
	public String list(){
		//��ѯ����ע��
		injectCondition(this.getClass());
		//��ҳ����
		Long total = busi.getCount(params, "��ֵ���ݲ���");
		pages(total);
		//ҵ������
		rlt_list = busi.list(params,"��ֵ���ݲ���");
		
		TableDatas tds = new TableDatas();
		tds.setTotal(total);
		tds.bindDatas(rlt_list);		
			
		
		JSONObject jobj = JSONObject.fromObject(tds);
		responseHtml(jobj.toString(), null);
		
		return null;
	}
	
	
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 */
//	@CheckPriv("-1")
	public String excel() throws Exception{
		//��ѯ�������죬��ѯ������ʼ��
		initCondition();
		//��ѯ����ע��
		injectCondition(this.getClass());
		//ҵ������
		rlt_list = busi.list(params,"��ֵ���ݲ���");
		//excel�ļ����ƣ����ô���չ����Ĭ��ʹ��xls��Ϊ�ļ���չ��
		setExcelName("��������");
		//����ָ��
		return "excel";
	}
	
	/**
	 * @throws Exception  
	* @Title: impExcel 
	* @Description: TODO(����excel) 
	* @return String    �������� 
	* @throws 
	*/ 
	public String impExcel() throws Exception{		
		try {
			Workbook book = createWorkBook(this.getUploadSingle(), 
					this.getUploadSingleFileName());
			if(book != null){
				Sheet sheet = book.getSheetAt(0);
				for(Row r : sheet){
					for(Cell c : r){
						String str = "";
						if(c.getCellType() == Cell.CELL_TYPE_NUMERIC){
							str = c.getNumericCellValue() + "";
						}else{
							str = c.getStringCellValue();
						}
						System.out.print(str + "::");	
					}
					System.out.println();					
				}
			}else{
				setRlt_message("�ϴ��ļ����ǺϷ���ecxel��");
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	
		return list();
	}
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 * --Logor����־��¼���ã�����Ϊ������־�洢����
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(��ת�����ҳ��) 
	* @return String    �������ͣ���תָ�� 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String toAdd() throws Exception{

		//����ָ��
		return "form";
	}
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 * --Logor����־��¼���ã�����Ϊ������־�洢����
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(��Ӳ���) 
	* @return String    �������ͣ���תָ�� 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String add() throws Exception{
		//�������
		po.setId(null);
		po.str2Dt();
		String id = busi.save(po,
				new File[][]{this.getUpload(),new File[]{this.getUploadSingle()}},
				new String[][]{this.getUploadFileName(),new String[]{this.getUploadSingleFileName()}},
				null);
		String rlt = "";		
		boolean succ = false;
		if(id != null && !id.equals("")){
			rlt = "��Ӽ�¼�ɹ�!";
			succ = true;
		}else{
			rlt = "��Ӽ�¼ʧ��!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//����ָ��
		return null;
	}
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 * --Logor����־��¼���ã�����Ϊ������־�洢����
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(��ת�����ҳ��) 
	* @return String    �������ͣ���תָ�� 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String toEdit() throws Exception{
		po = busi.getById(param_id);

		this.getActionProperties(this.getClass(), null);
		//����ָ��
		return "form";
	}
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 * --Logor����־��¼���ã�����Ϊ������־�洢����
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(��Ӳ���) 
	* @return String    �������ͣ���תָ�� 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String edit() throws Exception{		
		String id = busi.save(po,
				new File[][]{this.getUpload(),new File[]{this.getUploadSingle()}},
				new String[][]{this.getUploadFileName(),new String[]{this.getUploadSingleFileName()}},
				new String[]{this.getDel_fileIds(),this.getDel_fileId()});		
		String rlt = "";
		boolean succ = false;
		if(id != null && !id.equals("")){
			rlt = "��Ӽ�¼�ɹ�!";
			succ = true;
		}else{
			rlt = "��Ӽ�¼ʧ��!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//����ָ��
		return null;
	}
	
	/** 
	* @Title: validateForm 
	* @Description: TODO(����첽��֤) 
	* @return String    �������� ��true����֤ͨ����false����֤ʧ��
	* @throws 
	*/ 
	public String validateForm(){
		if(po != null){
			this.putParam("id", po.getId());
			this.putParam("cd", po.getCd());			
		}		
		responseXml(busi.validateForm(params),null);
		
		return null;
	}
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 * --Logor����־��¼���ã�����Ϊ������־�洢����
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(�鿴�������) 
	* @return String    �������ͣ���תָ�� 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String detail() throws Exception{
		po = busi.getById(param_id);	
		//����ָ��
		return "detail";
	}
	/**
	 * @return
	 * --CheckPriv��Ȩ����֤���ã�����Ϊ�����룬-1��ʾ���ܲ�������
	 * --Logor����־��¼���ã�����Ϊ������־�洢����
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(ɾ������) 
	* @return String    �������ͣ���תָ�� 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String delete(){
		String rlt = "";
		boolean succ = false;
		try{
			String[] ids = param_id.split(",");
			busi.delete(ids);
			rlt = "ɾ����¼�ɹ�!";
			succ = true;
		}catch(Exception e){
			rlt = "ɾ����¼ʧ��!";
		}finally{}		
		responseHtml(createAjaxResult(succ,rlt),null);
		//����ָ��
		return null;
	}
	
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getCon_deptName() {
		return con_deptName;
	}
	public void setCon_deptName(String con_deptName) {
		this.con_deptName = con_deptName;
	}
	public String getCon_name() {
		return con_name;
	}
	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}
	public String getTitleName1() {
		return titleName1;
	}
	public void setTitleName1(String titleName1) {
		this.titleName1 = titleName1;
	}
	
	public String getParam_id() {
		return param_id;
	}
	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}
	public TbDemoStudent getPo() {
		return po;
	}
	public void setPo(TbDemoStudent po) {
		this.po = po;
	}	
}
