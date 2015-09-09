package com.hxb.util;

import java.net.URL;
import java.net.URLConnection;
import org.apache.log4j.Logger;

/**
 * @author Administrator
 *
 */
public class WebMonitor {
	
	private static Logger log = Logger.getLogger(WebMonitor.class);
	
	/**
	 * ���webUrl�Ƿ��������� 
	 * @param webUrl
	 * return��true��������������false��������ʧ��
	 */
	public static boolean checkWebIsAlive(String webUrl){
		String con_rlt = "";
		boolean isAlive = false;
		java.io.BufferedReader in;
		try {
			log.info(">>>>>>���URL��" + webUrl);
			URL url = new URL(webUrl + "?webMonitorRandom=" + Math.random() * 65535);
			URLConnection con = url.openConnection();
			in = new java.io.BufferedReader(new java.io.InputStreamReader(
					con.getInputStream()));
			con.setConnectTimeout(1000);
			con.setReadTimeout(4000);
			while ((con_rlt = in.readLine()) != null) {
				if (con_rlt.length() > 0) {// ����ܹ���ȡ��ҳ����֤�����ã�web��������
					isAlive = true;
					return isAlive;
				}
			}
			in.close();
		} catch (Exception ex) {
			isAlive = false;
			log.error("���URL::" + webUrl + "::����ʧ�ܣ�");
			log.error(ex);
		} finally {
		}
		
		return isAlive;
	}
}
