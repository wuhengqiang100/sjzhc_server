package com.kexin.common.util;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.commons.codec.binary.Base64;
import java.io.UnsupportedEncodingException;


/**
 * @Description:md5加密方法
 * @Author:   @Date: 2019/10/30 16:10
 * @Param:
 * @Return: 
 */
public class CryptographyUtil {

	private static final String DEFAULT_URL_ENCODING = "UTF-8"; //默认的加密编码
	/**
	 * Md5加密
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str, String salt){
		return new Md5Hash(str,salt).toString();
	}
	public static String md5NotSalt(String str){
		return  new Md5Hash(str).toString();
	}

	/**
	 * base加密
	 * @param input
	 * @return
	 */
	public static String encodeBase64(String input) {

		try {
			return new String(Base64.encodeBase64(input.getBytes(DEFAULT_URL_ENCODING)));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * base64解密
	 * @param input
	 * @return
	 */
	public static String decodeBase64String(String input) {
		try {
			return new String(Base64.decodeBase64(input.getBytes()), DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public static void main(String[] args) {
		String password="123456";
		
		System.out.println("Md5方法"+ CryptographyUtil.md5(password, "Admin"));
		System.out.println("Md5方法"+ CryptographyUtil.md5NotSalt("111111"));
		String encodeBase64String=CryptographyUtil.encodeBase64("Admin");
		System.out.println("base64加密"+ encodeBase64String);
		System.out.println("base64解密"+ CryptographyUtil.decodeBase64String(encodeBase64String));

	}
}
