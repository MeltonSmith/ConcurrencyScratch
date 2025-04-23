package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Melton Smith
 * @since 28.01.2025
 */
public class SafeBox<V> {
    private V value;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition valuePresent = lock.newCondition();
    private final Condition valueAbsent = lock.newCondition();

    public V get() throws InterruptedException {
        lock.lock();
        try {
            while (value == null) {
                valuePresent.await();
            }
            V result = value;
            value = null;
            valueAbsent.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }

    public void set(V newValue) throws InterruptedException {
        lock.lock();
        try {
            while (value != null) {
                valueAbsent.await();
            }
            value = newValue;
            valuePresent.signal();
        } finally {
            lock.unlock();
        }
    }
}
