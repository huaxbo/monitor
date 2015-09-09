package com.hxb.util.tag;


import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * ����˵��������if-elseif-else�ṹ<br>
 * �����÷���<br>
 * �����÷���<br>
 */
public abstract class IfTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 102030405060L;
	/**
	 * ����˵�������ܵ�if-elseif-else�ṹ����������<br>
	 * ȡֵ��Χ��<br>
	 * �������ԣ�<br>
	 */
	protected ConditionTag container = null;
	/**
	 * ����˵��������IfElseContainer��������test��ֵ���ж��Ƿ���Ҫִ��Body��<br>
	 * ���������<br>
	 * ����ֵ��<br>
	 * �쳣��<br>
	 * @return int
	 * @exception javax.servlet.jsp.JspException �쳣˵����
	 */
	public int doStartTag() throws JspException {
		Tag parent = getParent();
		if (parent != null) {
			if (parent.getClass().equals(ConditionTag.class)) {
				container = (ConditionTag) parent;
				if (container.isWhenFlag()) {
					return SKIP_BODY;
				}
			}
		}
		if (check()) {
			if (container != null) {
				container.setWhenFlag(true);
			}
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * ����˵����������е���ʱ����<br>
	 * ���������<br>
	 * ����ֵ��<br>
	 * �쳣��<br>
	 */
	public void release() {
		container = null;
	}

	/**
	 * ����˵����������������Ƿ�����<br>
	 * ���������<br>
	 * ����ֵ��<br>
	 * �쳣��<br>
	 * @return boolean
	 * @exception javax.servlet.jsp.JspException �쳣˵����
	 */
	protected abstract boolean check() throws JspException;
}