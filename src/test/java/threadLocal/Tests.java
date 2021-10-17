package threadLocal;

import org.junit.Test;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 17.10.2021
 */
public class Tests {

    @Test
    public void testWith2Thread() throws InterruptedException {
        ThreadLocalWithUserContext firstUser
                = new ThreadLocalWithUserContext(1);
        ThreadLocalWithUserContext secondUser
                = new ThreadLocalWithUserContext(2);
        Thread firstThread = new Thread(firstUser);
        Thread secondThread = new Thread(secondUser);

        firstThread.start();
        secondThread.start();

        firstThread.join();
        secondThread.join();


    }
}
