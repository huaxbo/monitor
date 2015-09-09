package com.hxb.common.socketPool;

import com.hxb.ACException;
import com.hxb.common.imp.socketPool.PoolSocket;
import com.hxb.common.imp.socketPool.SocketPoolManager;

import java.io.IOException;

public class SocketPoolFactory {
	private static SocketPoolManager manager;

	public static SocketPoolFactory newInstance() {
		return new SocketPoolFactory();
	}

	public static void init(SocketConfigVO[] vos) throws ACException {
		if (manager != null) {
			throw new ACException("网络连接池工厂不能重复初始化！");
		}
		if (vos == null) {
			throw new ACException("网络连接池始化参数对象为空！");
		}
		manager = SocketPoolManager.getInstance(vos);
	}

	public void printOut(String serverId, String socketPoolName, int timeout,
			byte[] data, SocketJob sjob) {
		PoolSocket soc = null;
		boolean isExe = false;
		boolean flag = false;
		do {
			if ((timeout == -1) || (timeout == 0))
				soc = getSocket(socketPoolName);
			else
				soc = getSocket(socketPoolName, timeout);
			try {
				sjob.out(serverId, data, soc.out);
			} catch (IOException e) {
				isExe = true;
			} finally {
				flag = false;
				if (isExe) {
					isExe = false;
					if (!soc.newCreate) {
						flag = true;
					}
					destroySocket(socketPoolName, soc);
				} else {
					freeSocket(socketPoolName, soc);
				}
			}
		} while (flag);
	}

	private PoolSocket getSocket(String poolName) {
		return SocketPoolManager.getInstance().getSocket(poolName);
	}

	private PoolSocket getSocket(String poolName, int timeout) {
		return SocketPoolManager.getInstance().getSocket(poolName, timeout);
	}

	private void freeSocket(String poolName, PoolSocket soc) {
		SocketPoolManager.getInstance().freeSocket(poolName, soc);
	}

	private void destroySocket(String poolName, PoolSocket soc) {
		SocketPoolManager.getInstance().destroySocket(poolName, soc);
	}
}