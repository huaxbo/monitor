package com.hxb.common.imp.socketPool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.Logger;

class SocketPool {
	private String name;
	private int checkedOut;
	private Vector<PoolSocket> freeSockets = new Vector();
	private int maxNum;
	private int maxCreatingNum;
	private String URL;
	private int PORT;
	public boolean newSocketSucc = true;

	public int creatingSocketNum = 0;

	public int contineCreateNewSocketCount = 0;

	private Logger log = Logger.getLogger(SocketPool.class.getName());

	public SocketPool(String name, String URL, int port, int maxNum,
			int maxCreatingNum) {
		this.name = name;
		this.URL = URL;
		this.PORT = port;
		this.maxNum = maxNum;
		this.maxCreatingNum = maxCreatingNum;
		this.checkedOut = 0;
	}

	public synchronized void freeSocket(PoolSocket soc) {
		if (soc != null) {
			soc.newCreate = false;
			this.freeSockets.addElement(soc);
			this.checkedOut -= 1;
			log("TPC连接池" + this.name + "回收一个TCP连接");
			notifyAll();
		} else {
			log("TPC连接池" + this.name + "未回收一个连接");
		}
	}

	public void destroySocket(PoolSocket soc) {
		closeSocket(soc);
	}

	public synchronized PoolSocket getSocket() {
		PoolSocket soc = null;
		Socket s = null;
		if (this.freeSockets.size() > 0) {
			soc = (PoolSocket) this.freeSockets.firstElement();
			this.freeSockets.removeElementAt(0);
			s = soc.socket;
			if ((!s.isConnected()) || (s.isClosed())) {
				closeSocket(soc);
				log("从TPC连接池" + this.name + "删除一个无效的连接！");

				soc = getSocket();
			}
			this.contineCreateNewSocketCount = 0;
		} else if ((this.maxNum == 0) || (this.checkedOut < this.maxNum)) {
			if (((this.maxNum <= 0) && (this.contineCreateNewSocketCount > 100))
					|| ((this.maxNum > 0) && (this.contineCreateNewSocketCount >= this.maxNum))) {
				return null;
			}
			if (this.newSocketSucc) {
				if (this.creatingSocketNum < this.maxCreatingNum)
					soc = newSocket();
			} else {
				return null;
			}

		}

		if (soc != null) {
			this.checkedOut += 1;
		}
		notifyAll();
		if (soc != null)
			log("从TPC连接池" + this.name + "得到一个有效连接！");
		else {
			log("从TPC连接池" + this.name + "未能得到一个有效连接！");
		}
		return soc;
	}

	public synchronized PoolSocket getSocket(long timeout) {
		long startTime = System.currentTimeMillis();
		PoolSocket con = null;
		while ((con = getSocket()) == null) {
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
		Enumeration allSockets = this.freeSockets.elements();
		PoolSocket soc = null;
		while ((allSockets != null) && (allSockets.hasMoreElements())) {
			Object o = allSockets.nextElement();
			if (o != null) {
				soc = (PoolSocket) o;
				if (soc != null) {
					closeSocket(soc);
				}
			}
		}
		this.freeSockets.removeAllElements();
	}

	public PoolSocket newSocket() {
		PoolSocket ps = null;
		Socket s = null;
		InputStream in = null;
		OutputStream out = null;
		this.creatingSocketNum += 1;
		try {
			s = new Socket(InetAddress.getByName(this.URL), this.PORT);
			in = s.getInputStream();
			out = s.getOutputStream();
			ps = new PoolSocket(s, out, in, true);
		} catch (IOException e) {
			this.newSocketSucc = false;
			this.creatingSocketNum -= 1;
			log(e, "TPC连接池" + this.name + "不能创建到'" + this.URL + ":" + this.PORT
					+ "'的TCP连接！");
			return null;
		}
		this.newSocketSucc = true;
		this.creatingSocketNum -= 1;
		return ps;
	}

	private void closeSocket(PoolSocket soc) {
		try {
			soc.out.close();
			soc.in.close();
			soc.socket.close();
		} catch (Exception localException) {
		}
		log("TPC连接池" + this.name + "关闭了一个连接！");
	}

	private void log(String msg) {
		this.log.info(msg);
	}

	private void log(Throwable e, String msg) {
		this.log.error(msg + "\n" + e);
	}
}