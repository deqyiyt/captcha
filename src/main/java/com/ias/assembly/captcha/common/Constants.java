/**
 * 
 */

package com.ias.assembly.captcha.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    private Constants() {
    }
    
    private static Map<String, String> CODE_MAP = new HashMap<>();
    
    public static final class EXCEPTION {
    	public static final String ASSEMBLY_CAPTCHA_ERR = "20001";

        public static final String SYS_RESPONSE_STREAM_ERR = "10001";
	}

    static {
        CODE_MAP.put(EXCEPTION.ASSEMBLY_CAPTCHA_ERR, "图片验证码调用异常");
        CODE_MAP.put(EXCEPTION.SYS_RESPONSE_STREAM_ERR, "获取输出流异常");
    }

    public static String getMsg(String code) {
        return CODE_MAP.get(code);
    }
    
    public static final class Captcha {
    	
    	/**
    	 * 验证码类型，有三种类型
    	 * basic：简易类型
    	 * cage：防爆破类型	默认
    	 * equation：算式类型
    	 */
    	public static String IMAGE_TYPE;
    	
    	/** 图片宽度 */
    	public static int IMAGE_WIDTH = 80;

    	/** 图片高度 */
    	public static int IMAGE_HEIGHT = 28;
    	
    	/** 最小字体大小 */
    	public static int MIN_FONT_SIZE = 12;

    	/** 最大字体大小 */
    	public static int MAX_FONT_SIZE = 16;

    	/** 最小字符个数 */
    	public static int MIN_WORD_LENGTH = 4;

    	/** 最大字符个数 */
    	public static int MAX_WORD_LENGTH = 14;
    	
    	/** 最大字符个数 */
    	public static int RANDOM_MAX_RANGE = 49;
    	
    	/** 随机种子 */
    	public static String CHAR_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	
    	/** 随机背景图片路径 */
    	public static String BACKGROUND_IMAGE_PATH = "/captcha/";
	}
}
