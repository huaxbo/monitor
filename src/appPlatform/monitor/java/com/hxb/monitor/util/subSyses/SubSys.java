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
	* @Fields sysMarker : TODO(功能图标css名称) 
	*/ 
	private String sysMarker;
	/** 
	* @Fields sysContext : TODO(子系统上下文名称) 
	*/ 
	private String sysContext;
	/** 
	* @Fields sysPath : TODO(资源地址,以"/"开头。默认为空) 
	*/ 
	private String sysPath;
	/** 
	* @Fields sysName : TODO(子系统标题) 
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
