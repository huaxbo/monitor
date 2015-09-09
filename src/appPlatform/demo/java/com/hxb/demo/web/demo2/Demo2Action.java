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
 * --Aspect：设置springAOP,以实现切面方式权限验证、操作日志存储功能
 * --Controller：设置spring annotation方式注入Action，参数值为Action的注入id，供struts配置文件中指向spring调用
 * --Scope：设置为注入对象作用范围，设置request有效
 */
@Aspect
@Controller("demo2Action")
@Scope("request")
public class Demo2Action extends PageYMDActionSupport {

	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -7301386938142197364L;
	/**
	 * 获取log4j日志对象
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 标题名称.填写格式：一级目录目录>>二级目录>>三级目录
	 */
	private String titleName1 = "当前位置：样例>样例2>";
	/**
	 * 声明查询条件：变量命名规则：con_[conditionName];
	 * 必须使用public类型
	 * 变量必须生成getter、setter方法，借以支持struts2中form数据接收
	 * */
	/**
	 * 查询条件-[院系名称]
	 */
	@PropertieType
	public String con_deptName;
	/**
	 * 查询条件-[学生姓名]
	 */
	@PropertieType
	public String con_name;
	
	/** 
	* @Fields param_id : TODO(参数：记录id) 
	*/ 
	private String param_id;
	
	/**
	 * 声明busi层对象
	 * --Autowired：spring annotation方式注入对象。
	 * 此属性不必生成setter、getter方法，annotation方式注入已经做处理
	 * 注意：此对象必须添加注释选项@Service进行注入。且此类型必须整个context中没有重复类型
	 */
	@Autowired
	private Demo2Busi busi;
	
	/**
	 * 声明返回结果：变量命名规则：rlt_[resultName]
	 * 变量必须生成getter、setter方法，借以支持struts2中页面数据解析
	 * **/
	/**
	 * 返回结果-[action名称]
	 */
	private String actionName = this.getClass().getSimpleName().substring(0,1).toLowerCase() 
		+ this.getClass().getSimpleName().substring(1);
		
	/**
	 * 返回结果-[返回结果说明]
	 */
	private List<Object> rlt_list;
	/** 
	* @Fields po : TODO(学生信息) 
	*/ 
	private TbDemoStudent po;
	
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
	 * @return
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
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
		Long total = busi.getCount(params, "单值传递参数");
		pages(total);
		//业务层调用
		rlt_list = busi.list(params,"单值传递参数");
		
		TableDatas tds = new TableDatas();
		tds.setTotal(total);
		tds.bindDatas(rlt_list);		
			
		
		JSONObject jobj = JSONObject.fromObject(tds);
		responseHtml(jobj.toString(), null);
		
		return null;
	}
	
	
	/**
	 * @return
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
	 */
//	@CheckPriv("-1")
	public String excel() throws Exception{
		//查询参数构造，查询参数初始化
		initCondition();
		//查询参数注入
		injectCondition(this.getClass());
		//业务层调用
		rlt_list = busi.list(params,"单值传递参数");
		//excel文件名称，不用带扩展名。默认使用xls作为文件扩展名
		setExcelName("导出样例");
		//返回指向
		return "excel";
	}
	
	/**
	 * @throws Exception  
	* @Title: impExcel 
	* @Description: TODO(导入excel) 
	* @return String    返回类型 
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
				setRlt_message("上传文件不是合法的ecxel！");
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	
		return list();
	}
	/**
	 * @return
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
	 * --Logor：日志记录设置，参数为操作日志存储内容
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(跳转至添加页面) 
	* @return String    返回类型：跳转指向 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String toAdd() throws Exception{

		//返回指向
		return "form";
	}
	/**
	 * @return
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
	 * --Logor：日志记录设置，参数为操作日志存储内容
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(添加操作) 
	* @return String    返回类型：跳转指向 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String add() throws Exception{
		//保存对象
		po.setId(null);
		po.str2Dt();
		String id = busi.save(po,
				new File[][]{this.getUpload(),new File[]{this.getUploadSingle()}},
				new String[][]{this.getUploadFileName(),new String[]{this.getUploadSingleFileName()}},
				null);
		String rlt = "";		
		boolean succ = false;
		if(id != null && !id.equals("")){
			rlt = "添加记录成功!";
			succ = true;
		}else{
			rlt = "添加记录失败!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//返回指向
		return null;
	}
	/**
	 * @return
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
	 * --Logor：日志记录设置，参数为操作日志存储内容
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(跳转至添加页面) 
	* @return String    返回类型：跳转指向 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String toEdit() throws Exception{
		po = busi.getById(param_id);

		this.getActionProperties(this.getClass(), null);
		//返回指向
		return "form";
	}
	/**
	 * @return
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
	 * --Logor：日志记录设置，参数为操作日志存储内容
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(添加操作) 
	* @return String    返回类型：跳转指向 
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
			rlt = "添加记录成功!";
			succ = true;
		}else{
			rlt = "添加记录失败!";
		}
		
		responseHtml(createAjaxResult(succ,rlt),null);
		//返回指向
		return null;
	}
	
	/** 
	* @Title: validateForm 
	* @Description: TODO(表的异步验证) 
	* @return String    返回类型 。true：验证通过、false：验证失败
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
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
	 * --Logor：日志记录设置，参数为操作日志存储内容
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(查看详情操作) 
	* @return String    返回类型：跳转指向 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String detail() throws Exception{
		po = busi.getById(param_id);	
		//返回指向
		return "detail";
	}
	/**
	 * @return
	 * --CheckPriv：权限验证设置，参数为功能码，-1表示功能不受限制
	 * --Logor：日志记录设置，参数为操作日志存储内容
	 */	
	/** 
	* @Title: toAdd 
	* @Description: TODO(删除操作) 
	* @return String    返回类型：跳转指向 
	* @throws 
	*/ 
//	@CheckPriv("-1")
	public String delete(){
		String rlt = "";
		boolean succ = false;
		try{
			String[] ids = param_id.split(",");
			busi.delete(ids);
			rlt = "删除记录成功!";
			succ = true;
		}catch(Exception e){
			rlt = "删除记录失败!";
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
