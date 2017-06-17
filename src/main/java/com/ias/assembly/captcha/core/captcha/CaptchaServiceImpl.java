package com.ias.assembly.captcha.core.captcha;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ias.assembly.captcha.core.captcha.manager.CaptchaManager;
import com.ias.assembly.captcha.service.CaptchaService;
import com.ias.common.exception.CaptchaException;

public class CaptchaServiceImpl implements CaptchaService {
	
	private CaptchaManager captchaManager;
	
	public CaptchaServiceImpl(CaptchaManager captchaManager) {
		this.captchaManager = captchaManager;
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
		BufferedImage bufferedImage = captchaManager.buildImage(captchaId);
		try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	public boolean verify(String code, HttpServletRequest request) {
		return captchaManager.verify(code, request.getSession().getId());
	}

}
