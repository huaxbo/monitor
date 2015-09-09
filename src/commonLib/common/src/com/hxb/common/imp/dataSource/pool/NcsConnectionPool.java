package com.hxb.common.imp.dataSource.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.Logger;

class NcsConnectionPool {
	private int checkedOut;
	private Vector<MyConnection> freeConnections = new Vector();
	private int maxConn;
	private String name;
	private String password;
	private String URL;
	private String user;
	private boolean autoCommit;
	private boolean needTestConnection;
	private int timeConWaitNeedTest;
	public boolean newConnectionSucc = true;

	public int serialCreateNewConnectionCount = 0;

	private Logger log = Logger.getLogger(NcsConnectionPool.class.getName());

	public NcsConnectionPool(String name, String URL, String user,
			String password, int maxConn, boolean autoCommit,
			boolean needTestConnection, int timeConWaitNeedTest) {
		this.name = name;
		this.URL = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
		this.autoCommit = autoCommit;
		this.needTestConnection = needTestConnection;
		this.timeConWaitNeedTest = timeConWaitNeedTest;
	}

	public synchronized void freeConnection(Connection con) {
		if (con != null) {
			MyConnection myCon = new MyConnection(con, this.needTestConnection,
					this.timeConWaitNeedTest);
			this.freeConnections.addElement(myCon);
			this.checkedOut -= 1;
			log("连接池" + this.name + "回收一个连接");
			notifyAll();
		} else {
			log("连接池" + this.name + "未回收一个连接");
		}
	}

	public synchronized Connection getConnection() {
		MyConnection myCon = null;
		Connection con = null;
		if (this.freeConnections.size() > 0) {
			myCon = (MyConnection) this.freeConnections.firstElement();
			this.freeConnections.removeElementAt(0);
			con = myCon.getConnection();
			try {
				if (con.isClosed()) {
					log("从连接池'" + this.name + "'删除一个无效的连接！");

					con = getConnection();
				} else if ((myCon.needTest())
						&& (!myCon.testConnectionIsValid())) {
					log("从连接池'" + this.name + "'删除一个无效的连接！");

					con = getConnection();
				}
			} catch (SQLException e) {
				log("从连接池'" + this.name + "'删除一个无效的连接！");

				con = getConnection();
			}
			this.serialCreateNewConnectionCount = 0;
		} else if ((this.maxConn == 0) || (this.checkedOut < this.maxConn)) {
			this.serialCreateNewConnectionCount += 1;
			if (((this.maxConn <= 0) && (this.serialCreateNewConnectionCount > 100))
					|| ((this.maxConn > 0) && (this.serialCreateNewConnectionCount >= this.maxConn))) {
				return null;
			}
			if (this.newConnectionSucc)
				con = newConnection();
			else {
				return null;
			}

		}

		if (con != null) {
			this.checkedOut += 1;
		}
		notifyAll();
		if (con != null)
			log("从连接池'" + this.name + "'得到一个有效连接！");
		else {
			log("从连接池'" + this.name + "'未能得到一个有效连接！");
		}
		return con;
	}

	public synchronized Connection getConnection(long timeout) {
		long startTime = System.currentTimeMillis();
		Connection con = null;
		while ((con = getConnection()) == null) {
			try {
				wait(timeout);

				if (System.currentTimeMillis() - startTime >= timeout) {
					notifyAll();
					return con;
				}
			} catch (InterruptedException localInterruptedException) {
			}
			if (System.currentTimeMillis() - startTime < timeout)
				continue;
			notifyAll();
			return con;
		}

		notifyAll();
		return con;
	}

	public synchronized void release() {
		Enumeration allConnections = this.freeConnections.elements();
		MyConnection myCon = null;
		while ((allConnections != null) && (allConnections.hasMoreElements())) {
			Object o = allConnections.nextElement();
			if (o != null) {
				myCon = (MyConnection) o;
				Connection con = myCon.getConnection();
				if (con == null)
					continue;
				try {
					con.close();
					log("连接池'" + this.name + "'关闭了一个连接！");
				} catch (SQLException e) {
					log(e, "连接池'" + this.name + "'不能关闭一个连接！");
					return;
				}
			}
		}

		this.freeConnections.removeAllElements();
	}

	public Connection newConnection() {
		Connection con = null;
		try {
			if (this.user == null)
				con = DriverManager.getConnection(this.URL);
			else {
				con = DriverManager.getConnection(this.URL, this.user,
						this.password);
			}
			con.setAutoCommit(this.autoCommit);
			log("连接池‘" + this.name + "'创建了一个新的连接！");
		} catch (SQLException e) {
			this.newConnectionSucc = false;
			log(e, "不能创建到'" + this.URL + "'的数据库连接！");
			return null;
		}
		this.newConnectionSucc = true;
		return con;
	}

	private void log(String msg) {
		this.log.info(msg);
	}

	private void log(Throwable e, String msg) {
		this.log.error(msg + "\n" + e);
	}

	class MyConnection {
		private Connection con;
		private long time;
		private boolean needTest;
		private int waitTime;

		public MyConnection(Connection con, boolean needTest, int waitTime) {
			this.con = con;
			this.needTest = needTest;
			if (this.needTest) {
				this.time = System.currentTimeMillis();
			}
			this.waitTime = waitTime;
		}

		public boolean needTest() {
			if (this.needTest) {
				int wait = (int) (System.currentTimeMillis() - this.time);

				return wait > this.waitTime;
			}

			return false;
		}

		public boolean testConnectionIsValid() {
			Statement st = null;
			try {
				st = this.con.createStatement();
			} catch (Exception e) {
				return false;
			}
			try {
				st.close();
			} catch (Exception localException1) {
			}
			return true;
		}

		public Connection getConnection() {
			return this.con;
		}
	}
}