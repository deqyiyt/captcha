package com.ias.assembly.captcha.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ias.common.exception.CaptchaException;

public interface CaptchaService {

    void draw(HttpServletRequest request, HttpServletResponse response) throws CaptchaException;

    boolean verify(String code, HttpServletRequest request);
}
