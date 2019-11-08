package github.com.yangfan1224.awesome.concurrent.executor;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yangfan
 */
public class ScheduledExecutorExample {
    public static void main(String [] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new CustomThreadFactory("ScheduledExecutorExample"),new CustomRejectHandler());
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("to sleep");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("exit Runnable.");
            }
        }, 1, TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("to start1 " + new Date());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("exit Runnable.");
            }
        }, 1, 2,TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("to start2 " + new Date());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("exit Runnable.");
            }
        }, 1, 2,TimeUnit.SECONDS);
    }
}
