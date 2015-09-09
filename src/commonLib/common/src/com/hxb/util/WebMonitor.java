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
	 * 监测webUrl是否正常连接 
	 * @param webUrl
	 * return：true：：连接正常、false：：连接失败
	 */
	public static boolean checkWebIsAlive(String webUrl){
		String con_rlt = "";
		boolean isAlive = false;
		java.io.BufferedReader in;
		try {
			log.info(">>>>>>检测URL：" + webUrl);
			URL url = new URL(webUrl + "?webMonitorRandom=" + Math.random() * 65535);
			URLConnection con = url.openConnection();
			in = new java.io.BufferedReader(new java.io.InputStreamReader(
					con.getInputStream()));
			con.setConnectTimeout(1000);
			con.setReadTimeout(4000);
			while ((con_rlt = in.readLine()) != null) {
				if (con_rlt.length() > 0) {// 如果能够读取到页面则证明可用，web访问正常
					isAlive = true;
					return isAlive;
				}
			}
			in.close();
		} catch (Exception ex) {
			isAlive = false;
			log.error("检测URL::" + webUrl + "::连接失败！");
			log.error(ex);
		} finally {
		}
		
		return isAlive;
	}
}
