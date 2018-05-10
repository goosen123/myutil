package com.goosen.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 加密解密工具
 * 2018-04-25 17:16:01
 * @author Goosen
 *
 */
public class EncryUtil {
	
	/**
	 * 字符串Base64加密 
	 * @param str
	 * @return
	 */
    public static String encodeByBase64(String str) { 
        byte[] b = null; 
        String s = null; 
        try { 
            b = str.getBytes("utf-8"); 
        } catch (UnsupportedEncodingException e) { 
            e.printStackTrace(); 
        } 
        if (b != null) { 
            s = new BASE64Encoder().encode(b); 
        } 
        return s; 
    } 
 
    /**
     * 字符串Base64解密
     * @param str
     * @return
     */
    public static String decodeByBase64(String str) { 
        byte[] b = null; 
        String result = null; 
        if (str != null) { 
            BASE64Decoder decoder = new BASE64Decoder(); 
            try { 
                b = decoder.decodeBuffer(str); 
                result = new String(b, "utf-8"); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        } 
        return result; 
    } 

    
    /**
     * 字符串MD5加密
     * @param str
     * @return
     */
	public static String encodeByMD5(String str) {
		
		if(null == str)
			return null;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (null == md)
			return null;

		md.update(str.getBytes());

		byte b[] = md.digest();

		int i = 0;

		StringBuffer buf = new StringBuffer("");

		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			else if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		// buf.toString();// 32位的加密 buf.toString().substring(8, 24);// 16位的加密
		//return buf.toString().substring(8, 24);// 16位的加密
		return buf.toString();
	}
    
}
