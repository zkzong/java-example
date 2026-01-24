package org.example.java.record;

import java.util.Arrays;

/**
 * @Author: zongz
 * @Date: 2026-01-24
 */
public record Student(String name, Integer age, String[] address) {
    //public Student(String name, Integer age, String[] address) {
    //    if (name == null) {
    //        throw new RuntimeException("name 不能为 null");
    //    }
    //    this.name = name;
    //    this.age = age;
    //    this.address = address;
    //}

    // Compact 构造
    public Student {
        if (name == null) {
            throw new RuntimeException("name 不能为 null");
        }
    }

    static int i;

    static void foo() {
    }

    static {
        System.out.println();
    }

    public void bar() {

    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + Arrays.toString(address) +
                '}';
    }
}
