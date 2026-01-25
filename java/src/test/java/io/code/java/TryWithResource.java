package io.code.java;

import java.io.Closeable;

/**
 * @Author: zongz
 * @Date: 2026-01-25
 */
public class TryWithResource {

    static class MyResource implements Closeable {
        @Override
        public void close() {
            System.out.println("释放资源");
        }
    }

    public static void main(String[] args) {
        try (MyResource myResource = new MyResource()) {
            System.out.println(myResource);
            int i = 1 / 0;
        }

        MyResource myResource = new MyResource();
        try (myResource) {
            System.out.println(myResource);
            int i = 1 / 0;
        }
    }
}
