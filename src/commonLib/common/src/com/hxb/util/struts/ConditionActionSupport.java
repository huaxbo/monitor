package com.hxb.util.struts;

import java.lang.reflect.Field;
import java.util.*;

public abstract class ConditionActionSupport  extends SysActionSupport {

	private static final long serialVersionUID = 190001011231231L;
	
	//������ģ�鴫�ͱ��ύ�����Ĳ�ѯ����
	public Hashtable<String , Object> params = new Hashtable<String , Object>() ;

	/** 
	* @Fields con_region1 : TODO(1��������adcd) 
	*/ 
	@PropertieType
	public String con_region1;
	/** 
	* @Fields con_region2 : TODO(2��������adcd) 
	*/ 
	@PropertieType
	public String con_region2;
	/** 
	* @Fields con_region3 : TODO(3��������adcd) 
	*/ 
	@PropertieType
	public String con_region3;
	/**
	 * �Զ�ע���ѯ��������
	 */
	protected void injectCondition(Class<?> clazz){
		if(clazz != null){
			Field[] fds = clazz.getFields();
			if(fds != null){
				for(Field fd : fds){
					String nm = fd.getName();
					try {
						if(nm.startsWith("con_")){//��ѯ��������
							//�����ѯ����key-value
							this.putParam(nm, fd.get(this));
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{}
				}
			}
		}
	}
		
	public Hashtable<String, Object> getParams() {
		return params;
	}

	public void setParams(Hashtable<String, Object> params) {
		this.params = params;
	}
	
	public void putParam(String key , Object value){
		if(key == null || value == null){
			return ;
		}
		this.params.put(key , value) ;
	}

	public String getCon_region1() {
		return con_region1;
	}

	public void setCon_region1(String con_region1) {
		this.con_region1 = con_region1;
	}

	public String getCon_region2() {
		return con_region2;
	}

	public void setCon_region2(String con_region2) {
		this.con_region2 = con_region2;
	}

	public String getCon_region3() {
		return con_region3;
	}

	public void setCon_region3(String con_region3) {
		this.con_region3 = con_region3;
	}
	

}
