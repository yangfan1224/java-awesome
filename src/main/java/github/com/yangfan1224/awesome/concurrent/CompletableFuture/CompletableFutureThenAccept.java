package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * thenAccept()
 * If We want to run some code after receiving some value from Future then we can use thenAccept()
 */
public class CompletableFutureThenAccept {
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



    public void notify(String str) {
        System.out.println(Thread.currentThread() + "Recived vlaue " + str);
        someStateVaribale.set(100);
        sleep(1);
    }


    public void completableFutureThenAccept() {
        CompletableFuture.supplyAsync(this::process)
                .thenAccept(this::notify)
                .join();
        System.out.println(Objects.equals(100,someStateVaribale.get()));
    }

    public static void main(String [] args) {
        new CompletableFutureThenAccept().completableFutureThenAccept();
    }
}
