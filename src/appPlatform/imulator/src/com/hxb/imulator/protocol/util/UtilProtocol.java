package com.hxb.imulator.protocol.util;

public class UtilProtocol {
	
	private static UtilProtocol up;
	
	/**
	 * 
	 */
	private UtilProtocol(){}
	
	/**
	 * @return
	 */
	public static UtilProtocol getSingle(){
		if(up == null){
			up = new UtilProtocol();
		}
		
		return up;
	}
	
	
	/**
	 * �ֽ�ת�������
	 * 
	 * @param b
	 *            byte
	 * @throws Exception
	 * @return String
	 */
	public String byte2Binary(byte b) throws Exception {
		int n = (b + 256) % 256 + 256;
		try {
			return Integer.toBinaryString(n).substring(1);
		} catch (Exception e) {
			throw new Exception("�ֽ�ת���ɶ����Ƶ��ַ�������", null);
		}
	}

	/**
	 * �ֽ�ת��8λ������
	 * 
	 * @param b
	 *            byte
	 * @throws Exception
	 * @return String
	 */
	public String byte2bit8Binary(byte b) throws Exception {
		String s = this.byte2Binary(b);
		int len = s.length();
		for (int i = 0; i < 8 - len; i++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * �ֽ�ת���ַ�
	 * 
	 * @param b
	 * @return
	 */
	public char byte2char(byte b) {
		return (char) byte2PlusInt(b);
	}

	/**
	 * �ֽ�ת���ַ���
	 * 
	 * @param b
	 * @return
	 */
	public String bytes2chars2String(byte[] b, int start, int end) {
		String s = "";
		for (int i = start; i <= end; i++) {
			s += this.byte2char(b[i]);
		}
		return s.trim();
	}

	/**
	 * �ֽ�ת���޷����������� ��������
	 * 
	 * @param b
	 * @return
	 */
	public int byte2PlusInt(byte b) {
		int n = (b + 256) % 256;
		return n;
	}
	
	public int byte2Int(byte b) {
		return b ;
	}

	public int bytes2Int(byte[] src, int start, int length) {
		int value = 0;
		for (int i = start; i < start + length; i++) {
			value = (value << 8) ^ (src[i] & 0xFF);
		}
		return value;
	}

	public long byte2Long(byte b) {
		return bytes2Long(new byte[] { b }, 0, 1);
	}

	public long bytes2Long(byte[] src, int start, int length) {
		long value = 0;
		for (int i = start; i < start + length; i++) {
			value = (value << 8) ^ (src[i] & 0xFF);
		}
		return value;
	}


	/**
	 * �ֽ�����ת����ʮ�����Ƶ��ַ��� 
	 *  converts a byte array to a hex string consisting of
	 * two-digit hex values for each byte in the array
	 * 
	 * @param b byte[]
	 * @param hasBlank 16�����Ƿ��ÿո�ָ�
	 * @return String
	 */
	public String byte2Hex(byte[] b, boolean hasBlank) throws Exception {
		String rString = "";
		String temp = "";
		try {
			for (int i = 0; i < b.length; i++) {
				int c = b[i];
				temp = Integer.toHexString(c & 0XFF);
				if (temp.length() == 1) {
					temp = "0" + temp;
				}
				if(hasBlank){
					if (i == 0) {
						rString += temp;
					} else {
						rString += " " + temp;
					}
				}else{
					rString += temp;
				}
			}
		} catch (Exception e) {
			throw new Exception("�ֽ�����ת����ʮ�����Ƶ��ַ�������", null);
		}
		return rString;
	}

	/**
	 * �ַ�ת���ֽ�
	 * 
	 * @param c
	 * @return
	 */
	public byte char2byte(char c) {
		return (byte) c;
	}
	
	
	public static void main(String[] args){
		UtilProtocol up = new UtilProtocol() ;
		int i = 65536 ;
		byte[] b = up.int2bytes(i) ;
		int im = up.bytes2Int(b, 0, b.length) ;
		System.out.println(im);
	}
	
	/**
	 * ʮ����ת�����ֽ�����
	 * 
	 * @param s  String
	 * @return byte[]
	 */
	public byte[] int2bytes(int value) {
		int len = 4 ;
		byte[] b = new byte[len]; 
		for(int i = b.length-1; i > -1; i--){ 
           b[i] = (byte)(value & 0xff); 
           value = value >> 8; 
   		} 
		
		int count = 0 ;
		for(int i = 0 ; i < b.length ; i++){
			if(b[i] == 0){
				count++ ;
			}else{
				break ;
			}
		}
		if(count == len){
			count = len - 1 ;
		}
		byte[] bb = new byte[len - count] ;
		int index = 0 ;
		for(int i = count; i < len ; i++){
			bb[index++] = b[i] ;
		}
		return  bb; 
	}


	/**
	 * ת���з�����
	 * 
	 * @param l
	 * @return
	 */
	public byte[] long2bytes(long value) {
		int len = 8 ;
		byte[] b = new byte[len]; 
		for(int i = b.length-1; i > -1; i--){ 
           b[i] = (byte)(value & 0xff); 
           value = value >> 8; 
   		} 
		int count = 0 ;
		for(int i = 0 ; i < b.length ; i++){
			if(b[i] == 0){
				count++ ;
			}else{
				break ;
			}
		}
		if(count == len){
			count = len - 1 ;
		}
		byte[] bb = new byte[len - count] ;
		int index = 0 ;
		for(int i = count; i < len ; i++){
			bb[index++] = b[i] ;
		}
		return  bb; 
	}
	
	/**
	 * �ַ���������ת��byte
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public byte string2byte(String s) throws Exception {
		int n = 0;
		try {
			n = Integer.parseInt(s);
		} catch (Exception e) {
			throw new Exception("�ַ����������ֽ�ʱ�������ǺϷ�����:" + s, null);
		}
		return (byte) n;
	}
	/**
	 * �ַ���ת���ַ����ֽ�����
	 * 
	 * @param s
	 * @return
	 */
	public byte[] string2chars2bytes(String s) {
		s = s.trim();
		byte[] b = new byte[s.length()];
		for (int i = 0; i < b.length; i++) {
			b[i] = this.char2byte(s.charAt(i));
		}
		return b;
	}


	/**
	 * 
	 * @param s
	 * @return
	 */
	public int string2Int(String s) throws Exception {
		if (!isPlusIntNumber(s)) {
			throw new Exception("��ʮ�����ַ�����ת��ʮ�������������ǺϷ�����:" + s, null);
		}
		return Integer.parseInt(s);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public double string2Double(String s) {
		double d = Double.parseDouble(s);
		d = Math.round(d * 100) / (double) 100;
		return d;
	}
	/**
	 * ʮ�����ƴ�ת�ֽ�����
	 * @param src
	 * @return
	 */
	public byte[] hex2Bytes(String src) {
		if(src == null || src.length()%2 != 0){
			return null ;
		}
		byte[] ret = new byte[src.length()/2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < ret.length ; ++i) {
			ret[i] = this.uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	private byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 | _b1);
		return ret;
	}
	
	/**
	 * ����ʮ�����Ʒ�����ת��ʮ������
	 * 
	 * @param s String
	 * @return int
	 */
	public int hex2Int(String s) throws Exception {
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			result += Math.pow(16, i)
					* this.hex2Int(s.charAt(s.length() - 1 - i));
		}
		return result;
	}
	/**
	 * ʮ����������ת��ʮ������
	 * 
	 * @param c char
	 * @return int
	 */
	public int hex2Int(char c) throws Exception {
		if (c == '0')
			return 0;
		if (c == '1')
			return 1;
		if (c == '2')
			return 2;
		if (c == '3')
			return 3;
		if (c == '4')
			return 4;
		if (c == '5')
			return 5;
		if (c == '6')
			return 6;
		if (c == '7')
			return 7;
		if (c == '8')
			return 8;
		if (c == '9')
			return 9;
		if (c == 'a' || c == 'A')
			return 10;
		if (c == 'b' || c == 'B')
			return 11;
		if (c == 'c' || c == 'C')
			return 12;
		if (c == 'd' || c == 'D')
			return 13;
		if (c == 'e' || c == 'E')
			return 14;
		if (c == 'f' || c == 'F')
			return 15;
		throw new Exception("���ǺϷ���ʮ����������", null);
	}
	
	

	/**
	 * BCD����ת������
	 * 
	 * @param b
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public int BCD2Int(byte b) throws Exception {
		String str = "";
		str = this.decodeBCD(new byte[]{b}, 0, 1);
		int n = 0;
		try {
			n = Integer.parseInt(str);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), null);
		}
		return n;
	}
	/**
	 * BCD����ת������
	 * 
	 * @param b
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public int BCD2Int(byte[] b, int startIndex, int endIndex) throws Exception {
		String str = "";
		str = this.decodeBCD(b, startIndex, endIndex - startIndex + 1);
		int n = 0;
		try {
			n = Integer.parseInt(str);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), null);
		}
		return n;
	}
	/**
	 * BCD����ת���ַ�����
	 * 
	 * @param b
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public long BCD2Long(byte[] b, int startIndex, int endIndex)
			throws Exception {
		String str = "";
		str = this.decodeBCD(b, startIndex, endIndex - startIndex + 1);
		long n = 0;
		try {
			n = Long.parseLong(str);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), null);
		}
		return n;
	}

	/**
	 * BCD����ת���ַ�����
	 * 
	 * @param b
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws Exception
	 */
	public String BCD2String(byte[] b, int startIndex, int endIndex)
			throws Exception {
		String str = "";
		str = this.decodeBCD(b, startIndex, endIndex - startIndex + 1);
		return str;
	}


	/**
	 * ����ת��BCD����
	 * 
	 * @param l
	 * @return
	 */
	public byte[] int2BCD(int i) {
		String str = "" + i;
		byte[] b = null;
		if (str.length() % 2 == 0) {
			b = new byte[str.length() / 2];
		} else {
			b = new byte[(str.length() / 2) + 1];
		}
		this.encodeBCD(str, b, 0, b.length);

		return b;
	}
	/**
	 * ������ת��BCD����
	 * 
	 * @param l
	 * @return
	 */
	public byte[] long2BCD(long l) {
		String str = "" + l;
		byte[] b = null;
		if (str.length() % 2 == 0) {
			b = new byte[str.length() / 2];
		} else {
			b = new byte[(str.length() / 2) + 1];
		}
		this.encodeBCD(str, b, 0, b.length);

		return b;
	}
	/**
	 * �ַ���������ת��BCD����
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public byte[] string2BCD(String s) throws Exception {
		if (!isPlusIntNumber(s)) {
			throw new Exception("�ַ���������ת��BCD����ʱ�������ǺϷ�����:" + s, null);
		}
		long l = 0l;
		try {
			l = Long.parseLong(s);
		} catch (Exception e) {
			throw new Exception("�ַ���������ת��BCD����ʱ�������ǺϷ�����:" + s, null);
		}
		return this.long2BCD(l);
	}

	/**
	 * 
	 * @param value
	 * @param dest
	 * @param startIndex
	 * @param length
	 */
	private void encodeBCD(String value, byte[] dest, int startIndex, int length) {
		if (value == null || !value.matches("\\d*")) {
			throw new java.lang.IllegalArgumentException();
		}
		int[] tmpInts = new int[2 * length];
		int index = value.length() - 1;
		for (int i = tmpInts.length - 1; i >= 0 && index >= 0; i--, index--) {
			tmpInts[i] = value.charAt(index) - '0';
		}
		for (int i = startIndex, j = 0; i < startIndex + length; i++, j++) {
			dest[i] = (byte) (tmpInts[2 * j] * 16 + tmpInts[2 * j + 1]);
		}
	}

	/**
	 * 
	 * @param src
	 * @param startIndex
	 * @param length
	 * @return
	 */
	private String decodeBCD(byte[] src, int startIndex, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = startIndex; i < startIndex + length; i++) {
			int value = (src[i] + 256) % 256;
			sb.append((char) (value / 16 + '0')).append(
					(char) (value % 16 + '0'));
			value++;
		}
		String result = sb.toString();
		if (!result.matches("\\d*")) {
			throw new java.lang.IllegalArgumentException();
		}
		return result;
	}


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
	 * �ж��Ƿ�������
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

}
