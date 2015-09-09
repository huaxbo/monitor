package com.hxb.util.tag;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * 功能说明：构成if-elseif-else结构<br>
 * 典型用法：<br>
 * 特殊用法：<br>
 */
public class ConditionTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2569806286862031348L;
	/**
	 * 功能说明：是否已经有一个条件被满足了的标记位<br>
	 * 取值范围：<br>
	 * 依赖属性：<br>
	 */
	private boolean whenFlag;
	/**
	 * IfTag 构造子注解。
	 */
	public ConditionTag() {
		super();
	}

	/**
	 * 功能说明：设置初始状态为没有条件被满足。<br>
	 * 输入参数：<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * @return int
	 * @exception javax.servlet.jsp.JspException 异常说明。
	 */
	public int doStartTag() throws JspException {
		whenFlag = false;
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * 功能说明：<br>
	 * 输入参数：<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * @return boolean
	 */
	public boolean isWhenFlag() {
		return whenFlag;
	} 

	/**
	 * 功能说明：<br>
	 * 输入参数：<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * @param newWhenFlag boolean
	 */
	public void setWhenFlag(boolean newWhenFlag) {
		whenFlag = newWhenFlag;
	}
}