package com.ias.assembly.captcha.core.captcha.equation;


/**
 * <p>This interface defines methods to retrieve random words </p>.
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue</a>
 * @version 1.0
 */
public interface WordGenerator extends com.octo.captcha.component.word.wordgenerator.WordGenerator{
    /**
     * @Project FFT Client Personal AnHui
     * @Package com.froad.fft.bean
     * @Method getAnswer方法.<br>
     * @Description 算式答案
     * @author hjz
     * @date 2015-1-6 下午5:14:35
     * @return
     */
    String getAnswer();

}

