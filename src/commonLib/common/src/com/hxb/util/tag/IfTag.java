package com.hxb.util.tag;


import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * 功能说明：构成if-elseif-else结构<br>
 * 典型用法：<br>
 * 特殊用法：<br>
 */
public abstract class IfTag extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 102030405060L;
	/**
	 * 功能说明：可能的if-elseif-else结构的容器对象<br>
	 * 取值范围：<br>
	 * 依赖属性：<br>
	 */
	protected ConditionTag container = null;
	/**
	 * 功能说明：根据IfElseContainer的条件和test的值来判断是否需要执行Body。<br>
	 * 输入参数：<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * @return int
	 * @exception javax.servlet.jsp.JspException 异常说明。
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
	 * 功能说明：清除所有的临时变量<br>
	 * 输入参数：<br>
	 * 返回值：<br>
	 * 异常：<br>
	 */
	public void release() {
		container = null;
	}

	/**
	 * 功能说明：测试这个条件是否满足<br>
	 * 输入参数：<br>
	 * 返回值：<br>
	 * 异常：<br>
	 * @return boolean
	 * @exception javax.servlet.jsp.JspException 异常说明。
	 */
	protected abstract boolean check() throws JspException;
}