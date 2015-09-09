package com.hxb.global.service.log;

import com.hxb.common.sso.session.SessionUserVO;

/**
 * 
 * 父类SessionUserVO定义了以下必须属性：
 * 	protected String name;//帐号
 * 	protected String password;//密码
 * 	protected String fixedIP ;//用户常用(固定IP)
 * 	protected String[] powers;
 * 	protected Hashtable<String , String> attributes ;
 * 这些属性是单点登录控制所必须的。
 * 
 * 如果在SessionUserVO类属性基础上，还需要其他属性，可认自行定义必。
 * 但是后定义的属性在单点登录控制中的用户VO中不能以属性方式得到，
 * 可以把这些属性以名值对的方式放在SessionUserVO类的attributes集合
 * 属性中，在需要这些属性值的地方以以下例子方式得到。
 * 	 java.util.Hashtable<String, String> t = vo.getAttributes() ;
 * 	 if(t != null){
 * 	 	String roleName = (String)t.get("roleName") ;
 * 	 }
 * 
 * 最后需要注意的地方：
 * 本类要放值在项目级公共包 AMSsoUser.jar中。
 *
 */
public class UserVO extends SessionUserVO {
	
	protected static final long serialVersionUID = -8095863075964700786L;

	private String id ;
	private String nameCN ;
	private String isAdmin ;
	private String roleId ;
	private String roleName ;
	private String orgId;//部门id
	////////////////////////
	//非数据库属性
	private String currIp ;
	private String ssoSessionId ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNameCN() {
		return nameCN;
	}
	public void setNameCN(String nameCN) {
		this.nameCN = nameCN;
	}
	public String getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getCurrIp() {
		return currIp;
	}
	public void setCurrIp(String currIp) {
		this.currIp = currIp;
	}
	public String getSsoSessionId() {
		return ssoSessionId;
	}
	public void setSsoSessionId(String ssoSessionId) {
		this.ssoSessionId = ssoSessionId;
	}	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
