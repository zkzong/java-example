//package io.code.java;
//
//import org.checkerframework.checker.units.qual.A;
//
///**
// * @Author: zongz
// * @Date: 2026-01-25
// */
//public class Switch21Test {
//
//    public static void main(String[] args) {
//        test1(new A());
//        test1(new B());
//        test1(null);
//        test1("aaa");
//
//
//    }
//
//    static void test1(Object obj) {
//        if (obj instanceof A a) {
//            System.out.println("类型A");
//        } else if (obj instanceof B b) {
//            System.out.println("类型B");
//        }
//
//        switch (obj) {
//            case A a -> System.out.println("类型A");
//            case B b -> System.out.println("类型B");
//            case null, default -> System.out.println("其他类型");
//        }
//    }
//
//    static class A {
//        String name;
//    }
//
//    static class B {
//        String title;
//    }
//
//    record Student(String name, Integer age) {
//
//    }
//
//    static void test2(Object obj) {
//        switch (obj) {
//            case Student stu -> System.out.println(stu.name + " " + stu.age);
//            case Student(String name, Integer age) -> System.out.println(name + " " + age);
//            case Student(String name, Integer age) when age >= 30 -> System.out.println(name + " " + age);
//            default -> System.out.println("default");
//        }
//    }
//}
