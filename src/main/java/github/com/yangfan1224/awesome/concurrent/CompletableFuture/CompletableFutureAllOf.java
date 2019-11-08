package github.com.yangfan1224.awesome.concurrent.CompletableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * In Many scenario we want to run run multiple task in parallel and want to do some processing after all of them are complete.
 * Assume we want to find firstName of five different users and combine the results.
 * The CompletableFuture.allOf static method allows to wait for completion of all of the Futures.
 * The allOf method has limitation that it does not return the combined results of all Futures.
 * We you have to manually combine the results from Futures.
 */
public class CompletableFutureAllOf {
    public CompletableFuture<String> findSomeValue() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1);
            return "Niraj";
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

    public void completableFutureAllof() {
        List<CompletableFuture<String>> list = new ArrayList<>();

        IntStream.range(0, 5).forEach(num -> {
            list.add(findSomeValue());
        });

        CompletableFuture<Void> allfuture = CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));//Created All of object
        CompletableFuture<List<String>> allFutureList = allfuture.thenApply(val -> {
            System.out.println("Creating List");
            return list.stream().map(f -> f.join()).collect(Collectors.toList());
        });

        System.out.println("List creadted , now joing the vlaues");
        CompletableFuture<String> futureHavingAllValues = allFutureList.thenApply(fn -> {
            System.out.println("I am here");
            return fn.stream().collect(Collectors.joining());
        });
        String concatenateString = futureHavingAllValues.join();
        System.out.println(Objects.equals("NirajNirajNirajNirajNiraj", concatenateString));
    }

    public static void main(String []args) {
        new CompletableFutureAllOf().completableFutureAllof();
    }
}
