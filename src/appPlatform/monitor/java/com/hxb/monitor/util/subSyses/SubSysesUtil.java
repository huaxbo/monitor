/**
 * 
 */
package com.hxb.monitor.util.subSyses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Administrator
 * 
 */
@SuppressWarnings("unchecked")
public class SubSysesUtil {

	/** 
	* @Fields xstream : TODO(xstream实例) 
	*/ 
	private static XStream xstream;
	/** 
	* @Fields subSyses : TODO(子系统列表) 
	*/ 
	public static ArrayList<SubSys> subSyses;
	
	/** 
	* @Fields registerSys : TODO(注册子系统列表，用以系统注销) 
	*/ 
	public static Vector<String> registerSys = new Vector<String>(0);
	
	static {
		xstream = new XStream(new DomDriver());
		xstream.alias("subSyses", List.class);
		xstream.alias("subSys", SubSys.class);
		try {
			subSyses = (ArrayList<SubSys>) xstream
					.fromXML(new InputStreamReader(new FileInputStream(
							SubSysesUtil.class.getResource(
									"/config/config.subSys.xml").getPath()),
							Charset.forName("UTF-8")));
			//提取注册子系统信息
			for(SubSys sub:subSyses){
				if(!registerSys.contains(sub.getSysContext())){
					registerSys.add(sub.getSysContext());
				}				
			}
						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			subSyses = new ArrayList<SubSys>(0);
		} finally {
		}
	}

}
