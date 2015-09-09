package com.hxb.monitor.web.remote.online;


import net.sf.json.JSONObject;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hxb.global.common.tb.TableDatas;
import com.hxb.util.Config;
import com.hxb.util.struts.PageYMDActionSupport;
import com.hxb.util.struts.PropertieType;


@Aspect
@Controller
@Scope("request")
public class OnlineAction extends PageYMDActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 747330845009570085L;
	/**
	 * action����
	 */
	private String actionName = this.getClass().getSimpleName().substring(0,1).toLowerCase() 
		+ this.getClass().getSimpleName().substring(1);
	/**
	 * ��������.
	 */
	private String titleName = "��ǰλ�ã�" 
			+ Config.getConfig("monitor.remote") 
			+ "&gt;&gt;" 
			+ Config.getConfig("monitor.remote.1")
			+ "&gt;&gt;";
	@Autowired
	private OnlineBusi busi;
	/**
	 * ��ѯ����-����豸����
	 */
	@PropertieType
	public String con_name;
	
	/**
	 * ����-��¼id
	 */
	private String param_id;
	
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
	
	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
}
