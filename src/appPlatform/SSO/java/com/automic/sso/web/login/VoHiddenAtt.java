package com.automic.sso.web.login;

import java.util.*;
import com.automic.sso.session.UserVO ;

public class VoHiddenAtt {

	/**
	 * @param vo
	 * @return
	 */
	public UserVO setSessionUserVOHiddenAtt(UserVO vo){
		Hashtable<String , String> t = vo.getAttributes() ;
		t.put("id", vo.getId()) ;
		if(vo.getNameCN() != null){
			t.put("nameCN", vo.getNameCN()) ;
		}
		if(vo.getRoleId() != null){
			t.put("roleId", vo.getRoleId()) ;
		}
		if(vo.getRoleName() != null){
			t.put("roleName", vo.getRoleName()) ;
		}
		if(vo.getOrgId() != null){
			t.put("orgId", vo.getOrgId());
		}
		return vo ;
	}
}
