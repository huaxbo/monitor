package com.hxb.common.secure.sso.help;

import java.security.*;
import javax.crypto.*;

public class Crypt {
	private static String Algorithm_DES = "DES"; //定义 加密算法,可用 DES,DESede,Blowfish
	private static String Algorithm_MD5 = "MD5"; //or "SHA-1"

	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

	//生成密钥, 注意此步骤时间比较长
	public byte[] getKey() throws Exception {
		KeyGenerator keygen = KeyGenerator.getInstance(Algorithm_DES);
		SecretKey deskey = keygen.generateKey();
		return deskey.getEncoded();
	}
	/**
	 * 生成简易key
	 * @return
	 */
	public byte[] getEasyKey(){
		return "autoBJ09".getBytes() ; 
	}

	//加密
	public byte[] encode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm_DES);
		Cipher cip = Cipher.getInstance(Algorithm_DES);
		cip.init(Cipher.ENCRYPT_MODE, deskey);
		return cip.doFinal(input); 
	}

	//解密
	public byte[] decode(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm_DES);
		Cipher cip = Cipher.getInstance(Algorithm_DES);
		cip.init(Cipher.DECRYPT_MODE, deskey);
		return cip.doFinal(input);
	}

	//md5 信息摘要, 不可逆
	public byte[] md5(byte[] input) throws Exception {
		java.security.MessageDigest alg = java.security.MessageDigest.getInstance(Algorithm_MD5); 
		alg.update(input);
		return alg.digest();
	}

	//字节码转换成16进制字符串
	public String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
//			if (n < b.length - 1)
//				hs = hs + ":";
		}
		return hs.toUpperCase();
	}
	
	public  String hexString2binaryString(String hexString) {
	    if (hexString == null || hexString.length() % 2 != 0)
	      return null;
	    String bString = "", tmp;
	    for (int i = 0; i < hexString.length(); i++) {
	      tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
	      bString += tmp.substring(tmp.length() - 4);
	    }
	    return bString;
	  }

	
	
	  /**
	   * 十六进制的字符串转换成字节数组
	   * @param s String
	   * @return byte[]
	   */
	  public byte[] hex2Byte(String s) {
	      int len = s.length()/2 ;
	      int b = 0 ;
	      int e = 2 ;
	      String[] st = new String[len] ;
	      for(int i = 0 ; i < len ; i ++){
	        st[i] = s.substring(b , e) ;
	        b += 2  ;
	        e += 2  ;
	      }
	      byte[] bb = new byte[len];
	      int i = 0;
	      for(int j = 0 ; j < len ; j++) {
	          String token = st[j];
	          int tokenValue = hex2Int(token);
	          byte byteValue = (byte) (tokenValue);
	          bb[i] = byteValue;
	          i++;
	      }
	      return bb;
	  }
	  //converts a two digit hex string to a byte
	  private static int hex2Int(String s) {
	      int result = 0;
	      for (int i = 0; i < s.length(); i++) {
	          result += Math.pow(16, i) * hex2Int(s.charAt(s.length() - 1 - i));
	      }
	      return result;
	  }
	  //converts the hex base to integer values.
	  private static int hex2Int(char c) {
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
	      return -1;

	  }


	public static void main(String[] args) throws Exception {
		Crypt cry = new Crypt() ;
		//byte[] key = cry.getKey();
		byte[] key = cry.getEasyKey() ;

		String s = "刘润玉asdfasdf4554465465" ;
		
		System.out.println("加密前:" + s) ;
		byte[] en = cry.encode(s.getBytes(), key) ;
		String enHex = cry.byte2hex(en) ;
		System.out.println("加密后(16进制):" + enHex) ;
		
		
		byte[] enByte = cry.hex2Byte(enHex) ;
		byte[] de = cry.decode(enByte , key);
		System.out.println("解密后:" + new String(de)) ;
		
		
		byte[] md = cry.md5("测试md5".getBytes());
		System.out.println("md5签名结果(16进制):" + cry.byte2hex(md)) ;
	}
}
