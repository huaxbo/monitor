package com.hxb.util;

public class StringObjList {
	private MyNode head ;
	private MyNode tail ;
	private int len ;
	
	public static StringObjList instance(){
		return new StringObjList() ;
	}
	private StringObjList(){
		if(head == null){
			head = new MyNode() ;
			tail = head ;
			len = 0 ;
		}
	}
	
	public StringObjList put(String name , String value){
		MyNode node = new MyNode() ;
		node.name = name ;
		node.value = value ;
		tail.next = node ;
		tail = node ;
		len ++ ;
		return this ;
	}
	public StringObjList putChangeURLCode(String name , String value){
		MyNode node = new MyNode() ;
		node.name = name ;
		node.value = ChangeCode.changeURLCode(value) ;
		tail.next = node ;
		tail = node ;
		len ++ ;
		return this ;
	}
	
	public String getName(int n){
		String name = null ;
		if(n < 0 || n >= len){
			return null ;
		}else{
			MyNode temp = head ;
			for(int i = 0 ; i < len ; i++){
				temp = temp.next ;
				if(i == n){
					name = temp.name ;
					break ;
				}
			}
			return name ; 
		}
	}
	public String getValue(int n){
		String value = null ;
		if(n < 0 || n >= len){
			return null ;
		}else{
			MyNode temp = head ;
			for(int i = 0 ; i < len ; i++){
				temp = temp.next ;
				if(i == n){
					value = temp.value ;
					break ;
				}
			}
			return value ; 
		}
	}
	public String getValue(String name){
		String value = null ;
		MyNode temp = head ;
		for(int i = 0 ; i < len ; i++){
			temp = temp.next ;
			if(name.equals(temp.name)){
				value = temp.value ;
				break ;
			}
		}
		return value ; 
	}
	
	public int size(){
		return len ;
	}
	
	
	

	private class MyNode{
		private String name ;
		private String value ;
		private MyNode next ;
	}

}
