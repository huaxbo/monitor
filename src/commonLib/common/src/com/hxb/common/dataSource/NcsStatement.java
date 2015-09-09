package com.hxb.common.dataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface NcsStatement {
	public abstract ResultSet executeQuery(String paramString)
			throws SQLException;

	public abstract int executeUpdate(String paramString) throws SQLException;

	public abstract boolean execute(String paramString) throws SQLException;

	public abstract void close();
}