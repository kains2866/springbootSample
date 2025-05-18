package com.kains.utils;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/15
 * @Description:
 */
public class Probability {
    public static Number probability=null;
    public static Number denominator=null;
    public static <T extends Number> boolean result(T TProbability, T TDenominator){
        if (TDenominator.doubleValue()==0){
            throw new RuntimeException("分母不能为0");
        }
        if (TProbability.doubleValue()==0){
            return false;
        }
        if (TProbability.doubleValue() > TDenominator.doubleValue()){
            throw new RuntimeException("概率不能大于分母");
        }

        Probability.probability=TProbability;
        Probability.denominator=TDenominator;
        double actualProbability=probability.doubleValue()/denominator.doubleValue();
        return ThreadLocalRandom.current().nextDouble() < actualProbability;
    }



    public static void main(String[] args) {
        System.out.println(result(1,2));
        System.out.println(result(1.42,2));
        System.out.println(result(35,2092));
    }
}
