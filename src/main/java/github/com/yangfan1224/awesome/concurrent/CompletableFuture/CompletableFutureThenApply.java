package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * thenApply()
 * If We want to run some code after receiving value from Future and then want to return some value for this we can use thenAccept()
 */
public class CompletableFutureThenApply {

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



    public Integer notify(String str) {
        System.out.println(Thread.currentThread() + "Recived vlaue " + str);
        sleep(1);
        return 1;
    }

    public void completableFutureThenApply() {
        Integer notificationId = CompletableFuture.supplyAsync(this::process)
                .thenApply(this::notify)
                .join();
        System.out.println(Objects.equals(new Integer(1),notificationId));
    }

    public static void main(String []args) {
        new CompletableFutureThenApply().completableFutureThenApply();
    }
}
