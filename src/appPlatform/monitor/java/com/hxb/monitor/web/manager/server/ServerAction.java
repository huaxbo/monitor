package com.hxb.monitor.web.manager.server;


import net.sf.json.JSONObject;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hxb.global.common.tb.TableDatas;
import com.hxb.global.po.TbCsServer;
import com.hxb.util.Config;
import com.hxb.util.struts.PageYMDActionSupport;
import com.hxb.util.struts.PropertieType;


@Aspect
@Controller
@Scope("request")
public class ServerAction extends PageYMDActionSupport {
	
	private static final long serialVersionUID = 8885153382815424888L;
	/**
	 * action����
	 */
	private String actionName = this.getClass().getSimpleName().substring(0,1).toLowerCase() 
		+ this.getClass().getSimpleName().substring(1);
	/**
	 * ��������.
	 */
	private String titleName = "��ǰλ�ã�" 
			+ Config.getConfig("monitor.manager") 
			+ "&gt;&gt;" 
			+ Config.getConfig("monitor.manager.1")
			+ "&gt;&gt;";
	@Autowired
	private ServerBusi busi;
	/**
	 * ��ѯ����-ͨѶ��������
	 */
	@PropertieType
	public String con_name;
	
	/**
	 * ����-��¼id
	 */
	private String param_id;
	
	/**
	 * ��Ϣ��¼ 
	 */
	private TbCsServer po;
	
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
	 * ��ת���б��ѯ
	 * @return
	 * @throws Exception
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
		Long total = busi.getCount(params);
		pages(total);
		//ҵ������		
		TableDatas tds = new TableDatas();
		tds.setTotal(total);
		tds.bindDatas(busi.list(params));		
		JSONObject jobj = JSONObject.fromObject(tds);
		responseHtml(jobj.toString(), null);
		
		return null;
	}
	
	/**
	 * ��ת�����ҳ��
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String toAdd() throws Exception{

		//����ָ��
		return "form";
	}
	
	/**
	 * ��Ӳ���
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String add() throws Exception{
		//�������
		po.setId(null);
		String id = busi.save(po);
		String rlt = "";		
		boolean succ = false;
		if(id != null && !id.equals("")){
			rlt = "��¼��ӳɹ�!";
			succ = true;
		}else{
			rlt = "��¼���ʧ��!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//����ָ��
		return null;
	}

	/**
	 * ��ת���༭ҳ��
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String toEdit() throws Exception{
		po = busi.getById(param_id);

		this.getActionProperties(this.getClass(), null);
		//����ָ��
		return "form";
	}
	
	/**
	 * �༭����
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String edit() throws Exception{		
		String id = busi.save(po);		
		String rlt = "";
		boolean succ = false;
		if(id != null && !id.equals("")){
			rlt = "��¼�༭�ɹ�!";
			succ = true;
		}else{
			rlt = "��¼�༭ʧ��!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//����ָ��
		return null;
	}
	
	/**
	 * �鿴����
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String detail() throws Exception{
		po = busi.getById(param_id);	
		//����ָ��
		return "detail";
	}

	/**
	 * ɾ����¼
	 * @return
	 */
//	@CheckPriv("-1")
	public String delete(){
		String rlt = "";
		boolean succ = false;
		try{
			String[] ids = param_id.split(",");
			busi.delete(ids);
			rlt = "��¼ɾ���ɹ�!";
			succ = true;
		}catch(Exception e){
			rlt = "��¼ɾ��ʧ��!";
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
	
	public String getCon_name() {
		return con_name;
	}
	public void setCon_name(String con_name) {
		this.con_name = con_name;
	}
	public String getTitleName1() {
		return titleName;
	}
	public void setTitleName1(String titleName1) {
		this.titleName = titleName1;
	}
	
	public String getParam_id() {
		return param_id;
	}
	public void setParam_id(String param_id) {
		this.param_id = param_id;
	}
	public TbCsServer getPo() {
		return po;
	}
	public void setPo(TbCsServer po) {
		this.po = po;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}	
}
