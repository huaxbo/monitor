package com.automic.sso.util;

public class Util {
	/**
	 * �ж��Ƿ�������
	 * 
	 * @param str String
	 * @return boolean
	 */
	public boolean isIntNumber(String str) {
		// �ж��Ƿ�������
		if(str == null || str.trim().equals(""))
			return false ;
		
		if(str.startsWith("-")){
			str = str.substring(1 , str.length()) ;
		}
		for (int i = 0; i < str.length(); i++) {
			if (new String("9876543210").indexOf(str.substring(i, i + 1)) == -1) {
				return false;
			}
		}
		return true;
	}

}
