package com.ias.assembly.captcha.core.captcha.basic;

import static com.ias.assembly.captcha.common.Constants.Captcha.BACKGROUND_IMAGE_PATH;
import static com.ias.assembly.captcha.common.Constants.Captcha.CHAR_STRING;
import static com.ias.assembly.captcha.common.Constants.Captcha.IMAGE_HEIGHT;
import static com.ias.assembly.captcha.common.Constants.Captcha.IMAGE_WIDTH;
import static com.ias.assembly.captcha.common.Constants.Captcha.MAX_FONT_SIZE;
import static com.ias.assembly.captcha.common.Constants.Captcha.MAX_WORD_LENGTH;
import static com.ias.assembly.captcha.common.Constants.Captcha.MIN_FONT_SIZE;
import static com.ias.assembly.captcha.common.Constants.Captcha.MIN_WORD_LENGTH;

import java.awt.Color;
import java.awt.Font;

import org.springframework.core.io.ClassPathResource;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.JarFileReaderRandomBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * 验证码图片生成
 * @author FQ
 *
 */
public class CaptchaEngine extends ListImageCaptchaEngine{

	/**
	 * 随机字体
	 */
	private static final Font[] FONTS = new Font[] { new Font("nyala", Font.BOLD, MAX_FONT_SIZE), new Font("Arial", Font.BOLD, MAX_FONT_SIZE), new Font("nyala", Font.BOLD, MAX_FONT_SIZE), new Font("Bell", Font.BOLD, MAX_FONT_SIZE), new Font("Bell MT", Font.BOLD, MAX_FONT_SIZE), new Font("Credit", Font.BOLD, MAX_FONT_SIZE), new Font("valley", Font.BOLD, MAX_FONT_SIZE),
			new Font("Impact", Font.BOLD, MAX_FONT_SIZE) };

	/**
	 * 随机颜色
	 */
	private static final Color[] COLORS = new Color[] { new Color(255, 255, 255), new Color(255, 220, 220), new Color(220, 255, 255), new Color(220, 220, 255), new Color(255, 255, 220), new Color(220, 255, 220) };

	/**
	 * 验证码图片生成
	 */
	@Override
	protected void buildInitialFactories() {
		FontGenerator fontGenerator = new RandomFontGenerator(MIN_FONT_SIZE, MAX_FONT_SIZE, FONTS);
		BackgroundGenerator backgroundGenerator = new JarFileReaderRandomBackgroundGenerator(IMAGE_WIDTH, IMAGE_HEIGHT, new ClassPathResource(BACKGROUND_IMAGE_PATH).getPath());
		TextPaster textPaster = new DecoratedRandomTextPaster(MIN_WORD_LENGTH, MAX_WORD_LENGTH, new RandomListColorGenerator(COLORS), new TextDecorator[] {});
		addFactory(new GimpyFactory(new RandomWordGenerator(CHAR_STRING), new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster)));
	}
	
}
