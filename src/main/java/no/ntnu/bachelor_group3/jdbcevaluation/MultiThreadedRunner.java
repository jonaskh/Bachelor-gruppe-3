package no.ntnu.bachelor_group3.jdbcevaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedRunner {
    public static void main(String[] args) {
        int x = 5; // Number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(x);

        List<Runnable> tasks = new ArrayList<>();

        for (Runnable task : tasks) {
            executorService.submit(task);
        }

        executorService.shutdown();
    }
}
