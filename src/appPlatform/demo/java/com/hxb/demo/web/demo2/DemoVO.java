package com.hxb.demo.web.demo2;


import com.hxb.global.common.vo.CommonVO;
import com.hxb.global.common.vo.VoFetchedAnno;

public class DemoVO extends CommonVO{
	
	@VoFetchedAnno(true)
	public String id;
	@VoFetchedAnno(true)
	public String cd;
	@VoFetchedAnno(true)
	private String name;
	@VoFetchedAnno(true)
	private Integer age;
		
	/**
	 * 
	 */
	public DemoVO(){}
		
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}		
	
}
