package com.hxb.common.dataSource;

import com.hxb.ACException;
import com.hxb.common.imp.dataSource.NcsDataSourceImp;
import com.hxb.common.imp.dataSource.pool.NcsDataSourceManager;

public class NcsDataSourceFactory {
	private static NcsDataSourceManager manager;

	public static void init(DbConfigVO[] vos) throws ACException {
		if (manager != null) {
			throw new ACException("����Դ���������ظ���ʼ����");
		}
		if (vos == null) {
			throw new ACException("����Դ��ʼ����������Ϊ�գ�");
		}
		manager = NcsDataSourceManager.getInstance(vos);
	}

	public static final NcsDataSource lookupDataSource(String dataSourceName)
			throws ACException {
		if (manager == null) {
			throw new ACException("��������Դǰ���ȱ����ʼ����");
		}
		return new NcsDataSourceImp(dataSourceName);
	}
}