package com.kexin.common.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Description:md5加密方法
 * @Author:   @Date: 2019/10/30 16:10
 * @Param:
 * @Return: 
 */
public class CryptographyUtil {
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

	public static void main(String[] args) {
		String password="123456";
		
		System.out.println("Md5方法"+ CryptographyUtil.md5(password, "Admin"));
		System.out.println("Md5方法"+ CryptographyUtil.md5NotSalt("111111"));
	}
}
