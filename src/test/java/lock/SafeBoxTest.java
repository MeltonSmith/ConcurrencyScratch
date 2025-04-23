package lock;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

/**
 * @author Melton Smith
 * @since 28.01.2025
 */
public class SafeBoxTest {

    private SafeBox<Integer> safeBox;

    private static final Integer THREAD_NUMBERS = 2;

    @Before
    public void init(){
        safeBox = new SafeBox<>();
    }

    @Test
    public void testSafeBox() throws InterruptedException {
        Runnable set  = () ->  {
            try {
                safeBox.set(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable get  = () ->  {
            try {
                safeBox.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };


        var countDownLatch = new CountDownLatch(1);

//        Stream.iterate(0, i -> i + 1).limit(2)2

        Thread[] threads = Stream.of(set, get, set).map(Thread::new)
                .peek(Thread::run)
                .toArray(Thread[]::new);

        //start
        countDownLatch.countDown();

        for (Thread thread : threads) {
            thread.join();
        }

        safeBox.get();


    }
}
