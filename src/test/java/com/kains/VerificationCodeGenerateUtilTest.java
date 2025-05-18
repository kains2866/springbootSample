package com.kains;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description:
 */

import com.kains.utils.VerificationCodeGenerateUtil;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationCodeGenerateUtilTest {

    // 测试 4位纯数字验证码
    @Test
    public void testGeneratePureNumberCode_4() {
        String code = VerificationCodeGenerateUtil.generatePureNumberCode_4();
        System.out.println(code);
        assertEquals(4, code.length(), "4位验证码长度应为4");
        assertTrue(code.matches("\\d+"), "4位验证码应为纯数字");
    }

    // 测试 6位纯数字验证码
    @Test
    public void testGeneratePureNumberCode_6() {
        String code = VerificationCodeGenerateUtil.generatePureNumberCode_6();
        System.out.println(code);
        assertEquals(6, code.length(), "6位验证码长度应为6");
        assertTrue(code.matches("\\d+"), "6位验证码应为纯数字");
    }

    // 测试 4位数字加字母验证码
    @Test
    public void testGenerateNumberAndCharacterCode_4() {
        String code = VerificationCodeGenerateUtil.generateNumberAndCharacterCode_4();
        System.out.println(code);
        assertEquals(4, code.length(), "4位验证码长度应为4");
        assertTrue(Pattern.matches("^[a-zA-Z0-9]+$", code), "4位验证码应为数字或大小写字母");
    }

    // 测试 6位数字加字母验证码
    @Test
    public void testGenerateNumberAndCharacterCode_6() {
        String code = VerificationCodeGenerateUtil.generateNumberAndCharacterCode_6();
        System.out.println(code);
        assertEquals(6, code.length(), "6位验证码长度应为6");
        assertTrue(Pattern.matches("^[a-zA-Z0-9]+$", code), "6位验证码应为数字或大小写字母");
    }

}
