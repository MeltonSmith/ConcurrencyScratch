package completableFutureDemo;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 27.12.2021
 */
public class Main {

    public Future<String> calculateAsync() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();


        ForkJoinPool.commonPool().submit(() -> {
//                    Thread.sleep(5000);
                    completableFuture.complete("Value");
                }

        );

        return completableFuture;
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        Future<String> stringFuture = calculateAsync();

        String s = stringFuture.get();

//        CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("addb");

        assertEquals("Value", s);
    }

    @Test
    @SneakyThrows
    public void test1(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Completed");
        });

        Void unused = future.get();
        System.out.println(unused);

    }

    @Test
    @SneakyThrows
    public void thenDoSomethingTest(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Completed");
        });

        CompletableFuture<String> completableFuture = future.thenApply(s -> s + " abc");

        assertEquals("null abc", completableFuture.get());

        CompletableFuture<Void> future1 = future.thenAccept(s -> System.out.println(s + " AAAA"));

        future.thenRun(() -> System.out.println("Then run"));

    }

    @Test
    @SneakyThrows
    public void thenDoSomethingTest1(){
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));


        assertEquals("Hello World", completableFuture.get());


//        combining two futures independently
        CompletableFuture<String> completableFuture1
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> " World"), (s1, s2) -> s1 + s2);

        assertEquals("Hello World", completableFuture1.get());

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"),
                        (s1, s2) -> System.out.println(s1 + s2));
    }

}
