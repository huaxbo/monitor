package com.hxb.global.service.log;

import com.hxb.common.sso.session.SessionUserVO;

/**
 * 
 * ����SessionUserVO���������±������ԣ�
 * 	protected String name;//�ʺ�
 * 	protected String password;//����
 * 	protected String fixedIP ;//�û�����(�̶�IP)
 * 	protected String[] powers;
 * 	protected Hashtable<String , String> attributes ;
 * ��Щ�����ǵ����¼����������ġ�
 * 
 * �����SessionUserVO�����Ի����ϣ�����Ҫ�������ԣ��������ж���ء�
 * ���Ǻ���������ڵ����¼�����е��û�VO�в��������Է�ʽ�õ���
 * ���԰���Щ��������ֵ�Եķ�ʽ����SessionUserVO���attributes����
 * �����У�����Ҫ��Щ����ֵ�ĵط����������ӷ�ʽ�õ���
 * 	 java.util.Hashtable<String, String> t = vo.getAttributes() ;
 * 	 if(t != null){
 * 	 	String roleName = (String)t.get("roleName") ;
 * 	 }
 * 
 * �����Ҫע��ĵط���
 * ����Ҫ��ֵ����Ŀ�������� AMSsoUser.jar�С�
 *
 */
public class UserVO extends SessionUserVO {
	
	protected static final long serialVersionUID = -8095863075964700786L;

	private String id ;
	private String nameCN ;
	private String isAdmin ;
	private String roleId ;
	private String roleName ;
	private String orgId;//����id
	////////////////////////
	//�����ݿ�����
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
