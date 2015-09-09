package com.hxb.common.imp.util;

public class Util {
	public boolean isIntNumber(String str) {
		if ((str == null) || (str.trim().equals(""))) {
			return false;
		}
		if (str.startsWith("-")) {
			str = str.substring(1, str.length());
		}
		for (int i = 0; i < str.length(); i++) {
			if (new String("9876543210").indexOf(str.substring(i, i + 1)) == -1) {
				return false;
			}
		}
		return true;
	}
}