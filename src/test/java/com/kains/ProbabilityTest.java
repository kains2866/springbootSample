package com.kains;

import com.kains.utils.Probability;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/15
 * @Description:
 */
public class ProbabilityTest {

    @Test
    void testResultWithValidProbability() {
        // 测试50%概率（1/2）
        int trueCount = 0;
        int trials = 10000;
        for (int i = 0; i < trials; i++) {
            if (Probability.result(1, 2)) {
                trueCount++;
            }
        }
        double actualProbability = (double) trueCount / trials;
        // 允许1%的误差范围
        assertEquals(0.5, actualProbability, 0.01);
    }

    @Test
    void testResultWithZeroProbability() {
        // 测试0概率（0/100）
        assertFalse(Probability.result(0, 100));
    }

    @Test
    void testResultWithCertainProbability() {
        // 测试100%概率（5/5）
        assertTrue(Probability.result(5, 5));
    }

    @Test
    void testResultWithDifferentNumberTypes() {
        // 测试不同数值类型（Integer和Double混合）
        assertDoesNotThrow(() -> Probability.result(1, 2.0));
        assertDoesNotThrow(() -> Probability.result(3.0f, 4L));
    }

    @Test
    void testResultWithZeroDenominator() {
        // 测试分母为0的异常情况
        assertThrows(RuntimeException.class, () -> Probability.result(1, 0));
    }

    @Test
    void testProbabilityFieldsAreSet() {
        // 测试静态字段是否正确设置
        Probability.result(3, 7);
        assertEquals(3, Probability.probability.intValue());
        assertEquals(7, Probability.denominator.intValue());
    }

    @Test
    void testEdgeCaseVerySmallProbability() {
        // 测试极小概率（1/10000）
        int trueCount = 0;
        int trials = 100000;
        for (int i = 0; i < trials; i++) {
            if (Probability.result(1, 10000)) {
                trueCount++;
            }
        }
        double actualProbability = (double) trueCount / trials;
        assertEquals(0.0001, actualProbability, 0.00005);
    }
}
