package com.hxb.util.tag;

/**
*
* ����˵����<br>
* �����÷���<br>
* �����÷���<br>
*/
public class ElseTag extends IfTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5482562044258751809L;

	/**
	 * @see org.apache.taglibs.foundation.logic.IfTag
	 */
	protected boolean check() throws javax.servlet.jsp.JspException {
		return true;
	}
}