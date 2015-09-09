package com.hxb.common.sso.session;

import java.util.Hashtable;

public interface SessionUserInterface {
	
	public String getName();

	public String getPassword();

	public String getFixedIP();

	public String[] getPowers();
	
	public Hashtable<String, String> getAttributes();

}
