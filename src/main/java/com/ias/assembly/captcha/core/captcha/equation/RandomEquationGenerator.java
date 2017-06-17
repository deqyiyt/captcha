package com.ias.assembly.captcha.core.captcha.equation;

import static com.ias.assembly.captcha.common.Constants.Captcha.RANDOM_MAX_RANGE;

import java.util.Locale;
import java.util.Random;

public class RandomEquationGenerator implements WordGenerator {
	
	private String answer;
	
    public String getWord(Integer length, Locale locale) {
        return getWord(length);
    }
    
    public String getWord(Integer length) {  
    	Random random = new Random();
        int intTemp;  
        int intFirst = random.nextInt(RANDOM_MAX_RANGE);  
        int intSec = random.nextInt(RANDOM_MAX_RANGE);  
        String checkCode = "";  
        int result = 0;  
        switch (random.nextInt(6)) {
            case 0:  
                if (intFirst < intSec) {  
                    intTemp = intFirst;  
                    intFirst = intSec;  
                    intSec = intTemp;  
                }  
                checkCode = intFirst + " - " + intSec + " = ?";  
                result = intFirst-intSec;  
                break;  
            case 1:  
                if (intFirst < intSec) {
                    intTemp = intFirst;  
                    intFirst = intSec;  
                    intSec = intTemp;  
                }  
                checkCode = intFirst + " - ? = "+(intFirst-intSec);  
                result = intSec;  
                break;  
            case 2:  
                if (intFirst < intSec) {
                    intTemp = intFirst;  
                    intFirst = intSec;  
                    intSec = intTemp;  
                }  
                checkCode = "? - "+intSec+" = "+(intFirst-intSec);  
                result = intFirst;  
                break;  
            case 3:  
                checkCode = intFirst + " + " + intSec + " = ?";  
                result = intFirst + intSec;  
                break;
            case 4:  
                checkCode = intFirst + " + ? ="+(intFirst+intSec);  
                result = intSec;
                break;  
            case 5:  
                checkCode = "? + " + intSec + " ="+(intFirst+intSec);  
                result = intFirst;  
                break;  
        }
        this.answer = String.valueOf(result);
        return checkCode;  
    }

	/**
	 * answer的获取.
	 * @return String
	 */
	public String getAnswer() {
		return answer;
	}  
}
