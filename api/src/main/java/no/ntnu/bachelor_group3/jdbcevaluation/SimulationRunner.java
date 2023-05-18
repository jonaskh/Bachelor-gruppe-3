package no.ntnu.bachelor_group3.jdbcevaluation;

import no.ntnu.bachelor_group3.jdbcevaluation.Data.Customer;
import no.ntnu.bachelor_group3.jdbcevaluation.Data.Shipment;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.AddShipmentsRunnable;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.FindShipmentRunnable;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.TerminalShipmentsRunnable;
import no.ntnu.bachelor_group3.jdbcevaluation.Runnables.UpdateShipmentRunnable;
import org.apache.commons.lang3.time.StopWatch;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class SimulationRunner {
    private static List<String> evals = new CopyOnWriteArrayList<>();
    private DatabaseManager db;



    private ExecutorService executor1 = Executors.newFixedThreadPool(5);
    private ExecutorService executor2 = Executors.newFixedThreadPool(5);
    private ScheduledExecutorService findShipmentInCustomerService = Executors.newScheduledThreadPool(10);
    private ScheduledExecutorService findShipmentsInTerminalService = Executors.newScheduledThreadPool(2);

    private final ArrayBlockingQueue<Shipment> queue = new ArrayBlockingQueue<>(2000);


    public SimulationRunner(DatabaseManager db) {
        this.db = db;
    }

    public void simulate() throws SQLException {
            Customer sender = new Customer(0l, "Ålesund", "Jonas", "6008");
            Customer receiver = new Customer(0l, "Oslo", "Tarjei", "0021");
            db.saveCustomer(sender);
            db.saveCustomer(receiver);

            System.out.println("Starting simulation...");
            for (int i = 0; i < 2000; i++) {
                Shipment shipment = new Shipment(0L, sender, sender, receiver, 1.2);
                AddShipmentsRunnable shipmentsRunnable = new AddShipmentsRunnable(shipment, db);
                try {
                    queue.put(shipment);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                executor1.execute(shipmentsRunnable);
            }



            for (int k = 0; k < 100; k++) {
                findShipmentInCustomerService.scheduleAtFixedRate(new FindShipmentRunnable(db, db.getShipmentById(1l)), 0, 500, TimeUnit.MILLISECONDS);
            }

            for (int j = 0; j < 10; j++) {
                findShipmentsInTerminalService.scheduleAtFixedRate(new TerminalShipmentsRunnable(db), 0, 10000, TimeUnit.MILLISECONDS);
            }

            int i = 0;
            var size = queue.size();
            while (i < size) {
                try {
                    executor2.execute(new UpdateShipmentRunnable(queue.take(), db));
                    i++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        try {
                //stops the executors from accepting new tasks


                findShipmentsInTerminalService.shutdown();
                findShipmentInCustomerService.shutdown();
            executor1.shutdown();
            executor2.shutdown();


                executor1.awaitTermination(2, TimeUnit.MINUTES);
                System.out.println("Adding done");

                //stops the thread pools if no more tasks, an exception occurs or timeout.
                executor2.awaitTermination(2, TimeUnit.MINUTES);
                System.out.println("Update shipments done");

                findShipmentInCustomerService.awaitTermination(2, TimeUnit.MINUTES);
                findShipmentsInTerminalService.awaitTermination(2, TimeUnit.MINUTES);



            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Error in await termination");
            }

            db.commit();


            //evals.addAll(customerService.getCustomerEval());
            //evals.forEach(System.out::println);

            //System.out.println("shipments: " + sender.getShipments().size());



            //System.out.println("queue size: " + queue.size());
            //System.out.println("shipments: " + shipmentService.count());
            //System.out.println("parcels: " + parcelService.count());
            //System.out.println("checkpoints: " + checkpointService.count());

            //System.out.println("Number of shipments in terminal 14: " + terminalService.returnTerminalFromZip("6008").getShipmentNumber());
            //System.out.println("Number of checkpoints in terminal 14: " + terminalService.returnTerminalFromZip("0021").getCheckpointNumber());


        }


    public void realTimeConverter() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        long previousElapsedSeconds = -1;
        while (stopWatch.getTime() < 30000) { // 30 seconds in milliseconds

            long elapsedSeconds = stopWatch.getTime();
            stopWatch.split();
            if (elapsedSeconds != previousElapsedSeconds) {
                if (((elapsedSeconds / 1000) != (previousElapsedSeconds / 1000)) && calendar.get(Calendar.DAY_OF_MONTH) != currentDay) {
                    previousElapsedSeconds = stopWatch.getSplitTime();
                    currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int dayOfWeek = getDayOfWeek(calendar.getTime());
                    System.out.println("Today is " + daysOfWeek[dayOfWeek - 1]);
                } else {
                    previousElapsedSeconds = elapsedSeconds;
                }
            }
            calendar.setTime(new Date(stopWatch.getSplitTime() * 24 * 60 * 60 * 1000)); // Set the calendar time based on the elapsed time
            stopWatch.unsplit();

        }
        stopWatch.stop();

    }

    private static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}





