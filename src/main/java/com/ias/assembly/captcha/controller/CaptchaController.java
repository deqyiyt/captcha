package com.ias.assembly.captcha.controller;

import static com.ias.assembly.captcha.common.Constants.EXCEPTION.ASSEMBLY_CAPTCHA_ERR;
import static com.ias.assembly.captcha.common.Constants.EXCEPTION.SYS_RESPONSE_STREAM_ERR;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ias.assembly.captcha.common.Constants;
import com.ias.assembly.captcha.service.CaptchaService;
import com.ias.common.exception.CaptchaException;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CaptchaController {
	
    @Autowired
    private CaptchaService captchaService;
    
    @GetMapping(value = "/common/captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
    	setRespHeaders(response);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
        	captchaService.draw(request, response);
        } catch (CaptchaException e) {
            log.error("调用图片验证码方法出现异常", e);
            throw new CaptchaException(ASSEMBLY_CAPTCHA_ERR, Constants.getMsg(ASSEMBLY_CAPTCHA_ERR));
        } catch (IOException e1) {
            log.error("输出图片验证码出现异常", e1);
            throw new CaptchaException(SYS_RESPONSE_STREAM_ERR, Constants.getMsg(SYS_RESPONSE_STREAM_ERR));
        }
    }
    
    @GetMapping(value = "/common/captcha/{code}.do")
    @ResponseBody
    public String verify(HttpServletRequest request, @PathVariable("code") String code) {
    	if(captchaService.verify(code, request)) {
    		return "success";
    	} else {
    		return "error";
    	}
    }
    
    private void setRespHeaders(HttpServletResponse rsp) {
        rsp.setContentType("image/jpeg");
        rsp.setHeader("Cache-Control", "no-cache, no-store");
        rsp.setHeader("Pragma", "no-cache");
        final long time = System.currentTimeMillis();
        rsp.setDateHeader("Last-Modified", time);
        rsp.setDateHeader("Date", time);
        rsp.setDateHeader("Expires", time);
    }
}