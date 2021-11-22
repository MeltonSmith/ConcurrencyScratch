package mutex;

import java.util.concurrent.Semaphore;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 22.11.2021
 */
public class SequenceGeneratorUsingSemaphore extends SequenceGenerator{
    private Semaphore mutex = new Semaphore(1);

    @Override
    public int getNextSequence() {
        try {
            mutex.acquire();
            return super.getNextSequence();
        } catch (InterruptedException e) {
            return  0;
        } finally {
            mutex.release();
        }
    }
}
