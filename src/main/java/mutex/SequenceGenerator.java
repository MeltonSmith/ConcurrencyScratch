package mutex;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 22.11.2021
 */
public class SequenceGenerator {
    private int currentValue = 0;

    public int getNextSequence() {
        currentValue = currentValue + 1;
        return currentValue;
    }
}
