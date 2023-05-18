package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.SmallTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluationMethodsTests {

    private final int threads = 5;

    private static List<String> timestamps = new ArrayList<>();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(threads);
    private final ExecutorService executor = Executors.newFixedThreadPool(threads);


    //used for testing evaluation of timestamp
    public static class testClass {
        public String timeStampCheck() {
            var current = System.currentTimeMillis();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return System.currentTimeMillis() - current + ", create shipment";
        }
    }

    @Test
    @DisplayName("When scheduled service performs")
    public void service() {

        for (int k = 0; k < 5; k++) {
            int i = k;
            executorService.scheduleWithFixedDelay(() -> {
                System.out.println(i);
            }, 0, 10000, TimeUnit.MILLISECONDS);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int k = 0; k < 50; k++) {
            int i = k;
            executorService.execute(() -> System.out.println(i));
        }



        executorService.shutdown();
        System.out.println("Hello");

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("When scheduled service performs")
    public void serviceTwo() {

        for (int k = 0; k < 50; k++) {
            int i = k;
            executorService.execute(() -> System.out.println(i));
        }

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        executorService.shutdown();

        try {
            executorService.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    @DisplayName("Check one way to run the evaluation, each method returns a string with timestamp " +
            "and type of operation to a static list")
    public void addToList() {
        testClass test = new testClass();
        for (int i = 0; i < 10; i++) {
            timestamps.add(test.timeStampCheck());
        }
        timestamps.forEach(System.out::println);
        assertEquals(10,timestamps.size());
    }
}
