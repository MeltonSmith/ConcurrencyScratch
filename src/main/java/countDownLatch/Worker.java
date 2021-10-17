package countDownLatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 14.10.2021
 */
public class Worker implements Runnable{

    private List<String> collectorList;
    private CountDownLatch readyThreadLatch;
    private CountDownLatch completedThreadLatch;
    private CountDownLatch callingThreadLocker;

    public Worker(List<String> collectorList, CountDownLatch readyThreadLatch,
                  CountDownLatch completedThreadLatch, CountDownLatch callingThreadLocker) {
        this.collectorList = collectorList;
        this.readyThreadLatch = readyThreadLatch;
        this.completedThreadLatch = completedThreadLatch;
        this.callingThreadLocker = callingThreadLocker;
    }

    @Override
    public void run() {
        readyThreadLatch.countDown();
        System.out.println("Counted down");
        try{
            callingThreadLocker.await();
            collectorList.add("Something");
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        finally {
            completedThreadLatch.countDown();
        }

    }
}
