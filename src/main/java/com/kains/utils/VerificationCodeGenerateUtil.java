package com.kains.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description: 生成各式各样的验证码，4位纯数字，4位数字加字母，6位纯数字，6位数字加字母
 */
public class VerificationCodeGenerateUtil {
    private final static Random RANDOM = new Random();

    private final static List<String> CHARACTERS = Arrays.asList(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    );
    private final static List<String> NUMBERS = Arrays.asList(
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    );

    /**
     * 生成纯数字验证码，4位
     */
    public static String generatePureNumberCode_4() {
        StringBuilder stringBuilder = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(getRandomElement(NUMBERS));
        }
        return stringBuilder.toString();
    }

    /**
     * 生成数字加字母验证码，4位
     */
    public static String generateNumberAndCharacterCode_4() {
        StringBuilder stringBuilder = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            if (randomBoolean() == true) {
                stringBuilder.append(getRandomElement(NUMBERS));
            } else {
                stringBuilder.append(getRandomElement(CHARACTERS));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 生成纯数字验证码，6位
     */
    public static String generatePureNumberCode_6() {
        StringBuilder stringBuilder = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(getRandomElement(NUMBERS));
        }
        return stringBuilder.toString();
    }

    /**
     * 生成数字加字母验证码，6位
     */
    public static String generateNumberAndCharacterCode_6() {
        StringBuilder stringBuilder = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            if (randomBoolean() == true) {
                stringBuilder.append(getRandomElement(NUMBERS));
            } else {
                stringBuilder.append(getRandomElement(CHARACTERS));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 辅助随机选择
     *
     * @param list
     * @param <T>
     * @return
     */
    private static <T> T getRandomElement(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    /**
     * 以50%的概率返回true或false
     *
     * @return boolean 随机布尔值
     */
    private static boolean randomBoolean() {
        return RANDOM.nextBoolean();
    }
}
