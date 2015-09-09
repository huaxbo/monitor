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

	// �豸������Ϣ����
	private Hashtable<String, SessionVO> sessions = new Hashtable<String, SessionVO>(
			0);

	public Object synObj;

	private static SessionManager smgr;

	/**
	 * ���췽��
	 */
	private SessionManager() {
	}

	/**
	 * ��ȡΨһʵ��
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
	 * �����豸��Ϣ
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
	 * ����id��ȡ�豸��Ϣ
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
	 * ɾ��id�豸��Ϣ
	 * 
	 * @param id
	 */
	public void remove(String id) {
		if (id != null && !id.equals("")) {

			sessions.remove(id);
		}
	}

	/**
	 * �����豸״̬Ϊ����
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
	 * �����豸״̬Ϊ����
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
	 * ��������ʧ���������
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
				log.error("�豸[" + id + "]����ʧ������ﵽ"
						+ CommuConstant.continueFailCmds_times
						+ "�Σ�ͨѶ���������ر��������ӣ�");
			} else {
				svo.setCmdFails(cfs);
			}
		}
	}

	/**
	 * �������ʧ���������
	 * @param id
	 */
	public void cleanCmdFails(String id){
		SessionVO svo = get(id);
		if (svo != null) {
			svo.setCmdFails(0);
		}
	}
	/**
	 * ����ʱ���
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
	 * ɾ��session�豸��Ϣ
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
	 * ��ȡsession�豸id
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
	 * ��ȡ�豸�嵥
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
	 * ��ȡ�豸�嵥
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
	 * �������ӳ�ʱ�豸
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
					log.error("�豸[" + meterId + "]�����޽�������ʱ��>"
							+ CommuConstant.noneReceive_interval + "���ӣ�"
							+ "ͨѶ���������ر��������ӣ�");
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
			log.warn("����豸[" + id + "]�����ߣ�");
			return;
		}
		IoSession se = svo.getSession();
		if(se == null || !se.isConnected()){
			log.warn("����豸[" + id + "]�����ߣ�");
			return;
		}
		se.write(bts);
		try {
			log.info("�����豸[" + id + "]�������ݣ�" 
					+ UtilProtocol.getSingle().byte2Hex(bts, true).toUpperCase());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{}
	}
	/**
	 * �ر�����
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
