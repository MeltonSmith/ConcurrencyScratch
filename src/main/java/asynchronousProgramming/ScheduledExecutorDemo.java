package asynchronousProgramming;

import com.google.common.util.concurrent.Futures;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 24.11.2021
 */
public class ScheduledExecutorDemo {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> printNumberSquare(2), 10, 10, TimeUnit.SECONDS);


        while (!scheduledFuture.isDone()){

        }

        System.out.println("end");
    }

    private static void printNumberSquare(int number){
        System.out.println(number * number);
    }
}
