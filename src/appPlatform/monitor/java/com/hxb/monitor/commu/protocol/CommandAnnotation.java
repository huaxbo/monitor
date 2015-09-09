/**
 * 
 */
package com.hxb.monitor.commu.protocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CommandAnnotation {
	public boolean usage() default true;//此命令是否启用
	public String commandType();//命令类别（遥测|设置|命令）
	public String commandTip() default "";//命令tip提示内容
	public String commandName();//命令名称
	public String doAction();//命令调用的action
	public String doMethod();//命令调用的medhod
	public boolean winScroll() default true;//窗口是否滚动
	public boolean resizeable() default true;//窗口是否运行调整
	public int winLeft() default 300;//窗口距显示屏左边的距离
	public int winTop() default 100;//窗口距显示器顶端的距离
	public int winWidth() default 500;//窗口宽度
	public int winHeight() default 300;//窗口高度
	public String paramType() default "";//参数种类,用于进行在某项参数下是否隐藏此命令的控制
	
}
