//package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.Lifecycle;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
//public class ConcurrencyMultiTest {
//
//
//    private static class ExampleTask implements Runnable {
//
//        private String name;
//        public ExampleTask(String name) {
//            this.name = name;
//        }
//
//        @Override
//        public void run() {
//            System.out.println("Working on thread: " + Thread.currentThread().getId() + "...");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                System.out.println("F");
//                Thread.currentThread().interrupt();
//            }
//            System.out.println("Done working on thread: " + Thread.currentThread().getId());
//            // Do some work after the loop if necessary
//        }
//    }
//
//    @Test
//    @DisplayName("Create a fixed thread pool and hand it some example tasks to ensure paralellism")
//    public void simpleThreadPoolTest() throws InterruptedException {
//
//        ExecutorService executor = Executors.newFixedThreadPool(5);
//        List<ExampleTask> tasks = new ArrayList<>();
//        for (int j = 0; j < 25; j++) {
//            tasks.add(new ExampleTask("Task " + j));
//        }
//
//        tasks.forEach(executor::submit);
//
//        executor.shutdown();
//
//        try {
//            executor.awaitTermination(1, TimeUnit.HOURS);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
