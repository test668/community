package community.community.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @Author by wyc
 * @Date 2021/1/18.
 */
@Component
public class VerCodeGenerateUtil {
    private static final String SYMBOLS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();
    /**
     * 生成4位随机验证码
     * @return 返回4位验证码
     */
    public static String getVerCode() {
        char[] nonceChars = new char[4];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }
}

