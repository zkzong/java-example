package io.code.java.record;

import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zongz
 * @Date: 2026-01-24
 */
public class RecordTest {
    /*
        适用场景：
        1、作为数据载体，例如 DTO, VO 等
        2、相对于属性可变的普通 pojo, record 对象更适合函数式编程
     */

    String cvs = """
            张三,18
            李四,20
            王五,19
            赵六,20
            张四,22
            王八,15
            """;

    /**
     * 需求：
     * 把cvs数据
     * 1.转换成java对象
     * 2.保留20岁以上的
     * 3.姓相同的分到一组
     */
    @Test
    public void test() {
        Map<Character, List<Teacher>> collect = Arrays.stream(cvs.split("\n"))
                .map(line -> {
                    String[] split = line.split(",");
                    return new Teacher(split[0], Integer.valueOf(split[1]));
                })
                .filter(teacher -> teacher.age() >= 20)
                .collect(Collectors.groupingBy(teacher -> teacher.name().charAt(0)));
        System.out.println(collect);
    }

    // 1. 组件（属性）是浅层不可变
    @Test
    public void test2() {
        Student student = new Student("张三", 18, new String[]{"北京", "上海"});
        student.address()[0] = "深圳";
        System.out.println(Arrays.toString(student.address()));
    }

    // 2. Compact 构造
    @Test
    public void test3() {
        /*
                public Student {
                    if (name == null) {
                        throw new RuntimeException("name 不能为 null");
                    }
                }
         */
        Student student = new Student(null, 18, new String[]{"北京", "上海"});
    }

    // 3. 能做的和不能做的
    @Test
    public void test4() {
        Student student = new Student("张三", 18, new String[]{"北京", "上海"});
        System.out.println(student);

        student.bar();

        Student s = new Student("张三", 18, new String[]{"北京", "上海"});
        System.out.println(s.getClass().isRecord());

        for (RecordComponent recordComponent : s.getClass().getRecordComponents()) {
            System.out.println(recordComponent);
        }
    }

    // Record Pattern
    // Java 21支持
    //@Test
    //public void test5() {
    //    Object o = new Student("张三", 18, new String[]{"北京", "上海"});
    //    if (o instanceof Student student) {
    //        System.out.println(student.name());
    //    }
    //
    //    if (o instanceof Student(String name, Integer age, String[] address)) {
    //        System.out.println(name());
    //    }
    //}
}
