package com.kexin.common.util.encry;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * shiro 加密方法
 */
public class CryptographyUtil {

	private static final String DEFAULT_URL_ENCODING = "UTF-8"; //默认的加密编码
	/**
	 * Md5加密,带盐
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5(String str, String salt){
		return new Md5Hash(str,salt).toString();
	}

	/**
	 * Md5加密,不带盐
	 * @param str
	 * @return
	 */
	public static String md5NotSalt(String str){
		return  new Md5Hash(str).toString();
	}

	/**
	 * base加密
	 * @param input
	 * @return
	 */
	public static String encodeBase64(String input) {
		return Base64.encodeToString(input.getBytes());
	}

	/**
	 * base64解密
	 * @param input
	 * @return
	 */
	public static String decodeBase64String(String input) {
		return Base64.decodeToString(input.getBytes());
	}

	public static void main(String[] args) {
		String password="123456";
		
		System.out.println("Md5加密带盐:"+ CryptographyUtil.md5(password, "Admin"));
		System.out.println("Md5加密不带盐:"+ CryptographyUtil.md5NotSalt("Admin"));

		String encodeBase64String=CryptographyUtil.encodeBase64(password);
		System.out.println("base64加密:"+ encodeBase64String);
		System.out.println("base64解密:"+ CryptographyUtil.decodeBase64String(encodeBase64String));

	}
}
