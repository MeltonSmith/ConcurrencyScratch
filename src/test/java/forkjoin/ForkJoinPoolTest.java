package forkjoin;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 29.11.2021
 */
public class ForkJoinPoolTest {

    @Test
    public void testForJoin() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        var collect = new Random()
                .ints(1, 1000)
                .limit(1000)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinTask<Integer> submit = forkJoinPool.submit(new CustomRecursiveTask(collect));

        System.out.println("processing");
        while (!submit.isDone()){

        }

        System.out.println(submit.get());


    }
}