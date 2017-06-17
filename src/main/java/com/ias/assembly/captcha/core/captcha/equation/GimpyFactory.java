package com.ias.assembly.captcha.core.captcha.equation;

import java.awt.image.BufferedImage;
import java.util.Locale;

import com.octo.captcha.CaptchaException;
import com.octo.captcha.CaptchaQuestionHelper;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.image.ImageCaptcha;

public class GimpyFactory extends com.octo.captcha.image.gimpy.GimpyFactory{
	
	private WordGenerator wordGenerator;
	
	public WordGenerator getWordGenerator() {
        return wordGenerator;
    }

	public GimpyFactory(WordGenerator generator, WordToImage word2image) {
		super(generator, word2image);
		this.wordGenerator = generator;
		// TODO Auto-generated constructor stub
	}
	
	/**
     * gimpies are ImageCaptcha
     *
     * @return a pixCaptcha with the question :"spell the word"
     */
    public ImageCaptcha getImageCaptcha(Locale locale) {

        //length
        Integer wordLength = getRandomLength();

        String word = getWordGenerator().getWord(wordLength, locale);

        BufferedImage image = null;
        try {
            image = getWordToImage().getImage(word);
        } catch (Throwable e) {
            throw new CaptchaException(e);
        }
        ImageCaptcha captcha = new Gimpy(CaptchaQuestionHelper.getQuestion(locale, BUNDLE_QUESTION_KEY), image, getWordGenerator().getAnswer());
        return captcha;
    }

}
