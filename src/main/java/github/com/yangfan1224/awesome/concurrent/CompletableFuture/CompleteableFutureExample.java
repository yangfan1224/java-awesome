package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CompleteableFutureExample {
    private static final Logger logger = LoggerFactory.getLogger(CompleteableFutureExample.class);

    private  static AtomicInteger someStateVaribale = new AtomicInteger(1);

    public static void simpleComletedCompletableFuture() {
        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("Some Value");
        System.out.println(completableFuture.isDone());
        try {
            System.out.println(Objects.equals("Some Value", completableFuture.get()));
        } catch (ExecutionException | InterruptedException e) {
            logger.error("No Exception expected");
        }
    }

    public static void process() {
        System.out.println(Thread.currentThread() + " Process");
        someStateVaribale.set(100);
    }

    /**
     * Simple Asynchronous computation using runAsync
     */
    public static void completableFutureRunAsync() {
        System.out.println(Thread.currentThread() + " Process");
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> process());
        runAsync.join();
        System.out.println(Objects.equals(100, someStateVaribale.get()));
    }
    public static String processSomeData() {
        System.out.println(Thread.currentThread() + " Processing Some Data");
        return "Some Value";
    }

    /**
     * Simple Asynchronous computation using supplyAsync, return some value
     */
    public static void completableFutureSupplyAsync() {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(CompleteableFutureExample::processSomeData);
        try {
            System.out.println(Objects.equals("Some Value", supplyAsync.get())); //Blocking
        } catch (ExecutionException | InterruptedException e) {
            logger.error("No Exception expected");
        }
    }

    /**
     * CompletableFuture with Custom Executor
     */
    public static void completableFutureSupplyAsyncWithExecutor() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(CompleteableFutureExample::processSomeData,newFixedThreadPool);
        try {
            System.out.println(Objects.equals("Some Value", supplyAsync.get()));
        } catch (ExecutionException | InterruptedException e) {
            logger.error("No Exception expected");
        }
        newFixedThreadPool.shutdown();
    }

    public static void main(String []args) {
        simpleComletedCompletableFuture();
        //异步执行一个任务
        completableFutureRunAsync();
        //异步执行任务，并返回值
        completableFutureSupplyAsync();
        //自定义线程池，异步执行任务
        completableFutureSupplyAsyncWithExecutor();
        if (logger.isDebugEnabled()) {
            logger.debug("DebugEnabled()");
        }
    }

}
