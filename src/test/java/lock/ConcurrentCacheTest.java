package lock;

import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.CountDownLatch;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 05.12.2021
 */
public class ConcurrentCacheTest {

    private ConcurrentCacheWithStampedLock cache;

    @Before
    public void init(){
        cache = Mockito.mock(ConcurrentCacheWithStampedLock.class);
    }


    @Test
    public void test1(){
        Mockito.when(cache.get("1")).thenAnswer(str -> 1);
        final CountDownLatch latch = new CountDownLatch(1);


    }

}
