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
	public boolean usage() default true;//�������Ƿ�����
	public String commandType();//�������ң��|����|���
	public String commandTip() default "";//����tip��ʾ����
	public String commandName();//��������
	public String doAction();//������õ�action
	public String doMethod();//������õ�medhod
	public boolean winScroll() default true;//�����Ƿ����
	public boolean resizeable() default true;//�����Ƿ����е���
	public int winLeft() default 300;//���ھ���ʾ����ߵľ���
	public int winTop() default 100;//���ھ���ʾ�����˵ľ���
	public int winWidth() default 500;//���ڿ��
	public int winHeight() default 300;//���ڸ߶�
	public String paramType() default "";//��������,���ڽ�����ĳ��������Ƿ����ش�����Ŀ���
	
}
