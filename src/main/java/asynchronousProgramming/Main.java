package asynchronousProgramming;

import java.util.concurrent.*;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 24.11.2021
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> square(4));

        while (!future.isDone()){
            System.out.println("Is not finished yet...");
        }

        System.out.println("Done " + future.get());
    }

    private static int square(int number){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number * number;
    }
}
