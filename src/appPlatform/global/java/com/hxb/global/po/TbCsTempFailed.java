package com.hxb.global.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Administrator
 * ͨѶ�ɹ���ʱ��
 */
@Entity
@Table(name = "tb_cs_tempfailed")
public class TbCsTempFailed  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823621484194787132L;
	
	/**
	 * 
	 */
	public TbCsTempFailed(){}
	
	/**
	 * @param commandId
	 * @param data
	 */
	public TbCsTempFailed(String commandId,String data,String ts){
		this.commandId = commandId;
		this.data = data;
		this.ts = ts;
	}
	/**
	 * ����id
	 */
	private String id;
	/**
	 * ����id��
	 * Ϊ�ձ�ʾΪ����������
	 */
	private String commandId;
	/**
	 * �ϱ�����
	 */
	private String data;
	/**
	 * ʱ���
	 * ��ʾ���ݴ洢ʱ��
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
	@Column(name="commandId",length=17)
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
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
