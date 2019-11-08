package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureMultipleThenAccept {
    public int findAccountNumber() {
        System.out.println(Thread.currentThread() + " findAccountNumber");
        sleep(1);
        return 10;
    }

    public int calculateBalance(int accountNumber) {
        System.out.println(Thread.currentThread() + " calculateBalance");
        sleep(1);
        return accountNumber * accountNumber;
    }

    public Integer notifyBalance(Integer balance) {
        System.out.println(Thread.currentThread() + "Sending Notification");
        sleep(1);
        return balance;
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void notify(String str) {
        sleep(1);
        System.out.println(Thread.currentThread() + "Recived vlaue " + str);
    }

    public void notifyByEmail(int i) {
        sleep(1);
        System.out.println(Thread.currentThread() + "Recived vlaue : " + i );
    }

    public void comletableFutureMultipleThenApply() {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance);
        Integer balance = completableFuture.join();
        System.out.println(Objects.equals(Integer.valueOf(balance), Integer.valueOf(100)));

    }

    public void completableFutureThenApplyAccept() {
        CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .thenAccept((i)->notifyByEmail(i)).join();
    }

    public static void main(String [] args) {
        CompletableFutureMultipleThenAccept completableFutureMultipleThenAccept = new CompletableFutureMultipleThenAccept();
        completableFutureMultipleThenAccept.completableFutureThenApplyAccept();
        completableFutureMultipleThenAccept.comletableFutureMultipleThenApply();
    }
}
