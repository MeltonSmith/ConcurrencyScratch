package mutex;

import com.google.common.util.concurrent.Monitor;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 22.11.2021
 */
public class SequenceGeneratorUsingMonitor extends SequenceGenerator{

    private Monitor mutex = new Monitor();

    @Override
    public int getNextSequence() {
        mutex.enter();
        try {
            return super.getNextSequence();
        } finally {
            mutex.leave();
        }
    }
}
