package threadLocal;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 17.10.2021
 */
public class ThreadLocalWithUserContext implements Runnable{

    private static ThreadLocal<Context> userContext = new ThreadLocal<>();
    private Integer userId;

    public ThreadLocalWithUserContext(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        String userName = String.format("Name of the userId - %d", userId);
        userContext.set(new Context(userName));
        System.out.println("thread context for given userId: "
                + userId + " is: " + userContext.get());
    }
}
