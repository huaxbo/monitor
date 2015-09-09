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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/** 
 * @ClassName: PomfTbDemoDepartment 
 * @Description: TODO(表-测试学生附件信息表POJO) 
 * @author huaXinbo
 * @date 2014-3-18 下午03:24:14 
 *  
 */
@Entity
@Table(name = "tb_demo_stuAttachment")
public class TbDemoStuAttachment implements Serializable {
	

	
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -4840919916605336966L;
	/** 
	* @Fields id : TODO(主键id) 
	*/ 
	private String id;
	/** 
	* @Fields name : TODO(附件名称) 
	*/ 
	private String name;
	/** 
	* @Fields attaUrl : TODO(附件地址) 
	*/ 
	private String attaUrl;
	
	/** 
	* @Fields attaType : TODO(附件类型：1：单附件、2或者null：多附件) 
	*/ 
	private String attaType;
	/** 
	* @Fields deptId : TODO(外键：：所在院系id) 
	*/ 
	private String studentId;
	/** 
	* @Fields dempt : TODO(所在院系对象) 
	*/ 
	private TbDemoStudent student;
	
		
		
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="studentId",insertable=false,updatable=false)
	public TbDemoStudent getStudent() {
		return student;
	}
	public void setStudent(TbDemoStudent student) {
		this.student = student;
	}
	
	@Column(name="studentId",length=16)
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Column(name="name",length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="attaUrl",length=200)
	public String getAttaUrl() {
		return attaUrl;
	}

	public void setAttaUrl(String attaUrl) {
		this.attaUrl = attaUrl;
	}
	
	@Column(name="attaType",length=2)
	public String getAttaType() {
		return attaType;
	}
	public void setAttaType(String attaType) {
		this.attaType = attaType;
	}
	
}
