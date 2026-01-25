package io.code.java;

/**
 * @Author: zongz
 * @Date: 2026-01-25
 */
public class InstanceOf {

    static abstract class Animal {

    }

    static class Dog extends Animal {
        int age;
        public void foo() {

        }
    }

    static class Cat extends Animal {
        public void bar() {

        }
    }

    // 需求：需在方法内调用子类特有方法
    static void foo(Animal animal) {
        if (animal instanceof Dog) {
            ((Dog) animal).foo();
        }  else if (animal instanceof Cat) {
            ((Cat) animal).bar();
        }

        // 注意变量的作用范围
        if (animal instanceof Dog dog) {
            dog.foo();
        }  else if (animal instanceof Cat cat) {
            cat.bar();
        }

        // 与其他 boolean 表达式一起使用
        // 编译成功
        if (animal instanceof Dog dog && dog.age > 1) {
            dog.foo();
        }
        // 编译报错
        //if (animal instanceof Dog dog || dog.age > 1) {
        //    dog.foo();
        //}
    }
}
