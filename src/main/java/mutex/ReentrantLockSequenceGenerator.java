package mutex;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 22.11.2021
 */
public class ReentrantLockSequenceGenerator extends SequenceGenerator{
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public int getNextSequence() {
        try {
            lock.lock();
            return super.getNextSequence();
        } finally {
            lock.unlock();
        }
    }
}
