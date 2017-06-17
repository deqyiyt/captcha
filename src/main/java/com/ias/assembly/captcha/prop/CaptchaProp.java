/**
 * 
 */

package com.ias.assembly.captcha.prop;

import static com.ias.assembly.captcha.common.Constants.Captcha.BACKGROUND_IMAGE_PATH;
import static com.ias.assembly.captcha.common.Constants.Captcha.CHAR_STRING;
import static com.ias.assembly.captcha.common.Constants.Captcha.IMAGE_HEIGHT;
import static com.ias.assembly.captcha.common.Constants.Captcha.IMAGE_TYPE;
import static com.ias.assembly.captcha.common.Constants.Captcha.IMAGE_WIDTH;
import static com.ias.assembly.captcha.common.Constants.Captcha.MAX_FONT_SIZE;
import static com.ias.assembly.captcha.common.Constants.Captcha.MAX_WORD_LENGTH;
import static com.ias.assembly.captcha.common.Constants.Captcha.MIN_FONT_SIZE;
import static com.ias.assembly.captcha.common.Constants.Captcha.MIN_WORD_LENGTH;
import static com.ias.assembly.captcha.common.Constants.Captcha.RANDOM_MAX_RANGE;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.ias.common.utils.type.StringUtil;

import lombok.Data;

/**
 * 图片验证码配置项
 */
@ConfigurationProperties(prefix = "com.ias.assembly.captcha")
@Data
public class CaptchaProp {

    /**
     * 图片宽度
     */
    private int width;
    
    /**
     * 图片高度
     */
    private int height;
    
    /**
     * 随机种子
     */
    private String charString;
    
    /** 
     * 随机背景图片路径 
     */
    private String backgroundImagePath;
    
    /** 
     * 最小字体大小 
     */
	private int minFontSize;

	/** 
	 * 最大字体大小
	 */
	private int maxFontSize;

	/** 
	 * 最小字符个数 
	 */
	private int minWordLength;

	/** 
	 * 最大字符个数 
	 */
	private int maxWordLength;
	
	/** 
	 * 算式单个最大数字
	 */
	private int randomMaxRange;
    
    
    /**
	 * 验证码类型，有三种类型
	 * basic：简易类型
	 * cage：防爆破类型	默认
	 * equation：算式类型
	 */
    private String type;
    
    @PostConstruct
	public void postConstruct(){
    	if(width > 0) {
    		IMAGE_WIDTH = width;
    	}
    	if(height > 0) {
    		IMAGE_HEIGHT = getHeight();
    	}
    	if(StringUtil.isNotBlank(charString)) {
    		CHAR_STRING = charString;
    	}
    	if(StringUtil.isNotBlank(charString)) {
    		IMAGE_TYPE = type;
    	}
    	if(minFontSize > 0) {
    		MIN_FONT_SIZE = minFontSize;
    	}
    	
    	if(maxFontSize > 0) {
    		MAX_FONT_SIZE = maxFontSize;
    	}
    	
    	if(minWordLength > 0) {
    		MIN_WORD_LENGTH = minWordLength;
    	}
    	
    	if(maxWordLength > 0) {
    		MAX_WORD_LENGTH = maxWordLength;
    	}
    	if(StringUtil.isNotBlank(backgroundImagePath)) {
    		BACKGROUND_IMAGE_PATH = backgroundImagePath;
    	}
    	if(randomMaxRange > 0) {
    		RANDOM_MAX_RANGE = randomMaxRange;
    	}
    }
}
