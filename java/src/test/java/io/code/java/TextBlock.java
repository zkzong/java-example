package io.code.java;

import java.util.Arrays;

/**
 * @Author: zongz
 * @Date: 2026-01-25
 */
public class TextBlock {

    public static void main(String[] args) {
        String json = """
                {
                    "name":"张三",
                    "age":28
                }
                """;
        System.out.println(json);

        json = """
                {
                    "name":"%s",
                    "age":%d
                }
                """.formatted("李四", 20);
        System.out.println(json);

        // 注意事项1 - 换行
        String s1 = """
                a
                b
                c""";
        String s2 = """
                a
                b
                c
                """;
        System.out.println(Arrays.toString(s1.getBytes()));
        System.out.println(Arrays.toString(s2.getBytes()));

        // 注意事项2 - 前空格
        String s3 = """
                a
                b
                c
                """;
        String s4 = """
                 a
                 b
                 c
                """;
        System.out.println(s3);
        System.out.println(s4);

        // 注意事项3 - 后空格
        String s5 = """
                a
                b
                c
                """;
        System.out.println(s5);
        System.out.println(Arrays.toString(s5.getBytes()));
        String s6 = """
                a  \s
                b
                c
                """;
        System.out.println(s6);
        System.out.println(Arrays.toString(s6.getBytes()));

    }

}
