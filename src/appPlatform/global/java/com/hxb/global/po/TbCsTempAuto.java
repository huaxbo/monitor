package com.hxb.global.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hxb.util.DateTime;

/**
 * @author Administrator
 * 通讯成果临时表
 */
@Entity
@Table(name = "tb_cs_tempauto")
public class TbCsTempAuto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823621484194787132L;
	
	/**
	 * 
	 */
	public TbCsTempAuto(){}
	
	/**
	 * @param commandId
	 * @param data
	 */
	public TbCsTempAuto(String data){
		this.data = data;
		this.ts = DateTime.yyyy_MM_dd_HH_mm_ss();
	}
	/**
	 * 主键id
	 */
	private String id;
	/**
	 * 上报数据
	 */
	private String data;
	/**
	 * 时间戳
	 * 表示数据存储时间
	 */
	private String ts;
	
	@Id
	@GenericGenerator(name="hxbIDG",strategy="com.hxb.util.IDGenerator")
	@GeneratedValue(generator="hxbIDG")
	@Column(name="id",unique=true,nullable=false,length=17)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="data",length=2000)
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Column(name="ts",length=20)
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}	
	
}
