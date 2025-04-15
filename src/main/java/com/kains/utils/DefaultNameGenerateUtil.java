package com.kains.utils;

/**
 * @author kains
 * @date 2021/12/20
 */

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 此类用于给User对象生成默认的Name属性值
 * UUID生成的用户名保证唯一性，但不具有可读性
 */
public class DefaultNameGenerateUtil {
    // 可读性词库（可根据需要扩展）
    private static final List<String> ADJECTIVES = Arrays.asList(
            "Swift", "Mystic", "Silent", "Happy", "Brave",
            "Red", "Blue", "Golden", "Iron", "Shadow"
    );
    private static final List<String> NOUNS = Arrays.asList(
            "Phoenix", "Wolf", "Dragon", "Eagle", "Lion",
            "Bear", "Storm", "Moon", "Sun", "River"
    );
    private static final Random RANDOM = new Random();

    /**
     * 生成唯一且带有可读性的用户名
     * 格式：形容词 + 名词 + UUID片段 (例如 SwiftWolf_8f3d)
     */
    public static String generateUsername() {
        // 1. 随机选取词库组合
        String adjective = getRandomElement(ADJECTIVES);
        String noun = getRandomElement(NOUNS);

        // 2. 生成 UUID 并截取片段（取8位）
        String uuidPart = UUID.randomUUID().toString()
                .replace("-", "")  // 去掉连字符
                .substring(0, 8);  // 取前8位

        // 3. 组合成最终用户名（可自定义分隔符）
        return String.format("%s%s_%s",
                adjective,
                noun,
                uuidPart
        ).toLowerCase(); // 统一小写
    }

    /**
     * 生成纯UUID用户名（保证绝对唯一）
     */
    public static String generatePureUUIDUsername() {
        return "user_" + UUID.randomUUID().toString().replace("-", "");
    }

    // 辅助方法：随机获取列表元素
    private static <T> T getRandomElement(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
