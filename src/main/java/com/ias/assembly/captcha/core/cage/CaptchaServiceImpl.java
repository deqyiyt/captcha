package com.ias.assembly.captcha.core.cage;

import static com.ias.assembly.captcha.common.Constants.Captcha.IMAGE_HEIGHT;
import static com.ias.assembly.captcha.common.Constants.Captcha.IMAGE_WIDTH;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.github.cage.Cage;
import com.github.cage.image.Painter;
import com.github.cage.token.RandomTokenGenerator;
import com.ias.assembly.captcha.service.CaptchaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CaptchaServiceImpl implements CaptchaService {
	
    private static Random rnd = new Random();

    private Cage cage;

    private static final String KEY_PREFIX = "captcha_";

    public CaptchaServiceImpl() {
        Painter painter = new Painter(IMAGE_WIDTH, IMAGE_HEIGHT, null, null, null, rnd);
        cage = new Cage(painter, null, null, null, Cage.DEFAULT_COMPRESS_RATIO, new RandomTokenGenerator(rnd, 3, 2), rnd);
    }

    @Override
    public void draw(HttpServletRequest request, HttpServletResponse response) {
    	String token = cage.getTokenGenerator().next();
    	String key = request.getSession().getId();
    	try (ServletOutputStream outputStream = response.getOutputStream()) {
            cage.draw(token, outputStream);
            request.getSession().setAttribute(KEY_PREFIX + key, token);
        } catch (IOException e) {
            log.error("写出图片验证码出现异常", e);
        }
    }

    @Override
    public boolean verify(String code, HttpServletRequest request) {
    	HttpSession session = request.getSession();
        String rawKey = KEY_PREFIX + session.getId();
        String st = (String)session.getAttribute(rawKey);
        if (StringUtils.isEmpty(st) || !st.equalsIgnoreCase(code))
            return false;
        session.removeAttribute(rawKey);
        return true;
    }

}
