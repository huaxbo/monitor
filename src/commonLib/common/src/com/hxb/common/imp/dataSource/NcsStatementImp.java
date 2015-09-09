package com.hxb.common.imp.dataSource;

import com.hxb.common.dataSource.NcsStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class NcsStatementImp implements NcsStatement {
	private Statement statement;

	public NcsStatementImp(Statement statement) {
		this.statement = statement;
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return this.statement.executeQuery(sql);
	}

	public int executeUpdate(String sql) throws SQLException {
		return this.statement.executeUpdate(sql);
	}

	public boolean execute(String sql) throws SQLException {
		return this.statement.execute(sql);
	}

	public void close() {
		try {
			if (this.statement != null) {
				this.statement.close();
				this.statement = null;
				Logger log = Logger.getLogger(NcsStatementImp.class.getName());
				log.info("正确关闭了一个数据库会话.");
			}
		} catch (Exception e) {
			try {
				if (this.statement != null)
					this.statement.close();
			} catch (Exception ee) {
				Logger log = Logger.getLogger(NcsStatementImp.class.getName());
				log.info("出错，不能关闭一个数据库会话.");
			}
		}
	}
}