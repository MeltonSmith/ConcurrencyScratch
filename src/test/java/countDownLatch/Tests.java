package countDownLatch;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 15.10.2021
 * NOTE
 */
public class Tests {

    @Test
    public void startAtTheSameTime() throws InterruptedException {
        List<String> outputCollector = Collections.synchronizedList(new ArrayList<>());

        CountDownLatch readyThreadLatch = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);

        List<Thread> workers = Stream
                .generate(()-> new Thread(new Worker(outputCollector, readyThreadLatch,
                        completedThreadCounter, callingThreadBlocker)))
                .limit(5)
                .collect(Collectors.toList());

        workers.forEach(Thread::start);
        System.out.println("Waiting for the workers to be ready...");
        readyThreadLatch.await(3L, TimeUnit.SECONDS);
        outputCollector.add("Workers are ready");
        callingThreadBlocker.countDown();

        completedThreadCounter.await();
        outputCollector.add("Workers are done");

        assertThat(outputCollector)
                .containsExactly(
                        "Workers are ready",
                        "Something",
                        "Something",
                        "Something",
                        "Something",
                        "Something",
                        "Workers are done"
                );

    }
}
