package com.hxb.common.dataSource;

public class DbConfigVO {
	public String dataSourceName;
	public String driverClass;
	public String url;
	public String user;
	public String password;
	public int maxCount;
	public int minCount;
	public boolean autoCommit;
	public boolean needTestConnection;
	public int timeConNeedTest;
}