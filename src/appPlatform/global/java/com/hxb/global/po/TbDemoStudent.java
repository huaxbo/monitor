/**   
* @Title: PomfTbDemoDepartment.java 
* @Package com.hxb.global.po 
* @Description: TODO(用一句话描述该文件做什么) 
* @author huaXinbo   
* @date 2014-3-18 下午03:24:14 
* @version V1.0   
*/ 
package com.hxb.global.po;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.hxb.util.DateTime;


/** 
 * @ClassName: PomfTbDemoDepartment 
 * @Description: TODO(表-测试学生信息表POJO) 
 * @author huaXinbo
 * @date 2014-3-18 下午03:24:14 
 *  
 */
@Entity
@Table(name = "tb_demo_student")
public class TbDemoStudent implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(序列号唯一标识) 
	*/ 
	private static final long serialVersionUID = -6492639460391899964L;
	

	
	/** 
	* @Fields id : TODO(主键id) 
	*/ 
	private String id;
	/** 
	* @Fields cd : TODO(学号：唯一) 
	*/ 
	private String cd;
	/** 
	* @Fields name : TODO(学生姓名) 
	*/ 
	private String name;
	/** 
	* @Fields age : TODO(学生年龄) 
	*/ 
	private Integer age;
	/** 
	* @Fields address : TODO(学生籍贯) 
	*/ 
	private String address;
	
	/** 
	* @Fields dt : TODO(入学时间，格式：yyyy-MM-dd) 
	*/ 
	private Date dt;
	
	/** 
	* @Fields deptId : TODO(外键：：所在院系id) 
	*/ 
	private String deptId;
	/** 
	* @Fields dempt : TODO(所在院系对象) 
	*/ 
	private TbDemoDepartment dept;
	
	private Set<TbDemoStuAttachment> attas = new HashSet<TbDemoStuAttachment>(0);
	
	//非数据库属性
	/** 
	* @Fields dt_str : TODO(入学日期，格式：yyyy-MM-dd) 
	*/ 
	private String dt_str;
	/** 
	* @Fields dept_name : TODO(院系名称) 
	*/ 
	private String dept_name;
	
	/** 
	* @Title: fetch 
	* @Description: TODO(关联对象数据抓取) 
	* @return void    返回类型 
	* @throws 
	*/ 
	public void fetch(TbDemoDepartment dept){
		if(dept != null){
			dept_name = dept.getName();
		}
	}
	
	/**
	 * string -> date
	 */
	public void str2Dt(){
		if(dt_str != null && !dt_str.equals("")){
			try {
				dt = DateTime.dateFrom_yyyy_MM_dd(dt_str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{}
		}
	}
	/**
	 * date -> string
	 */
	public void dt2Str(){
		if(dt != null){
			dt_str = DateTime.yyyy_MM_dd(dt);
		}
		
	}
	
	@Id
	@GenericGenerator(name="hxbIDG",strategy="com.hxb.util.IDGenerator")
	@GeneratedValue(generator="hxbIDG")
	@Column(name="id",unique=true,nullable=false,length=16)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="name",length=20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="age")
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Column(name="address",length=100)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="deptId",insertable=false,updatable=false)
	public TbDemoDepartment getDept() {
		return dept;
	}
	public void setDept(TbDemoDepartment dept) {
		this.dept = dept;
	}
	
	@Column(name="deptId",length=16)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	@Column(name="cd",length=20)
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	@Column(name="dt")
	@Temporal(TemporalType.DATE)
	public Date getDt() {
		if(dt == null && dt_str != null){
			dt = DateTime.yyyy_MM_dd(dt_str);
		}
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
	
	@Transient
	public String getDt_str() {
		if(dt != null){
			dt_str = DateTime.yyyy_MM_dd(dt);
		}
		return dt_str;
	}
	public void setDt_str(String dt_str) {
		this.dt_str = dt_str;
	}
	
	@Transient
	public String getDept_name() {
		if(dept != null){
			dept_name = dept.getName();
		}
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,mappedBy="student")	
	public Set<TbDemoStuAttachment> getAttas() {
		return attas;
	}

	public void setAttas(Set<TbDemoStuAttachment> attas) {
		this.attas = attas;
	}
		
	
	
	
	
}
