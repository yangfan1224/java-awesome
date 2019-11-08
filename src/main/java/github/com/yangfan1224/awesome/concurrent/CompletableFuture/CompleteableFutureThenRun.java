package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * thenRun()
 * If We want to run some code after completion of the Future and dont want to return any value for this we can use thenRun()
 */
public class CompleteableFutureThenRun {
    public AtomicInteger someStateVaribale = new AtomicInteger(1);

    public String process() {
        System.out.println(Thread.currentThread() + " Process Method");
        sleep(1);
        return "Some Value";
    }

    private void sleep(Integer i)
    {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public void notifyMe() {
        System.out.println(Thread.currentThread());
        sleep(1);
        someStateVaribale.set(100);
    }

    public void completableFutureThenApply() {
        CompletableFuture.supplyAsync(this::process)
                .thenRun(this::notifyMe)
                .join();
        System.out.println(Objects.equals(100, someStateVaribale.get()));
    }

    public static void main(String [] args) {
        new CompleteableFutureThenRun().completableFutureThenApply();
    }

}
