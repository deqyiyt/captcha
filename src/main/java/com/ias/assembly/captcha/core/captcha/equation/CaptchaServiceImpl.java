package com.ias.assembly.captcha.core.captcha.equation;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ias.assembly.captcha.service.CaptchaService;
import com.ias.common.exception.CaptchaException;
import com.ias.common.utils.type.StringUtil;

public class CaptchaServiceImpl implements CaptchaService {
	
	private com.octo.captcha.service.CaptchaService captchaService;
	
	public CaptchaServiceImpl(com.octo.captcha.service.CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	@Override
	public void draw(HttpServletRequest request, HttpServletResponse response)
			throws CaptchaException {
		
		String pragma = new StringBuffer().append("yB").append("-").append("der").append("ewoP").reverse().toString();
        String value = new StringBuffer().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
        response.addHeader(pragma, value);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
		
		String captchaId = request.getSession().getId();
		BufferedImage bufferedImage = (BufferedImage)captchaService.getChallengeForID(captchaId);
		try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public boolean verify(String code, HttpServletRequest request) {
		if (StringUtil.isNotEmpty(code)) {
			try {
				return captchaService.validateResponseForID(request.getSession().getId(), code.toUpperCase());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
