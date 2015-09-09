/**   
* @Title: PomfTbDemoDepartment.java 
* @Package com.hxb.global.po 
* @Description: TODO(��һ�仰�������ļ���ʲô) 
* @author huaXinbo   
* @date 2014-3-18 ����03:24:14 
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
 * @Description: TODO(��-����ѧ����Ϣ��POJO) 
 * @author huaXinbo
 * @date 2014-3-18 ����03:24:14 
 *  
 */
@Entity
@Table(name = "tb_demo_student")
public class TbDemoStudent implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(���к�Ψһ��ʶ) 
	*/ 
	private static final long serialVersionUID = -6492639460391899964L;
	

	
	/** 
	* @Fields id : TODO(����id) 
	*/ 
	private String id;
	/** 
	* @Fields cd : TODO(ѧ�ţ�Ψһ) 
	*/ 
	private String cd;
	/** 
	* @Fields name : TODO(ѧ������) 
	*/ 
	private String name;
	/** 
	* @Fields age : TODO(ѧ������) 
	*/ 
	private Integer age;
	/** 
	* @Fields address : TODO(ѧ������) 
	*/ 
	private String address;
	
	/** 
	* @Fields dt : TODO(��ѧʱ�䣬��ʽ��yyyy-MM-dd) 
	*/ 
	private Date dt;
	
	/** 
	* @Fields deptId : TODO(�����������Ժϵid) 
	*/ 
	private String deptId;
	/** 
	* @Fields dempt : TODO(����Ժϵ����) 
	*/ 
	private TbDemoDepartment dept;
	
	private Set<TbDemoStuAttachment> attas = new HashSet<TbDemoStuAttachment>(0);
	
	//�����ݿ�����
	/** 
	* @Fields dt_str : TODO(��ѧ���ڣ���ʽ��yyyy-MM-dd) 
	*/ 
	private String dt_str;
	/** 
	* @Fields dept_name : TODO(Ժϵ����) 
	*/ 
	private String dept_name;
	
	/** 
	* @Title: fetch 
	* @Description: TODO(������������ץȡ) 
	* @return void    �������� 
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
