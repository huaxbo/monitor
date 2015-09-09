package com.hxb.common.imp.dataSource;

import com.hxb.common.dataSource.NcsConnection;
import com.hxb.common.dataSource.NcsStatement;
import com.hxb.common.imp.dataSource.pool.NcsDataSourceManager;

import java.sql.Connection;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class NcsConnectionImp implements NcsConnection {
	private Connection connection;
	private String connectPoolName;

	public NcsConnectionImp(String connectPoolName, Connection connection) {
		this.connectPoolName = connectPoolName;
		this.connection = connection;
	}

	public NcsStatement getNcsStatement() {
		try {
			Statement st = this.connection.createStatement();
			NcsStatementImp ncs = new NcsStatementImp(st);
			return ncs;
		} catch (Exception e) {
			Logger log = Logger.getLogger(NcsConnectionImp.class.getName());
			log.error("创建数据库会话出错:\n", e);
		}
		return null;
	}

	public boolean isClosed() {
		try {
			return this.connection.isClosed();
		} catch (Exception e) {
		}
		return true;
	}

	public void commit() {
		try {
			this.connection.commit();
		} catch (Exception e) {
			Logger log = Logger.getLogger(NcsConnectionImp.class.getName());
			log.error("提交数据库事务出错!\n", e);
		}
	}

	public void setAutoCommit(boolean flag) {
		try {
			this.connection.setAutoCommit(flag);
		} catch (Exception e) {
			Logger log = Logger.getLogger(NcsConnectionImp.class.getName());
			log.error("设置数据库事务提交类型出错!\n", e);
		}
	}

	public void rollback() {
		try {
			this.connection.rollback();
		} catch (Exception e) {
			Logger log = Logger.getLogger(NcsConnectionImp.class.getName());
			log.error("回滚数据库事务出错!\n", e);
		}
	}

	public void free() {
		NcsDataSourceManager.getInstance().freeConnection(this.connectPoolName,
				this.connection);
		this.connection = null;
	}

	public void close() {
		try {
			this.connection.close();
		} catch (Exception e) {
			this.connection = null;
		}
	}
}