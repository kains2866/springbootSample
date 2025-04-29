package com.kains.Methods;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Java反射机制学习示例
 * 这个类展示了Java反射的各种用法
 */
public class ReflectionMethods {

    public static void main(String[] args) {
        try {
            // 1. 获取Class对象的多种方式
            demonstrateGettingClass();

            // 2. 获取类的信息
            demonstrateClassInfo();

            // 3. 使用反射创建对象
            demonstrateCreatingObjects();

            // 4. 使用反射调用方法
            demonstrateMethodInvocation();

            // 5. 访问和修改字段
            demonstrateFieldAccess();

            // 6. 获取注解信息
            demonstrateAnnotations();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示获取Class对象的多种方式
     */
    private static void demonstrateGettingClass() {
        System.out.println("\n=== 获取Class对象的多种方式 ===");

        // 1. 通过类名.class
        Class<?> class1 = ExampleClass.class;
        System.out.println("方式1 - 通过类名.class: " + class1.getName());

        // 2. 通过对象.getClass()
        ExampleClass obj = new ExampleClass();
        Class<?> class2 = obj.getClass();
        System.out.println("方式2 - 通过对象.getClass(): " + class2.getName());

        // 3. 通过Class.forName()
        try {
            Class<?> class3 = Class.forName("com.kains.Methods.ExampleClass");
            System.out.println("方式3 - 通过Class.forName(): " + class3.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 演示获取类的信息
     */
    private static void demonstrateClassInfo() {
        System.out.println("\n=== 获取类的信息 ===");
        Class<?> clazz = ExampleClass.class;

        // 获取类名
        System.out.println("类名: " + clazz.getSimpleName());
        System.out.println("完整类名: " + clazz.getName());

        // 获取修饰符
        int modifiers = clazz.getModifiers();
        System.out.println("是否是public: " + Modifier.isPublic(modifiers));

        // 获取父类
        System.out.println("父类: " + clazz.getSuperclass().getName());

        // 获取接口
        System.out.println("实现的接口: " + Arrays.toString(clazz.getInterfaces()));

        // 获取所有公共方法
        System.out.println("\n公共方法:");
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        // 获取所有字段
        System.out.println("\n声明的字段:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName() + ": " + field.getType());
        }
    }

    /**
     * 演示使用反射创建对象
     */
    private static void demonstrateCreatingObjects() throws Exception {
        System.out.println("\n=== 创建对象 ===");

        // 使用默认构造器
        Class<?> clazz = ExampleClass.class;
        ExampleClass obj1 = (ExampleClass) clazz.newInstance();
        System.out.println("使用默认构造器创建的对象: " + obj1);

        // 使用带参数的构造器
        Constructor<?> constructor = clazz.getConstructor(String.class);
        ExampleClass obj2 = (ExampleClass) constructor.newInstance("反射创建的对象");
        System.out.println("使用带参构造器创建的对象: " + obj2);
    }

    /**
     * 演示使用反射调用方法
     */
    private static void demonstrateMethodInvocation() throws Exception {
        System.out.println("\n=== 调用方法 ===");

        Class<?> clazz = ExampleClass.class;
        ExampleClass obj = new ExampleClass();

        // 调用无参方法
        Method publicMethod = clazz.getMethod("publicMethod");
        publicMethod.invoke(obj);

        // 调用带参方法
        Method setNameMethod = clazz.getMethod("setName", String.class);
        setNameMethod.invoke(obj, "新名字");

        // 调用私有方法
        Method privateMethod = clazz.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true); // 设置可访问
        privateMethod.invoke(obj);
    }

    /**
     * 演示访问和修改字段
     */
    private static void demonstrateFieldAccess() throws Exception {
        System.out.println("\n=== 访问和修改字段 ===");

        ExampleClass obj = new ExampleClass();
        Class<?> clazz = obj.getClass();

        // 访问公共字段
        Field nameField = clazz.getField("publicField");
        System.out.println("公共字段原值: " + nameField.get(obj));
        nameField.set(obj, "新的公共值");
        System.out.println("公共字段新值: " + nameField.get(obj));

        // 访问私有字段
        Field privateField = clazz.getDeclaredField("privateField");
        privateField.setAccessible(true); // 设置可访问
        System.out.println("私有字段原值: " + privateField.get(obj));
        privateField.set(obj, "新的私有值");
        System.out.println("私有字段新值: " + privateField.get(obj));
    }

    /**
     * 演示获取注解信息
     */
    private static void demonstrateAnnotations() {
        System.out.println("\n=== 获取注解信息 ===");

        Class<?> clazz = ExampleClass.class;

        // 获取类上的注解
        if (clazz.isAnnotationPresent(ExampleAnnotation.class)) {
            ExampleAnnotation annotation = clazz.getAnnotation(ExampleAnnotation.class);
            System.out.println("类上的注解值: " + annotation.value());
        }

        // 获取方法上的注解
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ExampleAnnotation.class)) {
                ExampleAnnotation annotation = method.getAnnotation(ExampleAnnotation.class);
                System.out.println("方法 " + method.getName() + " 上的注解值: " + annotation.value());
            }
        }
    }
}

/**
 * 用于演示的注解
 */
@interface ExampleAnnotation {
    String value() default "";
}

/**
 * 用于演示反射操作的示例类
 */
@ExampleAnnotation("类注解")
class ExampleClass {
    public String publicField = "公共字段";
    private String privateField = "私有字段";
    private String name;

    public ExampleClass() {
        this.name = "默认名称";
    }

    public ExampleClass(String name) {
        this.name = name;
    }

    @ExampleAnnotation("公共方法注解")
    public void publicMethod() {
        System.out.println("这是一个公共方法");
    }

    private void privateMethod() {
        System.out.println("这是一个私有方法");
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("名字被设置为: " + name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ExampleClass{name='" + name + "'}";
    }
}
