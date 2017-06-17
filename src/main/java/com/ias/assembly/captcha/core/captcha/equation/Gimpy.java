package com.ias.assembly.captcha.core.captcha.equation;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.octo.captcha.image.ImageCaptcha;

public class Gimpy extends ImageCaptcha implements Serializable{
	/**
	 * @Title serialVersionUID
	 * @type long
	 * @date 2015-1-6 下午4:55:35
	 * 含义 TODO
	 */
	private static final long serialVersionUID = 5613147342793210058L;
	
	private String response;
	
	Gimpy(String question, BufferedImage challenge, String response) {
		super(question, challenge);
        this.response = response;
	}

	 /**
     * Validation routine from the CAPTCHA interface. this methods verify if the response is not null and a String and
     * then compares the given response to the internal string.
     *
     * @return true if the given response equals the internal response, false otherwise.
     */
    public final Boolean validateResponse(final Object response) {
        return (null != response && response instanceof String) ? validateResponse((String) response) : Boolean.FALSE;
    }

    /**
     * Very simple validation routine that compares the given response to the internal string.
     *
     * @return true if the given response equals the internal response, false otherwise.
     */
    private final Boolean validateResponse(final String response) {
        return new Boolean(response.equals(this.response));
    }
}
