package io.code.java;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 可变类型参数的坑
 *
 * @Author: zongz
 * @Date: 2026-01-24
 */
public class VarVariablesPrimitiveArray {

    public static void main(String[] args) {
        test(new Object(), new Object(), new Object()); // new Object[]{new Object(), new Object(), new Object()}
        System.out.println("------------------------");

        test(1, 2, 3); // new Integer[]{1, 2, 3}
        System.out.println("------------------------");

        test(new int[]{1, 2, 3}); // new Object[]{new int[]{1, 2, 3}}
        test(Arrays.stream(new int[]{1, 2, 3}).boxed().toArray());

        // Object[] objects = new int[]() // 不可以

        System.out.println(String.format("%d %d %d", Arrays.stream(new int[]{1, 2, 3}).boxed().toArray()));

    }

    static void test(Object... objects) {
        for (Object object : objects) {
            System.out.println(object);
        }
    }
}
