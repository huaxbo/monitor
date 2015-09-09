package com.hxb.util;


public class NumberUtil {
	/**
	 * �ж��Ƿ�������
	 * 
	 * @param str String
	 * @return boolean
	 */
	public static boolean isIntNumber(String str) {
		// �ж��Ƿ�������
		if(str == null || str.trim().equals(""))
			return false ;
		
		if(str.startsWith("-")){
			str = str.substring(1 , str.length()) ;
		}
		for (int i = 0; i < str.length(); i++) {
			if (new String("9876543210").indexOf(str.substring(i, i + 1)) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �ж��Ƿ���������
	 * @param str String
	 * @return boolean
	 */
	public static boolean isPlusIntNumber(String str) {
		// �ж��Ƿ�������
		if(str == null || str.trim().equals("")){
			return false ;
		}
		int n = 0 ;
		for (int i = n; i < str.length(); i++) {
			if (new String("9876543210").indexOf(str.substring(i, i + 1)) == -1) {
				return false;
			}
		}
		return true;
	}
	/**
	 * �ж��Ƿ�������
	 * @param str String
	 * @return boolean
	 */
	public static boolean isDoubleNumber(String str) {
		// �ж��Ƿ�������
		if(str == null || str.trim().equals("")){
			return false ;
		}
		for (int i = 0; i < str.length(); i++) {
			if (new String("9876543210.-").indexOf(str.substring(i, i + 1)) == -1) {
				return false;
			} 
		} 
		if(str.startsWith(".") || str.endsWith(".")){
			return false ;
		}else{
			if(str.indexOf('.') != str.lastIndexOf('.')){
				return false ;
			}
		}
		if(str.startsWith("-")){
			if(str.indexOf('-') != str.lastIndexOf('-')){
				return false ;
			}
		}
		return true;
	}
	
	/**
	 * ����������������
	 * @param d
	 * @param scale
	 * @return
	 */
	public static Double roundDouble(Double d , int scale){
		if(d == null){
			return null ;
		}
		String temp = "##." ;
		for(int i = 0 ; i < scale ; i++){
			temp += "0" ;
		}
		return Double.valueOf(new java.text.DecimalFormat(temp).format(d)) ;
	}
	
	/**
	 * ����������������
	 * @param d
	 * @param scale
	 * @return
	 */
	public static String roundDoubleStr(Double d , int scale){
		if(d == null){
			return null ;
		}
		String temp = "#0." ;
		for(int i = 0 ; i < scale ; i++){
			temp += "0" ;
		}
		return String.valueOf(new java.text.DecimalFormat(temp).format(d)) ;
	}
	/**
	 * ð������
	 * @param values Ҫ���������
	 * @param up true ��С����  false �Ӵ�С
	 * @return
	 */
	public static int[] sort(int[]  values , boolean up){
		boolean changed = false ;
		int temp;
		int end = 0 ;
		for (int i = 0; i < values.length; ++i) {
			changed = false ;
			end++ ;
			for (int j = 0; j < values.length - end ; ++j) {
				if(up){
					if (values[j] > values[j + 1]) {
						temp = values[j];
						values[j] = values[j + 1];
						values[j + 1] = temp;
						changed = true ;
					}
				}else{
					if (values[j] < values[j + 1]) {
						temp = values[j];
						values[j] = values[j + 1];
						values[j + 1] = temp;
						changed = true ;
					}
				}
			}
			if(!changed){
				break ;
			}
		}
		return values ;
	}
	
	public static void main(String[] args){
		Double d = 11.05567 ;
		Double dd = NumberUtil.roundDouble(d, 1) ;
		System.out.println(dd) ;
		String ss = NumberUtil.roundDoubleStr(d, 1) ;
		System.out.println(ss) ;
		
//		int[] ints = new int[]{2,4,6,1,67,5,6,8,12,34,56} ;
//		ints = NumberUtil.sort(ints , false) ;
//		for(int i = 0 ; i < ints.length ; i++){
//			System.out.println(ints[i]) ;
//		}
	}

}
