package waitNotify;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 18.10.2021
 */
public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data));
        Thread receiver = new Thread(new Receiver(data));

        sender.start();
        receiver.start();
    }
}
