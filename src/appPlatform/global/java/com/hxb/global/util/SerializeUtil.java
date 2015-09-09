package com.hxb.global.util;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.util.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SerializeUtil {

	Logger log = Logger.getLogger(SerializeUtil.class);

	private static SerializeUtil su;
	private XStream xstream;

	/**
	 * constructor method
	 */
	private SerializeUtil() {
		this.xstream = new XStream(new DomDriver());
	}

	/**
	 * get instance of the XmlCommonUtil...
	 * 
	 * @return
	 */
	public static SerializeUtil getInstance() {
		if (su == null) {
			su = new SerializeUtil();
		}
		return su;
	}

	/**
	 * @param xml
	 * @param aliasClazzes
	 * @return
	 */
	public Object xml2Obj(String xml, Class[] aliasClazzes) {
		try {
			if (xstream != null) {
				if (xml != null) {
					for (int i = 0; aliasClazzes != null
							&& i < aliasClazzes.length; i++) {
						xstream.alias(aliasClazzes[i].getSimpleName(),
								aliasClazzes[i]);
					}
					return (Object) xstream.fromXML(xml);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			;
		}

		return null;
	}

	/**
	 * object to xml
	 * 
	 * @param obj
	 *            :the object which convert to xml
	 * @param aliasClazzes
	 *            :the class list which need alias
	 * @return
	 */
	public String obj2Xml(Object obj, Class[] aliasClazzes) {
		try {
			if (xstream != null) {
				if (obj != null) {
					xstream.alias(obj.getClass().getSimpleName(),
							obj.getClass());
					for (int i = 0; aliasClazzes != null
							&& i < aliasClazzes.length; i++) {
						xstream.alias(aliasClazzes[i].getSimpleName(),
								aliasClazzes[i]);
					}
					return xstream.toXML(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			;
		}

		return null;
	}

	/**
	 * @param xml
	 * @return
	 */
	public List xml2List(String xml, Class[] aliasClazzes) {
		try {
			if (xml == null) {
				return null;
			}
			for (int i = 0; aliasClazzes != null && i < aliasClazzes.length; i++) {
				xstream.alias(aliasClazzes[i].getSimpleName(), aliasClazzes[i]);
			}
			return (List) xstream.fromXML(xml);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
		}

		return null;
	}

	/**
	 * list to xml
	 * 
	 * @param lt
	 *            :the list which convert to xml
	 * @param aliasClazzes
	 *            :the class list which need alias
	 * @return
	 */
	public String list2Xml(Object[] list, Class[] aliasClazzes) {
		if (xstream != null) {
			if (list != null) {
				for (int i = 0; aliasClazzes != null && i < aliasClazzes.length; i++) {
					xstream.alias(aliasClazzes[i].getSimpleName(),
							aliasClazzes[i]);
				}
				return xstream.toXML(list);
			}
		}
		return null;
	}

	/**
	 * @param list
	 * @param aliasClazzes
	 * @return
	 */
	public String lllist2Xml(ArrayList<ArrayList<ArrayList<String[]>>> list,
			Class[] aliasClazzes) {
		if (xstream != null) {
			if (list != null) {
				for (int i = 0; aliasClazzes != null && i < aliasClazzes.length; i++) {
					xstream.alias(aliasClazzes[i].getSimpleName(),
							aliasClazzes[i]);
				}
				return xstream.toXML(list);
			}
		}
		return null;
	}

}
