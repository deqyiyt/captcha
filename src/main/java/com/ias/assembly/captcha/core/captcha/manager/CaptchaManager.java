package com.ias.assembly.captcha.core.captcha.manager;

import java.awt.image.BufferedImage;

import com.ias.common.utils.type.StringUtil;
import com.octo.captcha.service.CaptchaService;

public class CaptchaManager {

	private CaptchaService captchaService;
	
	public CaptchaManager(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public BufferedImage buildImage(String captchaId) {
		return (BufferedImage) captchaService.getChallengeForID(captchaId);
	}

	public boolean verify(String code, String captchaId) {
		if (StringUtil.isNotEmpty(code)) {
			try {
				return captchaService.validateResponseForID(captchaId, code.toUpperCase());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
