package executorservicedemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 24.11.2021
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(() -> printNumberSquare(4));
        executorService.submit(() -> printNumberSquare(4));
        executorService.submit(() -> printNumberSquare(4));

//        executorService.shutdown();
        executorService.shutdownNow();
//        executorService.awaitTermination()
//        executorService.awaitTermination(2, TimeUnit.SECONDS);
//        executorService.submit(() -> System.out.println("ABCD"));



        System.out.println("END");
    }

    private static void printNumberSquare(int number){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(number * number);
    }
}
