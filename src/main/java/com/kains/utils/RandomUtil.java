package com.kains.utils;

import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/04/2025/4/29
 * @Description:
 */
public class RandomUtil {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()_+-=[]{}|;:',.<>/?";
    private static final String ALL_CHARS = UPPER + LOWER + DIGITS + SPECIAL;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成强密码，包含至少一个大写字母、一个小写字母、一个数字和一个特殊字符，且长度为10位
     * @return
     */
    public static String generateStrongPassword() {
        char[] password = new char[10];
        // 确保包含至少一个每种字符类型
        password[0] = getRandomChar(UPPER);
        password[1] = getRandomChar(LOWER);
        password[2] = getRandomChar(DIGITS);
        password[3] = getRandomChar(SPECIAL);
        // 填充剩余字符
        for (int i = 4; i < 10; i++) {
            password[i] = ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length()));
        }
        // 随机打乱数组顺序
        shuffleArray(password);
        return new String(password);
    }

    private static char getRandomChar(String charSet) {
        return charSet.charAt(RANDOM.nextInt(charSet.length()));
    }
    private static void shuffleArray(char[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
