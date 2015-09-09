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
	 * action名称
	 */
	private String actionName = this.getClass().getSimpleName().substring(0,1).toLowerCase() 
		+ this.getClass().getSimpleName().substring(1);
	/**
	 * 标题名称.
	 */
	private String titleName = "当前位置：" 
			+ Config.getConfig("monitor.manager") 
			+ "&gt;&gt;" 
			+ Config.getConfig("monitor.manager.1")
			+ "&gt;&gt;";
	@Autowired
	private ServerBusi busi;
	/**
	 * 查询条件-通讯服务名称
	 */
	@PropertieType
	public String con_name;
	
	/**
	 * 参数-记录id
	 */
	private String param_id;
	
	/**
	 * 信息记录 
	 */
	private TbCsServer po;
	
	/**
	 * 进行访问方法声明，用以form提交请求
	 * **/
	
	
	/** 
	* @Title: initCondition 
	* @Description: TODO(初始化参数信息) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/ 
	private void initCondition() {
		// TODO Auto-generated method stub
		if(this.firstTime){
			//初始化参数
			
			this.firstRequest();
		}
	}
	
	/**
	 * 跳转至列表查询
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String toList() throws Exception{
		//查询参数构造，查询参数初始化
		initCondition();		
		this.getActionProperties(this.getClass(), null);				
		//返回指向
		return "list";
	}
	/**
	 * 获取table 数据集合
	 * @return
	 */
	public String list(){
		//查询参数注入
		injectCondition(this.getClass());
		//分页操作
		Long total = busi.getCount(params);
		pages(total);
		//业务层调用		
		TableDatas tds = new TableDatas();
		tds.setTotal(total);
		tds.bindDatas(busi.list(params));		
		JSONObject jobj = JSONObject.fromObject(tds);
		responseHtml(jobj.toString(), null);
		
		return null;
	}
	
	/**
	 * 跳转至添加页面
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String toAdd() throws Exception{

		//返回指向
		return "form";
	}
	
	/**
	 * 添加操作
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String add() throws Exception{
		//保存对象
		po.setId(null);
		String id = busi.save(po);
		String rlt = "";		
		boolean succ = false;
		if(id != null && !id.equals("")){
			rlt = "记录添加成功!";
			succ = true;
		}else{
			rlt = "记录添加失败!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//返回指向
		return null;
	}

	/**
	 * 跳转至编辑页面
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String toEdit() throws Exception{
		po = busi.getById(param_id);

		this.getActionProperties(this.getClass(), null);
		//返回指向
		return "form";
	}
	
	/**
	 * 编辑操作
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String edit() throws Exception{		
		String id = busi.save(po);		
		String rlt = "";
		boolean succ = false;
		if(id != null && !id.equals("")){
			rlt = "记录编辑成功!";
			succ = true;
		}else{
			rlt = "记录编辑失败!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//返回指向
		return null;
	}
	
	/**
	 * 查看详情
	 * @return
	 * @throws Exception
	 */
//	@CheckPriv("-1")
	public String detail() throws Exception{
		po = busi.getById(param_id);	
		//返回指向
		return "detail";
	}

	/**
	 * 删除记录
	 * @return
	 */
//	@CheckPriv("-1")
	public String delete(){
		String rlt = "";
		boolean succ = false;
		try{
			String[] ids = param_id.split(",");
			busi.delete(ids);
			rlt = "记录删除成功!";
			succ = true;
		}catch(Exception e){
			rlt = "记录删除失败!";
		}finally{}		
		responseHtml(createAjaxResult(succ,rlt),null);
		//返回指向
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
