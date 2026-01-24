package io.code.java.multithread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zongz
 * @Date: 2024-12-14
 */
public class CompletableFutureTest {

    /***
     * 基本用法
     */
    @Test
    public void base() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 模拟耗时操作
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 42;
        });

        // 获取结果
        future.thenAccept(result -> System.out.println("结果: " + result));
    }

    /***
     * 使用 thenCombine 合并结果
     */
    @Test
    public void thenCombine() {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            return 10;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 20;
        });

        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            return result1 + result2;
        });

        combinedFuture.thenAccept(result -> System.out.println("合并结果: " + result));
    }

    /***
     * 使用 thenCompose 进行依赖执行
     */
    @Test
    public void thenCompose() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 10;
        });

        CompletableFuture<Integer> finalFuture = future.thenCompose(result -> {
            return CompletableFuture.supplyAsync(() -> result * 2);
        });

        finalFuture.thenAccept(result -> System.out.println("最终结果: " + result));
    }

    /***
     * 使用 exceptionally 处理异常
     */
    @Test
    public void exceptionally() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("发生异常");
            return 10;
        });

        future.exceptionally(ex -> {
            System.out.println("异常: " + ex.getMessage());
            return 0; // 返回默认值
        }).thenAccept(result -> System.out.println("结果: " + result));
    }

    /***
     * 使用 handle 处理结果和异常
     */
    @Test
    public void handle() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            if (true) throw new RuntimeException("发生异常");
            return 10;
        });

        future.handle((result, ex) -> {
            if (ex != null) {
                System.out.println("异常: " + ex.getMessage());
                return 0; // 返回默认值
            }
            return result;
        }).thenAccept(result -> System.out.println("结果: " + result));
    }

    /***
     * 超时控制
     */
    @Test
    public void timeout() {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 42;
        }).orTimeout(2, TimeUnit.SECONDS); // 设置超时时间为2秒

        future.exceptionally(ex -> {
            System.out.println("异常: " + ex.getMessage());
            return null;
        }).thenAccept(result -> System.out.println("结果: " + result));
    }

}
