package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExceptionHandling {

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
        if(true)
        {
            CompletableFuture<String> future = new CompletableFuture<>();
            throw new RuntimeException("Invalid Balance Exception");
        }
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

    public void completableFutureExceptionally()
    {
        CompletableFuture<Integer> thenApply = CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .exceptionally(ex -> {
                    System.out.println("Got Some Exception "+ex.getMessage());
                    System.out.println("Returning some default value");
                    return 0;
                });
        Integer join = thenApply.join();
        System.out.println(Objects.equals(new Integer(0), join));
    }

    public void completableFutureHandel()
    {
        System.out.println("completableFutureHandel");
        CompletableFuture<Integer> thenApply = CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .handle((ok, ex) -> {
                    System.out.println("Code That we want to run in finally ");
                    if (ok != null) {
                        System.out.println("No Exception !!");

                    } else {

                        System.out.println("Got Exception " + ex.getMessage());
                        return -1;
                    }
                    return ok;
                });
        Integer join = thenApply.join();
        System.out.println(Objects.equals(new Integer(100), join));

    }

    public void completableFutureWhenComplete()
    {
        System.out.println("completableFutureHandel");
        CompletableFuture<Integer>  thenApply = CompletableFuture.supplyAsync(this::findAccountNumber)
                .thenApply(this::calculateBalance)
                .thenApply(this::notifyBalance)
                .whenComplete((i,t)->System.out.println("finally action, balance = " + i));

        Integer join = thenApply.join();
        System.out.println(Objects.equals(new Integer(100), join));

    }

    public static void main(String [] args) {
       CompletableFutureExceptionHandling handling= new CompletableFutureExceptionHandling();
       handling.completableFutureWhenComplete();
       //handling.completableFutureExceptionally();
       //handling.completableFutureHandel();
    }
}
