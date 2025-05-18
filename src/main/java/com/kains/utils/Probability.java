package com.kains.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/15
 * @Description:
 */
public class Probability {

    /**
     * 传入概率和分母，返回是否成功
     * @param TProbability
     * @param TDenominator
     * @param <T>
     * @return
     */
    public static <T extends Number> boolean getProbabilisticResults(T TProbability, T TDenominator){
        if (TDenominator.doubleValue()==0){
            throw new RuntimeException("分母不能为0");
        }
        if (TProbability.doubleValue()<=0){
            return false;
        }
        if (TProbability.doubleValue() > TDenominator.doubleValue()){
            throw new RuntimeException("概率不能大于分母");
        }
        double actualProbability=TProbability.doubleValue()/TDenominator.doubleValue();
        return ThreadLocalRandom.current().nextDouble() < actualProbability;
    }

    /**
     * 传入0~100的概率，分母固定100，返回是否成功
     * @param TProbability
     * @return
     * @param <T>
     */
    public static <T extends Number> boolean getProbabilisticResults(T TProbability){
        if (TProbability.doubleValue()<=0){
            return false;
        }
        if (TProbability.doubleValue() > 100){
            return true;
        }
        double TDenominator=100.0;
        double actualProbability=TProbability.doubleValue()/TDenominator;
        return ThreadLocalRandom.current().nextDouble() < actualProbability;
    }




    public static void main(String[] args) {
        System.out.println(getProbabilisticResults(1,2));
        System.out.println(getProbabilisticResults(1.42,2));
        System.out.println(getProbabilisticResults(35,2092));
    }
}
