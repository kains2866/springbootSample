package com.kains.Methods.playSomething.generate;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description: 此模块随机生成距离，仅用于此模块测试
 */
public class DistanceGenerate {
    private final static int MAX_DISTANCE = 100;
    private final static int MIN_DISTANCE = 0;
    private final static Random RANDOM = new Random();

    /**
     * 生成距离
     * @return
     */
    public static int generateDistance() {
        return RANDOM.nextInt(MAX_DISTANCE - MIN_DISTANCE + 1) + MIN_DISTANCE;
    }
}
