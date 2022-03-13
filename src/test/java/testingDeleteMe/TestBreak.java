package testingDeleteMe;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by: mSmith
 * Date: 13.03.2022
 */
public class TestBreak {


    static ExecutorService executor = Executors.newCachedThreadPool();

    Runnable sendBufferTask = () -> {
        try {
            System.out.println("Start task in a executor");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    @Test
    public void iShouldNotStop() throws InterruptedException {
        Future<?> submit = executor.submit(sendBufferTask);

        while(!submit.isDone()){
            Thread.sleep(1000);
            System.out.println("Still is in progress");
        }


        System.out.println("End");
    }

    @Test
    public void iShouldWait() throws InterruptedException {

        Void nothing = CompletableFuture
                .runAsync(sendBufferTask, executor)
                .join();


        System.out.println("End");
    }

}
