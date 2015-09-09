package com.hxb.common.imp.dataSource;

import com.hxb.common.dataSource.NcsConnection;
import com.hxb.common.dataSource.NcsDataSource;
import com.hxb.common.imp.dataSource.pool.NcsDataSourceManager;

import java.sql.Connection;

public class NcsDataSourceImp implements NcsDataSource {
	private String connectPoolName;

	public NcsDataSourceImp(String dataSourceName) {
		this.connectPoolName = dataSourceName;
	}

	public NcsConnection getNcsConnetion(long time) {
		Connection c = NcsDataSourceManager.getInstance().getConnection(
				this.connectPoolName, time);
		if (c == null) {
			return null;
		}
		NcsConnectionImp cimp = new NcsConnectionImp(this.connectPoolName, c);
		return cimp;
	}

	public NcsConnection getNcsConnetion() {
		Connection c = NcsDataSourceManager.getInstance().getConnection(
				this.connectPoolName);
		if (c == null) {
			return null;
		}
		NcsConnectionImp cimp = new NcsConnectionImp(this.connectPoolName, c);
		return cimp;
	}

	public void destroyDataSource() {
		NcsDataSourceManager.getInstance().release();
	}
}