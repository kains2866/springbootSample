package com.kains.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author kains
 * @Date 2025/04/2025/4/29
 * @Description:据脱敏工具类(可扩展到其他类型的数据，如手机号、邮箱等)
 */
public class DesensitizationUtil {
    public static String desensitizeName(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }

        int length = name.length();
        // 处理长度小于等于2的情况（中英文姓名）
        if (length <= 2) {
            return "*" + name + "*"; // 两边各加一个星号
        }

        // 处理长度大于2的情况
        int keep = (length % 2 == 0) ? 2 : 1; // 偶数保留2位，奇数保留1位
        int start = (length - keep) / 2;      // 计算保留区起始位置
        int end = start + keep;               // 计算保留区结束位置

        // 构建脱敏字符串
        return name.substring(0, start).replaceAll(".", "*")  // 左边脱敏
                + name.substring(start, end)                     // 保留中间字符
                + name.substring(end).replaceAll(".", "*");      // 右边脱敏
    }

    public static String desensitizePhone(String phone) {
        // 判断手机号格式是否正确(中国)
        if (phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
            return phone;
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }
}
