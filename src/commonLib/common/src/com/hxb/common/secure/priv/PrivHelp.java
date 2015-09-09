package com.hxb.common.secure.priv;

import java.lang.reflect.Method;

public class PrivHelp {
	
	/**
	 * 查验value权限配置
	 * @param m
	 * @param powers
	 * @return
	 */
	public boolean checkValue(Method m , String powers[] , String privs_value){
		boolean proceed = false ;
		if(privs_value != null && !privs_value.equals("")){
			for(String gap:powers){
				if(privs_value.equals(gap)){
					proceed = true ;
					break ;
				}
			}
		}
		return proceed ;
	}
	
	/**
	 * 查验ifAll权限配置
	 * @param m
	 * @param powers
	 * @param userIndirectPowers
	 * @return
	 */
	public boolean checkIfAll(Method m , String powers[] , String privs_ifAll[]){
		boolean proceed = false ;
		if(privs_ifAll != null && privs_ifAll.length > 0 && !privs_ifAll[0].equals("")){
			boolean flag = false ;
			for(String priv: privs_ifAll){
				flag = false ;
				for(String gap:powers){
					if(priv.equals(gap)){
						flag = true ;
						break ;
					}
				}
				if(!flag){
					break ;
				}
			}
			if(flag){
				proceed = true;
			}
		}
		return proceed ;
	}
	/**
	 * 查验ifAny权限配置
	 * @param m
	 * @param powers
	 * @param userIndirectPowers
	 * @return
	 */
	public boolean checkIfAny(Method m , String powers[] , String privs_ifAny[]){
		boolean proceed = false ;

		if(privs_ifAny != null && privs_ifAny.length > 0 && !privs_ifAny[0].equals("")){
			for(String priv: privs_ifAny){
				for(String gap:powers){
					if(priv.equals(gap)){
						proceed = true ;
						break ;
					}
				}
				if(proceed){
					break;
				}
			}
		}
		return proceed ;
	}
}
