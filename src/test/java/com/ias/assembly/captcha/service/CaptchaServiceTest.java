package com.ias.assembly.captcha.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ias.assembly.captcha.core.captcha.manager.CaptchaManager;
import com.ias.assembly.config.AssemblyCaptchaConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=AssemblyCaptchaConfig.class)
public class CaptchaServiceTest {
	
	@Autowired
	private CaptchaManager captchaManager;

	@Test
	public void buildEquationImage() throws IOException {
		String name = "123456";
		File file = new File("d:/temp/captcha/"+name+".jpg");
		BufferedImage bufferedImage = captchaManager.buildImage(name);
		ImageIO.write(bufferedImage, "jpg", file); 
	}
}
