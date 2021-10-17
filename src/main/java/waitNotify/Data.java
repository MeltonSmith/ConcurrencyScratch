package waitNotify;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 18.10.2021
 */
@Slf4j
public class Data {
    private String packet;
    // True if receiver should wait
    // False if sender should wait
    private boolean transfer = true;

    public synchronized void send(String packet){
        while (!transfer){
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                log.error("Thread interrupted", e);

            }
        }
        System.out.println("Sender is unlocked");
        transfer = false;
        this.packet = packet;
        notifyAll();
    }

    public synchronized String receive(){
        while (transfer){
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                log.error("Thread interrupted", e);
            }
        }
        transfer = true;
        notifyAll();
        System.out.println("notified from receiver");
        return packet;
    }
}
