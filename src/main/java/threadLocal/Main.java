package threadLocal;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 17.10.2021
 */
public class Main {




    public static void main(String[] args) {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        ThreadLocal.withInitial(() -> 4);
        threadLocal.set(1);
        Integer integer = threadLocal.get();
        System.out.println(integer);
    }
}
