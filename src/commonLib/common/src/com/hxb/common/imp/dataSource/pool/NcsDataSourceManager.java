package com.hxb.common.imp.dataSource.pool;

import com.hxb.common.dataSource.DbConfigVO;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.Logger;

public class NcsDataSourceManager {
	private static Logger log = Logger.getLogger(NcsDataSourceManager.class
			.getName());
	private static NcsDataSourceManager instance;
	private static int clients;
	private static Vector<Driver> drivers = new Vector();

	private static Hashtable<String, NcsConnectionPool> pools = new Hashtable();

	private static Hashtable<String, Boolean> poolsConnectSucc = new Hashtable();

	private static Hashtable<String, ReCreatePoolThread> reCreatePoolsThreads = new Hashtable();

	private static Hashtable<String, Object> poolsCreateThreadSynObj = new Hashtable();

	public static synchronized NcsDataSourceManager getInstance(DbConfigVO[] vos) {
		if (instance == null) {
			instance = new NcsDataSourceManager();

			if (!instance.init(vos)) {
				instance = null;
			}
		}
		return instance;
	}

	public static synchronized NcsDataSourceManager getInstance() {
		return instance;
	}

	public Connection getConnection(String poolName) {
		if (((Boolean) poolsConnectSucc.get(poolName)).booleanValue()) {
			NcsConnectionPool pool = (NcsConnectionPool) pools.get(poolName);
			if (pool != null) {
				Connection con = pool.getConnection();
				if (con == null) {
					if (!pool.newConnectionSucc) {
						poolsConnectSucc.put(poolName, new Boolean(false));

						Object o = poolsCreateThreadSynObj.get(poolName);
						synchronized (o) {
							o.notifyAll();
						}
					}

					return null;
				}
				clients += 1;
				return con;
			}
		}

		return null;
	}

	public Connection getConnection(String poolName, long time) {
		if (((Boolean) poolsConnectSucc.get(poolName)).booleanValue()) {
			NcsConnectionPool pool = (NcsConnectionPool) pools.get(poolName);
			if (pool != null) {
				Connection con = pool.getConnection(time);
				if (con == null) {
					if (!pool.newConnectionSucc) {
						log.error("从数据源" + poolName + "中产生新连接时失败!");
						poolsConnectSucc.put(poolName, new Boolean(false));
						Object o = poolsCreateThreadSynObj.get(poolName);
						synchronized (o) {
							o.notifyAll();
						}
					}

					return null;
				}
				clients += 1;
				return con;
			}
		}

		return null;
	}

	public void freeConnection(String poolName, Connection con) {
		NcsConnectionPool pool = (NcsConnectionPool) pools.get(poolName);
		if (pool != null) {
			if (con != null) {
				clients -= 1;
			}
			pool.freeConnection(con);
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
			NcsConnectionPool pool = (NcsConnectionPool) allPools.nextElement();
			pool.release();
		}
		Enumeration allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
				log.info("撤销驱动程序" + driver.getClass().getName() + " ");
			} catch (SQLException e) {
				log.error("不能撤销驱动程序" + driver.getClass().getName(), e);
			}
		}
		drivers.removeAllElements();
	}

	private boolean init(DbConfigVO[] vo) {
		if ((vo == null) || (vo.length == 0)) {
			return false;
		}
		boolean flag = true;
		for (int i = 0; i < vo.length; i++) {
			flag = loadDrivers(vo[i]);
			if (!flag) {
				return false;
			}
		}
		for (int i = 0; i < vo.length; i++) {
			createPools(vo[i]);
		}
		return true;
	}

	private boolean loadDrivers(DbConfigVO vo) {
		String driverClassName = vo.driverClass.trim();
		try {
			Driver driver = (Driver) Class.forName(driverClassName)
					.newInstance();
			DriverManager.registerDriver(driver);
			drivers.addElement(driver);
			log.info("成功注册了JDBC驱动" + driverClassName);
			return true;
		} catch (Exception e) {
			log.error("错误，不能注册JDBC驱动" + driverClassName + " ! ", e);
			System.out.println("错误，不能注册JDBC驱动" + driverClassName + " ! ");
		}
		return false;
	}

	private void createPools(DbConfigVO vo) {
		String dataSourceName = vo.dataSourceName;
		String url = vo.url;
		String user = vo.user;
		String password = vo.password;
		int maxCount = vo.maxCount;
		int minCount = vo.minCount;
		boolean autoCommit = vo.autoCommit;
		boolean needTestConnection = vo.needTestConnection;
		int timeConNeedTest = vo.timeConNeedTest;

		NcsConnectionPool pool = new NcsConnectionPool(dataSourceName, url,
				user, password, maxCount, autoCommit, needTestConnection,
				timeConNeedTest);

		pools.put(dataSourceName, pool);
		poolsCreateThreadSynObj.put(dataSourceName, new Object());
		ReCreatePoolThread th = new ReCreatePoolThread(dataSourceName);
		th.start();
		reCreatePoolsThreads.put(dataSourceName, th);

		Connection[] cs = new Connection[minCount];
		for (int i = 0; i < minCount; i++) {
			cs[i] = pool.getConnection();
		}
		for (int i = 0; i < minCount; i++) {
			if (cs[i] != null) {
				pool.freeConnection(cs[i]);
			}
		}
		if (!pool.newConnectionSucc) {
			System.out.println("出错，不能产生到数据库的连接，请检查数据源配置、网络以及数据库服务器！");
			poolsConnectSucc.put(dataSourceName, new Boolean(false));

			Object o = poolsCreateThreadSynObj.get(dataSourceName);
			synchronized (o) {
				o.notifyAll();
			}
		} else {
			poolsConnectSucc.put(dataSourceName, new Boolean(true));
			log.info("成功创建数据库数据源 " + dataSourceName);
		}
	}

	private class ReCreatePoolThread extends Thread {
		private String poolName;
		private int count = 0;

		public ReCreatePoolThread(String poolName) {
			this.poolName = poolName;
		}

		public void run() {
			while (!((Boolean) NcsDataSourceManager.poolsConnectSucc
					.get(this.poolName)).booleanValue()) {
				Object o = NcsDataSourceManager.poolsCreateThreadSynObj
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
				NcsConnectionPool pool = (NcsConnectionPool) NcsDataSourceManager.pools
						.get(this.poolName);
				Connection cs = pool.newConnection();
				if (cs != null) {
					pool.freeConnection(cs);
				}
				if (!pool.newConnectionSucc) {
					NcsDataSourceManager.poolsConnectSucc.put(this.poolName,
							new Boolean(false));
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
					NcsDataSourceManager.poolsConnectSucc.put(this.poolName,
							new Boolean(true));
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