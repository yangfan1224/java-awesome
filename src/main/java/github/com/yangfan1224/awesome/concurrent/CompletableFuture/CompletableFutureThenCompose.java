package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Let’s Say we want to first find Account Number and then calculate Balance for that account and after calculations we want to send notifications.
 * Now All these task are Dependent and methods are returning CompletableFuture , Then We need to use thenCompose Method.
 * This is similar to flatMap in case of Streams.
 */
public class CompletableFutureThenCompose {

    public CompletableFuture<Integer> findAccountNumber() {
        sleep(1);
        System.out.println(Thread.currentThread() + " findAccountNumber");
        return CompletableFuture.completedFuture(10);
    }

    public CompletableFuture<Integer> calculateBalance(int accountNumber) {
        System.out.println(Thread.currentThread() + " calculateBalance");
        sleep(1);
        return CompletableFuture.completedFuture(accountNumber * accountNumber);
    }

    public CompletableFuture<Integer> notifyBalance(Integer balance) {
        System.out.println(Thread.currentThread() + "Sending Notification");
        sleep(1);
        return CompletableFuture.completedFuture(balance);

    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void completableFutureThenCompose()
    {

        Integer join = findAccountNumber()
                .thenComposeAsync(this::calculateBalance)
                .thenCompose(this::notifyBalance)
                .join();
        System.out.println(Objects.equals(new Integer(100), join));
    }

    public static void main(String [] args) {
        new CompletableFutureThenCompose().completableFutureThenCompose();
    }
}
