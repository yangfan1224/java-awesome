package github.com.yangfan1224.awesome.concurrent.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorExample {
    public static void main(String [] args) {
        ExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("ThreadID: "+ Thread.currentThread());
            }
        });
    }
}
