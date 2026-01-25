package io.code.java;

/**
 * @Author: zongz
 * @Date: 2026-01-25
 */
public class SwitchTest {
    /*
        1、用 case ... -> 替代 case ... :
        2、case 后可以同时跟多个值
        3、-> 跟的是表达式执行结果
     */
    public static void test1(int day) {
        switch (day) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("工作日");
            }
            case 6, 7 -> {
                System.out.println("休息日");
            }
            default -> {
                throw new RuntimeException("非法");
            }
        }
    }

    public static void test2(int day) {
        String str = switch (day) {
            case 1, 2, 3, 4, 5 -> "工作日";
            case 6, 7 -> "休息日";
            default -> throw new RuntimeException("非法");
        };
        System.out.println(str);
    }

    public static void test3(int day) {
        String str = switch (day) {
            // 执行多行代码，需要使用yield
            case 1, 2, 3, 4, 5 -> {
                System.out.println("代码1");
                System.out.println("代码2");
                yield "工作日";
            }
            case 6, 7 -> "休息日";
            default -> throw new RuntimeException("非法");
        };
        System.out.println(str);
    }

    enum Day {
        D1, D2, D3, D4, D5, D6, D7
    }

    public static void test4(Day day) {
        String str = switch (day) {
            // 执行多行代码，需要使用yield
            case D1, D2, D3, D4, D5 -> {
                System.out.println("代码1");
                System.out.println("代码2");
                yield "工作日";
            }
            case D6, D7 -> "休息日";
        };
        System.out.println(str);
    }

    static void test5(Object obj) {
        switch (obj) {
            case Day.D6, Day.D7 -> System.out.println("休息日");
            case Day d -> System.out.println("工作日");
            default -> System.out.println("default");
        }
    }

    public static void main(String[] args) {
        test1(2);
        test2(6);
        test3(1);
        test4(Day.D1);
    }
}
