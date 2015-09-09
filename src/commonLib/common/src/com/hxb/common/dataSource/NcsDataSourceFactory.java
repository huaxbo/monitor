package com.hxb.common.dataSource;

import com.hxb.ACException;
import com.hxb.common.imp.dataSource.NcsDataSourceImp;
import com.hxb.common.imp.dataSource.pool.NcsDataSourceManager;

public class NcsDataSourceFactory {
	private static NcsDataSourceManager manager;

	public static void init(DbConfigVO[] vos) throws ACException {
		if (manager != null) {
			throw new ACException("数据源工厂不能重复初始化！");
		}
		if (vos == null) {
			throw new ACException("数据源初始化参数对象为空！");
		}
		manager = NcsDataSourceManager.getInstance(vos);
	}

	public static final NcsDataSource lookupDataSource(String dataSourceName)
			throws ACException {
		if (manager == null) {
			throw new ACException("查找数据源前首先必须初始化！");
		}
		return new NcsDataSourceImp(dataSourceName);
	}
}