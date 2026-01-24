package io.code.java.cpu100;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: zongz
 * @Date: 2025-03-01
 */
public class JVMCPU {
    private static ExecutorService service = Executors.newFixedThreadPool(5);
    private static Object lock = new Object();

    public static class yupengTask implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                long sum = 0L;
                while (true) {
                    sum += 1;
                }
            }
        }
    }

    public static void main(String[] args) {

        yupengTask yupengTask = new yupengTask();
        service.execute(yupengTask);

    }
}

