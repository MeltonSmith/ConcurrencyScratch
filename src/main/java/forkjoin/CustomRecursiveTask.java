package forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 29.11.2021
 */
public class CustomRecursiveTask extends RecursiveTask<Integer> {
    private final List<Integer> integers;

    private static final int THRESHOLD = 20;

    public CustomRecursiveTask(List<Integer> integers) {
        this.integers = integers;
    }

    @Override
    protected Integer compute() {
        if (integers.size() > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(integers);
        }
    }

    private Collection<CustomRecursiveTask> createSubtasks() {
        System.out.println("Dividing");
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(integers.subList(0, integers.size()/2)));
        dividedTasks.add(new CustomRecursiveTask(integers.subList(integers.size()/2+1, integers.size()-1)));
        return dividedTasks;
    }

    private Integer processing(List<Integer> integersInput) {
        return integersInput.stream()
//                .filter(a -> a > 10 && a < 27)
                .map(a -> a * 10)
                .reduce(0, Integer::sum);
    }
}
