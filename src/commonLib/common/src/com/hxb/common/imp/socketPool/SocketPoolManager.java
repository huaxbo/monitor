package com.hxb.common.imp.socketPool;

import com.hxb.common.socketPool.SocketConfigVO;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.log4j.Logger;

public class SocketPoolManager {
	private static Logger log = Logger.getLogger(SocketPoolManager.class
			.getName());
	private static SocketPoolManager instance;
	private static int clients;
	private static Hashtable<String, SocketPool> pools = new Hashtable();

	private static Hashtable<String, Boolean> poolSucc = new Hashtable();

	private static Hashtable<String, ReCreatePoolThread> reCreatePoolsThreads = new Hashtable();

	private static Hashtable<String, Object> poolsCreateThreadSynObj = new Hashtable();

	public static synchronized SocketPoolManager getInstance(
			SocketConfigVO[] vos) {
		if (instance == null) {
			instance = new SocketPoolManager();

			if (!instance.init(vos)) {
				instance = null;
			}
		}
		return instance;
	}

	public static synchronized SocketPoolManager getInstance() {
		return instance;
	}

	public PoolSocket getSocket(String poolName) {
		if (((Boolean) poolSucc.get(poolName)).booleanValue()) {
			SocketPool pool = (SocketPool) pools.get(poolName);
			if (pool != null) {
				PoolSocket soc = pool.getSocket();
				if (soc == null) {
					if (!pool.newSocketSucc) {
						poolSucc.put(poolName, new Boolean(false));

						Object o = poolsCreateThreadSynObj.get(poolName);
						synchronized (o) {
							o.notifyAll();
						}
					}

					return null;
				}
				clients += 1;
				return soc;
			}
		}

		return null;
	}

	public PoolSocket getSocket(String poolName, long time) {
		if (((Boolean) poolSucc.get(poolName)).booleanValue()) {
			SocketPool pool = (SocketPool) pools.get(poolName);
			if (pool != null) {
				PoolSocket soc = pool.getSocket(time);
				if (soc == null) {
					if (!pool.newSocketSucc) {
						log.error("从TCP" + poolName + "中产生新连接时失败!");
						poolSucc.put(poolName, new Boolean(false));
						Object o = poolsCreateThreadSynObj.get(poolName);
						synchronized (o) {
							o.notifyAll();
						}
					}

					return null;
				}
				clients += 1;
				return soc;
			}
		}

		return null;
	}

	public void freeSocket(String poolName, PoolSocket soc) {
		SocketPool pool = (SocketPool) pools.get(poolName);
		if (pool != null) {
			if (soc != null) {
				clients -= 1;
			}
			pool.freeSocket(soc);
		}
	}

	public void destroySocket(String poolName, PoolSocket soc) {
		SocketPool pool = (SocketPool) pools.get(poolName);
		if (pool != null) {
			if (soc != null) {
				clients -= 1;
			}
			pool.destroySocket(soc);
		}
	}

	public synchronized void release() {
		int count = 0;
		while (clients > 0) {
			count++;
			try {
				Thread.sleep(1000L);
			} catch (Exception localException) {
			}
			if (count == 9) {
				break;
			}
		}
		Enumeration allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			SocketPool pool = (SocketPool) allPools.nextElement();
			pool.release();
		}
	}

	private boolean init(SocketConfigVO[] vo) {
		if ((vo == null) || (vo.length == 0)) {
			return false;
		}
		for (int i = 0; i < vo.length; i++) {
			createPools(vo[i]);
		}
		return true;
	}

	private void createPools(SocketConfigVO vo) {
		String poolName = vo.poolName;
		String url = vo.url;
		int port = vo.port;
		int maxCount = vo.maxCount;
		int minCount = vo.minCount;
		int maxCreatingNum = vo.maxCreatingNum;

		SocketPool pool = new SocketPool(poolName, url, port, maxCount,
				maxCreatingNum);

		pools.put(poolName, pool);
		poolsCreateThreadSynObj.put(poolName, new Object());
		ReCreatePoolThread th = new ReCreatePoolThread(poolName);
		th.start();
		reCreatePoolsThreads.put(poolName, th);

		PoolSocket[] cs = new PoolSocket[minCount];
		for (int i = 0; i < minCount; i++) {
			cs[i] = pool.getSocket();
		}
		for (int i = 0; i < minCount; i++) {
			if (cs[i] != null) {
				pool.freeSocket(cs[i]);
			}
		}
		if (!pool.newSocketSucc) {
			System.out.println("出错，不能产生到" + vo.url + ":" + vo.port
					+ "的TCP的连接，请检查TCP配置、网络以及TCP服务器！");
			poolSucc.put(poolName, new Boolean(false));

			Object o = poolsCreateThreadSynObj.get(poolName);
			synchronized (o) {
				o.notifyAll();
			}
		} else {
			poolSucc.put(poolName, new Boolean(true));
			log.info("成功创建到" + vo.url + ":" + vo.port + "的TCP的连接! " + poolName);
		}
	}

	private class ReCreatePoolThread extends Thread {
		private String poolName;
		private int count = 0;

		public ReCreatePoolThread(String poolName) {
			this.poolName = poolName;
		}

		public void run() {
			while (!((Boolean) SocketPoolManager.poolSucc.get(this.poolName))
					.booleanValue()) {
				Object o = SocketPoolManager.poolsCreateThreadSynObj
						.get(this.poolName);
				synchronized (o) {
					try {
						o.wait();
					} catch (Exception e) {
						try {
							sleep(60000L);
						} catch (Exception localException1) {
						}
					}
				}
				this.count += 1;
				SocketPool pool = (SocketPool) SocketPoolManager.pools
						.get(this.poolName);
				PoolSocket cs = pool.newSocket();
				if (cs != null) {
					pool.freeSocket(cs);
				}
				if (!pool.newSocketSucc) {
					SocketPoolManager.poolSucc.put(this.poolName, new Boolean(
							false));
					try {
						if (this.count < 10) {
							sleep(1000L);
							continue;
						}
						sleep(60000L);
						this.count = 0;
					} catch (Exception e) {
						try {
							if (this.count < 100) {
								sleep(1000L);
								continue;
							}
							sleep(60000L);
							this.count = 0;
						} catch (Exception localException2) {
						}
					}
				} else {
					SocketPoolManager.poolSucc.put(this.poolName, new Boolean(
							true));
					synchronized (o) {
						try {
							o.wait();
						} catch (Exception e) {
							try {
								sleep(60000L);
							} catch (Exception localException3) {
							}
						}
					}
				}
			}
		}
	}
}