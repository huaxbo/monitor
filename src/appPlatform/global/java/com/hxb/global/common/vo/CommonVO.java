package com.hxb.global.common.vo;

import java.lang.reflect.Field;

public class CommonVO {

	/**
	 * 初始化基本类型
	 * @param param
	 */
	public void build(Object param){
		if(param == null){
			return ;
		}
		Field[] locals = this.getClass().getDeclaredFields();
		if(locals != null){
			for(Field local:locals){
				local.setAccessible(true);
				VoFetchedAnno anno = local.getAnnotation(VoFetchedAnno.class);
				if(anno == null || !anno.value()){
					continue;
				}
				try {
					local.set(this, getField(param,local.getName()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{}
			}
		}
	}
	/**
	 * @param name
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	private Object getField(Object param,String name) throws Exception{
		if(param == null){
			return null;
		}
		Field f = param.getClass().getDeclaredField(name);
		if(f == null){
			return null;
		}
		f.setAccessible(true);
		
		return f.get(param);
	}
}
