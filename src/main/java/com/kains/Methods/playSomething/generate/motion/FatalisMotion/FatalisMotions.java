package com.kains.Methods.playSomething.generate.motion.FatalisMotion;

import com.kains.Methods.playSomething.generate.DistanceGenerate;
import com.kains.Methods.playSomething.generate.motion.Motion;
import com.kains.Methods.playSomething.generate.motion.NoMotion;
import com.kains.utils.Probability;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/05/2025/5/18
 * @Description:
 */
public class FatalisMotions extends Motion {
    private FatalisMotion_1 fatalisMotion_1 = new FatalisMotion_1();
    private FatalisMotion_2 fatalisMotion_2 = new FatalisMotion_2();
    private FatalisMotion_3 fatalisMotion_3 = new FatalisMotion_3();
    private FatalisMotion_4 fatalisMotion_4 = new FatalisMotion_4();
    private FatalisMotion_5 fatalisMotion_5 = new FatalisMotion_5();

    @Override
    public void run() {
        NoMotion noMotion = new NoMotion();
        noMotion.run();

        long startTime = System.currentTimeMillis(); // 记录开始时间
        long duration = 5 * 1000; // 5秒
        /**
         * 设置一个状态机，根据状态机进行不同操作
         */
        while (System.currentTimeMillis() - startTime < duration) {
            fatalisMotion_1.run();
            int distance = DistanceGenerate.generateDistance();
            if (distance <= 50) {
                if (Probability.getProbabilisticResults(30)) {
                    fatalisMotion_2.run();
                } else {
                    fatalisMotion_3.run();
                }
            }
            else if (distance <= 100) {
                if (Probability.getProbabilisticResults(70)) {
                    fatalisMotion_4.run();
                } else {
                    fatalisMotion_5.run();
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
