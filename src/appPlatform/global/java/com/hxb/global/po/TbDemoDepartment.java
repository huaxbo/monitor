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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/** 
 * @ClassName: PomfTbDemoDepartment 
 * @Description: TODO(��-����Ժϵ��POJO) 
 * @author huaXinbo
 * @date 2014-3-18 ����03:24:14 
 *  
 */
@Entity
@Table(name = "tb_demo_department")
public class TbDemoDepartment implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(���к�Ψһ��ʶ) 
	*/ 
	private static final long serialVersionUID = 2707081237936699163L;

	
	/** 
	* @Fields id : TODO(����id) 
	*/ 
	private String id;
	/** 
	* @Fields name : TODO(Ժϵ����) 
	*/ 
	private String name;
	/** 
	* @Fields marker : TODO(��ע��Ϣ) 
	*/ 
	private String marker;
	
	/** 
	* @Fields students : TODO(Ժϵ������ѧ����Ϣ) 
	*/ 
	private Set<TbDemoStudent> students = new HashSet<TbDemoStudent>(0);
	
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
	
	@Column(name="name",length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="marker",length=100)
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
	}
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,mappedBy="dept")	
	public Set<TbDemoStudent> getStudents() {
		return students;
	}
	public void setStudents(Set<TbDemoStudent> students) {
		this.students = students;
	}
	
	
	
}
