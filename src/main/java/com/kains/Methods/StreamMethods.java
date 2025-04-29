package com.kains.Methods;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Java Stream API 常用方法示例
 * 这个类展示了 Java 流操作的各种用法
 */
public class StreamMethods {

    public static void main(String[] args) {
        // 创建示例数据
        List<String> stringList = Arrays.asList("Java", "Python", "JavaScript", "Java", "Go", "Python");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 1. 基础流操作示例
        demonstrateBasicOperations(stringList);

        // 2. 中间操作示例
        demonstrateIntermediateOperations(stringList);

        // 3. 终端操作示例
        demonstrateTerminalOperations(numbers);

        // 4. 收集操作示例
        demonstrateCollectOperations(stringList);

        // 5. 数值流操作示例
        demonstrateNumericStreams();

        // 6. 并行流操作示例
        demonstrateParallelStreams(numbers);

        // 7. Optional 使用示例
        demonstrateOptional(stringList);
    }

    /**
     * 演示基础流操作
     */
    private static void demonstrateBasicOperations(List<String> list) {
        System.out.println("\n=== 基础流操作示例 ===");
        
        // filter：过滤元素
        List<String> filtered = list.stream()
                .filter(s -> s.startsWith("J"))
                .collect(Collectors.toList());
        System.out.println("以J开头的语言: " + filtered);

        // map：转换元素
        List<Integer> lengths = list.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("每个单词的长度: " + lengths);

        // flatMap：展平嵌套集合
        List<List<Integer>> nestedNumbers = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6)
        );
        List<Integer> flattenedNumbers = nestedNumbers.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println("展平后的数字: " + flattenedNumbers);
    }

    /**
     * 演示中间操作
     */
    private static void demonstrateIntermediateOperations(List<String> list) {
        System.out.println("\n=== 中间操作示例 ===");

        // distinct：去重
        List<String> distinct = list.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("去重后的列表: " + distinct);

        // sorted：排序
        List<String> sorted = list.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("排序后的列表: " + sorted);

        // limit：限制元素数量
        List<String> limited = list.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("限制3个元素: " + limited);

        // skip：跳过元素
        List<String> skipped = list.stream()
                .skip(2)
                .collect(Collectors.toList());
        System.out.println("跳过前2个元素: " + skipped);
    }

    /**
     * 演示终端操作
     */
    private static void demonstrateTerminalOperations(List<Integer> numbers) {
        System.out.println("\n=== 终端操作示例 ===");

        // count：计数
        long count = numbers.stream().count();
        System.out.println("元素总数: " + count);

        // reduce：归约操作
        Optional<Integer> sum = numbers.stream()
                .reduce(Integer::sum);
        System.out.println("所有数字的和: " + sum.orElse(0));

        // anyMatch：是否存在匹配元素
        boolean hasEven = numbers.stream()
                .anyMatch(n -> n % 2 == 0);
        System.out.println("是否包含偶数: " + hasEven);

        // allMatch：是否所有元素都匹配
        boolean allPositive = numbers.stream()
                .allMatch(n -> n > 0);
        System.out.println("是否全部为正数: " + allPositive);
    }

    /**
     * 演示收集操作
     */
    private static void demonstrateCollectOperations(List<String> list) {
        System.out.println("\n=== 收集操作示例 ===");

        // 收集到List
        List<String> collectedList = list.stream()
                .collect(Collectors.toList());
        System.out.println("收集到List: " + collectedList);

        // 收集到Set（去重）
        Set<String> collectedSet = list.stream()
                .collect(Collectors.toSet());
        System.out.println("收集到Set: " + collectedSet);

        // 收集到Map
        Map<String, Integer> lengthMap = list.stream()
                .collect(Collectors.toMap(
                        s -> s,
                        String::length,
                        (existing, replacement) -> existing // 处理重复键
                ));
        System.out.println("收集到Map: " + lengthMap);

        // 分组
        Map<Integer, List<String>> groupedByLength = list.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("按长度分组: " + groupedByLength);

        // 连接字符串
        String joined = list.stream()
                .collect(Collectors.joining(", "));
        System.out.println("连接字符串: " + joined);
    }

    /**
     * 演示数值流操作
     */
    private static void demonstrateNumericStreams() {
        System.out.println("\n=== 数值流操作示例 ===");

        // 创建数值流
        IntStream intStream = IntStream.rangeClosed(1, 5);
        System.out.println("数值流求和: " + intStream.sum());

        // 统计
        IntSummaryStatistics stats = IntStream.rangeClosed(1, 5)
                .summaryStatistics();
        System.out.println("最小值: " + stats.getMin());
        System.out.println("最大值: " + stats.getMax());
        System.out.println("平均值: " + stats.getAverage());
        System.out.println("总和: " + stats.getSum());
        System.out.println("数量: " + stats.getCount());
    }

    /**
     * 演示并行流操作
     */
    private static void demonstrateParallelStreams(List<Integer> numbers) {
        System.out.println("\n=== 并行流操作示例 ===");

        // 串行流处理
        long start = System.currentTimeMillis();
        numbers.stream()
                .map(n -> performHeavyOperation(n))
                .collect(Collectors.toList());
        System.out.println("串行流耗时: " + (System.currentTimeMillis() - start) + "ms");

        // 并行流处理
        start = System.currentTimeMillis();
        numbers.parallelStream()
                .map(n -> performHeavyOperation(n))
                .collect(Collectors.toList());
        System.out.println("并行流耗时: " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 演示Optional的使用
     */
    private static void demonstrateOptional(List<String> list) {
        System.out.println("\n=== Optional使用示例 ===");

        // 创建Optional
        Optional<String> first = list.stream()
                .filter(s -> s.startsWith("J"))
                .findFirst();

        // isPresent和get
        if (first.isPresent()) {
            System.out.println("找到以J开头的单词: " + first.get());
        }

        // orElse
        String value = first.orElse("没有找到");
        System.out.println("使用orElse的结果: " + value);

        // orElseGet
        String valueGet = first.orElseGet(() -> "没有找到");
        System.out.println("使用orElseGet的结果: " + valueGet);

        // ifPresent
        first.ifPresent(s -> System.out.println("使用ifPresent处理: " + s));
    }

    /**
     * 模拟耗时操作
     */
    private static int performHeavyOperation(int n) {
        try {
            Thread.sleep(100); // 模拟耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return n * 2;
    }
}
