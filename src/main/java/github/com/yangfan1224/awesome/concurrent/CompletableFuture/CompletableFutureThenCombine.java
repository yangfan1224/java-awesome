package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * As name suggest thenCombine is used to merge results of two independent CompletableFuture.
 * Assume that for a person we get first name and last name by calling two different independent methods.
 * To get the Full name we want ot merge results of both the methods then we will use thenCombine.
 */
public class CompletableFutureThenCombine {
    public CompletableFuture<String> findFirstName() {

        return CompletableFuture.supplyAsync(() -> {
            sleep(1);
            System.out.println(Thread.currentThread() + " findFirstName");
            return "Niraj";
        });

    }

    public CompletableFuture<String> findLastName() {

        return CompletableFuture.supplyAsync(() -> {
            sleep(1);
            System.out.println(Thread.currentThread() + " findLastName");
            return "Sonawane";
        });
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void completableFutureThenCombine() {

        CompletableFuture<String> thenCombine = findFirstName().thenCombine(findLastName(), (firstName, lastname) -> {
            return firstName + lastname;
        });
        String fullName = thenCombine.join();
        System.out.println(Objects.equals("NirajSonawane", fullName));
    }

    public static void main(String [] args){
        new CompletableFutureThenCombine().completableFutureThenCombine();
    }

}
