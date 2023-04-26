package no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.JOOQTests;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.CUSTOMER;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class SimulationJOOQTest {

    private DSLContext dslContext;

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String filename = "customers.csv";
    private final String username = "postgres";
    private final String password = "postgres";

    int customerCounter = 1;

    public SimulationJOOQTest() throws FileNotFoundException {
    }

    /**
     * Tries to import customers from the customer.csv file while using multithreading
     * The method adds around 49k costumers when the customer.csv file contains 50k, why? No idea
     * @throws SQLException
     */
    @Test
    void importingCustomersMultithreadingTest() throws SQLException {

        ExecutorService executor = Executors.newFixedThreadPool(10); // create a thread pool with 4 threads

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                List<String[]> rows = reader.readAll();
                int batchSize = 1;
                List<Callable<Void>> tasks = new ArrayList<>();

                for (int i = 0; i < rows.size(); i += batchSize) {
                    List<String[]> batchRows = rows.subList(i, Math.min(i + batchSize, rows.size()));
                    Callable<Void> task = () -> {
                        dsl.batch(batchRows.stream().map(row -> {
                            return dsl.insertInto(table("customer"))
                                    .set(field("customer_id"), customerCounter++)
                                    .set(field("name"), row[0])
                                    .set(field("address"), row[1])
                                    .set(field("zip_code"), row[2])
                                    .onConflict(CUSTOMER.CUSTOMER_ID)
                                    .doNothing();
                        }).collect(Collectors.toList())).execute();
                        return null;
                    };
                    tasks.add(task);
                }

                //Does nothing
                /*// Add a separate task for the last batch, if it's smaller than the batch size
                List<String[]> lastBatch = rows.subList((rows.size() / batchSize) * batchSize, rows.size());
                if (!lastBatch.isEmpty()) {
                    Callable<Void> task = () -> {
                        dsl.batch(lastBatch.stream().map(row -> {
                            return dsl.insertInto(table("customer"))
                                    .set(field("customer_id"), customerCounter++)
                                    .set(field("name"), row[0])
                                    .set(field("address"), row[1])
                                    .set(field("zip_code"), row[2])
                                    .onConflict(CUSTOMER.CUSTOMER_ID)
                                    .doNothing();
                            // add ON CONFLICT clause
                        }).collect(Collectors.toList())).execute();
                        return null;
                    };
                    tasks.add(task);
                }
*/

                List<Future<Void>> results = executor.invokeAll(tasks);

                for (Future<Void> result : results) {
                    result.get();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException | InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        executor.shutdown();

    }



    CSVReader csvReader = new CSVReader(new FileReader(filename));

    ExecutorService executorService = Executors.newFixedThreadPool(4);


    public void importMultipleCustomersTest() throws CsvValidationException, IOException, InterruptedException, ExecutionException {
        List<Callable<String[]>> tasks = new ArrayList<>();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            final String[] finalLine = line;
            tasks.add(() -> finalLine);
        }


        List<Future<String[]>> results = executorService.invokeAll(tasks);

        for (Future<String[]> future : results) {
            String[] line2 = future.get();
            System.out.print(line2);
        }
    }





}
