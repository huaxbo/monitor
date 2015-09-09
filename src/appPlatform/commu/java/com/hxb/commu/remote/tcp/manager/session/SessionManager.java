package com.hxb.commu.remote.tcp.manager.session;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.hxb.commu.remote.protocol.util.UtilProtocol;
import com.hxb.commu.util.CommuConstant;

public class SessionManager {

	private Logger log = Logger.getLogger(SessionManager.class);

	// 设备连接信息集合
	private Hashtable<String, SessionVO> sessions = new Hashtable<String, SessionVO>(
			0);

	public Object synObj;

	private static SessionManager smgr;

	/**
	 * 构造方法
	 */
	private SessionManager() {
	}

	/**
	 * 获取唯一实例
	 * 
	 * @return
	 */
	public static SessionManager getSingleInstance() {
		if (smgr == null) {
			smgr = new SessionManager();
			smgr.synObj = new Object();
		}

		return smgr;
	}

	/**
	 * 增加设备信息
	 * 
	 * @param id
	 * @param svo
	 */
	public void add(String id, SessionVO svo) {
		if (id != null && !id.equals("") && svo != null) {
			sessions.put(id, svo);
		}
	}

	/**
	 * 根据id获取设备信息
	 * 
	 * @param id
	 * @return
	 */
	public SessionVO get(String id) {
		if (id != null && !id.equals("")) {

			return sessions.get(id);
		}

		return null;
	}

	/**
	 * 删除id设备信息
	 * 
	 * @param id
	 */
	public void remove(String id) {
		if (id != null && !id.equals("")) {

			sessions.remove(id);
		}
	}

	/**
	 * 更改设备状态为在线
	 * 
	 * @param id
	 * @param status
	 */
	public void change2Online(String id, String status) {
		SessionVO svo = get(id);
		if (svo != null) {
			svo.setOnline(SessionConstant.METER_ONLINE);
		}
	}

	/**
	 * 更改设备状态为离线
	 * 
	 * @param id
	 * @param status
	 */
	public void change2Offline(String id, String status) {
		SessionVO svo = get(id);
		if (svo != null) {
			svo.setOnline(SessionConstant.METER_OFFLINE);
		}
	}

	/**
	 * 递增连续失败命令次数
	 * 
	 * @param id
	 */
	public void plusCmdFails(String id) {
		SessionVO svo = get(id);
		if (svo != null) {
			int cfs = svo.getCmdFails();
			cfs++;
			if (cfs == CommuConstant.continueFailCmds_times) {
				close(id, svo.getSession());
				log.error("设备[" + id + "]连续失败命令达到"
						+ CommuConstant.continueFailCmds_times
						+ "次，通讯服务主动关闭网络连接！");
			} else {
				svo.setCmdFails(cfs);
			}
		}
	}

	/**
	 * 清空连续失败命令次数
	 * @param id
	 */
	public void cleanCmdFails(String id){
		SessionVO svo = get(id);
		if (svo != null) {
			svo.setCmdFails(0);
		}
	}
	/**
	 * 更新时间戳
	 * 
	 * @param id
	 */
	public void updateTimestamp(String id) {
		SessionVO svo = get(id);
		if (svo != null) {
			svo.setTimestamp(System.currentTimeMillis());
		}
	}

	/**
	 * 删除session设备信息
	 * 
	 * @param session
	 * @return
	 */
	public String removeSession(IoSession session) {
		Enumeration<String> ses = sessions.keys();
		while(ses.hasMoreElements()){
			String meterId = ses.nextElement();
			if(sessions.get(meterId).getSession() == session){
				sessions.remove(meterId);
				
				return meterId;
			}
		}
				
		return null;
	}
	/**
	 * 获取session设备id
	 * @param session
	 * @return
	 */
	public String getMeterId(IoSession session){
		Enumeration<String> ses = sessions.keys();
		while(ses.hasMoreElements()){
			String meterId = ses.nextElement();
			if(sessions.get(meterId).getSession() == session){
				
				return meterId;
			}
		}
		
		return null;
	}
	/**
	 * 获取设备清单
	 */
	public List<SessionVO> getAll() {		
		Iterator<Entry<String, SessionVO>> it = sessions.entrySet().iterator();
		List<SessionVO> lt = new ArrayList<SessionVO>(0);
		while (it.hasNext()) {
			lt.add(it.next().getValue());
		}

		return lt;
	}

	/**
	 * 获取设备清单
	 */
	public String[] getAllId() {
		Iterator<Entry<String, SessionVO>> it = sessions.entrySet().iterator();
		Vector<String> arr = new Vector<String>(0);
		while (it.hasNext()) {
			arr.add(it.next().getKey());
		}
		String[] tmp = new String[arr.size()];
		arr.toArray(tmp);

		return tmp;
	}

	/**
	 * 清理连接超时设备
	 */
	public void cleanTimeout() {
		long curr = System.currentTimeMillis();
		Enumeration<String> ses = sessions.keys();
		while(ses.hasMoreElements()){			
			String meterId = ses.nextElement();
			SessionVO svo = sessions.get(meterId);
			if (CommuConstant.noneReceive_interval > 0) {
				long tt = svo.getTimestamp();
				if ((curr - tt) > CommuConstant.noneReceive_interval * 60 * 1000) {
					close(meterId, svo.getSession());
					log.error("设备[" + meterId + "]连续无接收数据时长>"
							+ CommuConstant.noneReceive_interval + "分钟，"
							+ "通讯服务主动关闭网络连接！");
				}
			}
			
		}
	}
	/**
	 * @param bts
	 */
	public void send(String id,byte[] bts){
		SessionVO svo = get(id);
		if(svo == null){
			log.warn("测控设备[" + id + "]不在线！");
			return;
		}
		IoSession se = svo.getSession();
		if(se == null || !se.isConnected()){
			log.warn("测控设备[" + id + "]不在线！");
			return;
		}
		se.write(bts);
		try {
			log.info("向测控设备[" + id + "]发送数据：" 
					+ UtilProtocol.getSingle().byte2Hex(bts, true).toUpperCase());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	}
	/**
	 * 关闭连接
	 * 
	 * @param id
	 * @param se
	 */
	public void close(String id, IoSession se) {
		if (se != null) {
			se.close(true);
		}
		sessions.remove(id);
	}

	public Hashtable<String, SessionVO> getSessions() {
		return sessions;
	}

	public void setSessions(Hashtable<String, SessionVO> sessions) {
		this.sessions = sessions;
	}

}
