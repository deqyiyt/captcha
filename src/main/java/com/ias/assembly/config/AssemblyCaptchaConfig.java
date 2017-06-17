/**
 * 
 */

package com.ias.assembly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.ias.assembly.captcha.core.captcha.manager.CaptchaManager;
import com.ias.assembly.captcha.prop.CaptchaProp;
import com.ias.assembly.captcha.service.CaptchaService;
import com.ias.common.bo.IasAnnotationBeanNameGenerator;
import com.ias.common.utils.type.StringUtil;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 图片验证码配置类
 *
 * @author: chenbing
 * @create: 2016-05-17 6:35
 */
@Configuration
@PropertySources({
        @PropertySource("classpath:config/ias-assembly-captcha.properties"),
        @PropertySource(value = "file:/ias/config/ias-assembly-captcha.properties", ignoreResourceNotFound = true)
})
@EnableConfigurationProperties({CaptchaProp.class})
@ComponentScan(basePackages = "com.ias.assembly.captcha", nameGenerator = IasAnnotationBeanNameGenerator.class)
public class AssemblyCaptchaConfig {
	
	@Bean("captchaService")
	@Autowired
	public CaptchaService captchaService(CaptchaProp prop) {
		if(StringUtil.equals(prop.getType(), "basic")) {
			return new com.ias.assembly.captcha.core.captcha.CaptchaServiceImpl(captchaManager(prop));
		} else if(StringUtil.equals(prop.getType(), "equation")) {
			return new com.ias.assembly.captcha.core.captcha.CaptchaServiceImpl(captchaManager(prop));
		} else {
			return new com.ias.assembly.captcha.core.cage.CaptchaServiceImpl();
		} 
	}
	
	@Bean
	public CaptchaManager captchaManager(CaptchaProp prop) {
		if(StringUtil.equals(prop.getType(), "equation")) {
			return new CaptchaManager(imageEquationCaptchaService());
		} else {
			return new CaptchaManager(imageCaptchaService());
		}
	}
	
	
	/** 
	 * 配置JCaptcha验证码功能
	 * @author: jiuzhou.hu
	 * @date:2017年6月14日下午4:00:08 
	 * @return
	 */
	private ImageCaptchaService imageCaptchaService() {
		DefaultManageableImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService();
		com.ias.assembly.captcha.core.captcha.basic.CaptchaEngine captchaEngine = new com.ias.assembly.captcha.core.captcha.basic.CaptchaEngine();
		imageCaptchaService.setCaptchaEngine(captchaEngine);
		imageCaptchaService.setMinGuarantedStorageDelayInSeconds(600);
		return imageCaptchaService;
	}
	
	/** 
	 * 配置JCaptcha算式验证码功能
	 * @author: jiuzhou.hu
	 * @date:2017年6月14日下午4:00:21 
	 * @return
	 */
	private ImageCaptchaService imageEquationCaptchaService() {
		DefaultManageableImageCaptchaService imageCaptchaService = new DefaultManageableImageCaptchaService();
		com.ias.assembly.captcha.core.captcha.equation.CaptchaEngine captchaEngine = new com.ias.assembly.captcha.core.captcha.equation.CaptchaEngine();
		imageCaptchaService.setCaptchaEngine(captchaEngine);
		imageCaptchaService.setMinGuarantedStorageDelayInSeconds(600);
		return imageCaptchaService;
	}
}
