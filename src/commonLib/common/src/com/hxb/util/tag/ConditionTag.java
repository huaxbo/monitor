package com.hxb.util.tag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * ����˵��������if-elseif-else�ṹ<br>
 * �����÷���<br>
 * �����÷���<br>
 */
public class ConditionTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2569806286862031348L;
	/**
	 * ����˵�����Ƿ��Ѿ���һ�������������˵ı��λ<br>
	 * ȡֵ��Χ��<br>
	 * �������ԣ�<br>
	 */
	private boolean whenFlag;
	/**
	 * IfTag ������ע�⡣
	 */
	public ConditionTag() {
		super();
	}

	/**
	 * ����˵�������ó�ʼ״̬Ϊû�����������㡣<br>
	 * ���������<br>
	 * ����ֵ��<br>
	 * �쳣��<br>
	 * @return int
	 * @exception javax.servlet.jsp.JspException �쳣˵����
	 */
	public int doStartTag() throws JspException {
		whenFlag = false;
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * ����˵����<br>
	 * ���������<br>
	 * ����ֵ��<br>
	 * �쳣��<br>
	 * @return boolean
	 */
	public boolean isWhenFlag() {
		return whenFlag;
	} 

	/**
	 * ����˵����<br>
	 * ���������<br>
	 * ����ֵ��<br>
	 * �쳣��<br>
	 * @param newWhenFlag boolean
	 */
	public void setWhenFlag(boolean newWhenFlag) {
		whenFlag = newWhenFlag;
	}
}