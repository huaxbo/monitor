/**
 * 
 */
package com.hxb.monitor.util.subSyses;

/**
 * @author Administrator
 *
 */
public class SubSys {

	/** 
	* @Fields sysMarker : TODO(����ͼ��css����) 
	*/ 
	private String sysMarker;
	/** 
	* @Fields sysContext : TODO(��ϵͳ����������) 
	*/ 
	private String sysContext;
	/** 
	* @Fields sysPath : TODO(��Դ��ַ,��"/"��ͷ��Ĭ��Ϊ��) 
	*/ 
	private String sysPath;
	/** 
	* @Fields sysName : TODO(��ϵͳ����) 
	*/ 
	private String sysTitle;
	
	
	public String getSysContext() {
		return sysContext;
	}
	public void setSysContext(String sysContext) {
		this.sysContext = sysContext;
	}
	public String getSysTitle() {
		return sysTitle;
	}
	public void setSysTitle(String sysTitle) {
		this.sysTitle = sysTitle;
	}
	public String getSysMarker() {
		return sysMarker;
	}
	public void setSysMarker(String sysMarker) {
		this.sysMarker = sysMarker;
	}
	public String getSysPath() {
		return sysPath;
	}
	public void setSysPath(String sysPath) {
		this.sysPath = sysPath;
	}
}
