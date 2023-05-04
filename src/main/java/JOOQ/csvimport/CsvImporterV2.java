//package JOOQ.csvimport;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import com.opencsv.exceptions.CsvException;
//import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
//import org.jooq.*;
//
//import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.*;
//import static org.jooq.impl.DSL.rowNumber;
//
//import org.jooq.impl.DSL;
//
//import com.opencsv.CSVReader;
//
//import static org.jooq.impl.DSL.field;
//import static org.jooq.impl.DSL.table;
//
//public class CsvImporterV2 {
//
//    private final String filename;
//    private final String url;
//    private final String username;
//    private final String password;
//
//
//    int terminalCounter = 1;
//    int customerCounter = 1;
//
//    public CsvImporterV2(String filename, String url, String username, String password) {
//        this.filename = filename;
//        this.url = url;
//        this.username = username;
//        this.password = password;
//    }
//
//
//    public void importData() {
//        try (Connection conn = DriverManager.getConnection(url, username, password)) {
//            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
//            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
//                List<String[]> rows = reader.readAll();
//                int batchSize = 1000;
//                for (int i = 0; i < rows.size(); i += batchSize) {
//                    List<String[]> batchRows = rows.subList(i, Math.min(i + batchSize, rows.size()));
//                    dsl.batch(batchRows.stream().map(row -> {
//                        return dsl.insertInto(table("valid_postal_codes"))
//                                .set(field("postal_code"), row[0])
//                                .set(field("county"), row[4])
//                                .set(field("municipality"), row[3])
//                                .onConflict(field("postal_code")).doNothing(); // add ON CONFLICT clause
//                    }).collect(Collectors.toList())).execute();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (CsvException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    //Imports the customers from a .csv with given batch size, has a onConflict clause that changes nothing if a customer with the given customer_id already exisits.
//
//    public void importCustomers() {
//        try (Connection conn = DriverManager.getConnection(url, username, password)) {
//            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
//            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
//                List<String[]> rows = reader.readAll();
//                int batchSize = 1000;
//                for (int i = 0; i < rows.size(); i += batchSize) {
//                    List<String[]> batchRows = rows.subList(i, Math.min(i + batchSize, rows.size()));
//                    dsl.batch(batchRows.stream().map(row -> {
//                        return dsl.insertInto(table("customer"))
//                                .set(field("customer_id"), customerCounter++)
//                                .set(field("name"), row[0])
//                                .set(field("address"), row[1])
//                                .set(field("zip_code"), row[2])
//                                .onConflict(CUSTOMER.CUSTOMER_ID)
//                                .doNothing();
//
//                        // add ON CONFLICT clause
//                    }).collect(Collectors.toList())).execute();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (CsvException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void insertTerminals() {
//        try (Connection conn = DriverManager.getConnection(url, username, password)) {
//            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
//            String[] terminalAddresses = {"OSLO", "AKERSHUS", "ØSTFOLD", "HEDMARK", "OPPLAND", "BUSKERUD", "VESTFOLD", "TELEMARK",
//                    "ROGALAND", "VEST-AGDER", "AUST-AGDER", "HORDALAND", "SOGN OG FJORDANE", "MØRE OG ROMSDAL", "SØR-TRØNDELAG", "NORD-TRØNDELAG",
//                    "NORDLAND", "TROMS", "FINNMARK"};
//            List<Query> queries = Arrays.stream(terminalAddresses)
//                    .flatMap(a -> {
//                        // Insert into terminal table and get the primary key
//                        int terminalId = Objects.requireNonNull(dsl.insertInto(TERMINAL)
//                                        .set(TERMINAL.ADDRESS, a)
//                                        .set(TERMINAL.TERMINAL_ID, terminalCounter++)
//                                        .onConflict(TERMINAL.TERMINAL_ID)
//                                        .doNothing()
//                                        .returning(TERMINAL.TERMINAL_ID)
//                                        .fetchOne())
//                                .getTerminalId();
//                        // Get the primary keys of the valid_postal_codes rows with county = a
//                        Result<Record1<String>> postalCodeIds = dsl.select(VALID_POSTAL_CODES.POSTAL_CODE)
//                                .from(VALID_POSTAL_CODES)
//                                .where(VALID_POSTAL_CODES.COUNTY.eq(a))
//                                .fetch();
//                        // Insert into terminal_id table with the primary keys of terminal and valid_postal_codes rows
//                        return postalCodeIds.stream()
//                                .map(postalCodeId -> dsl.insertInto(TERMINAL_ID)
//                                        .values(terminalId, postalCodeId.get(VALID_POSTAL_CODES.POSTAL_CODE)));
//                    })
//                    .collect(Collectors.toList());
//            dsl.batch(queries).execute();
//        } catch (SQLException e) {
//            // Handle the exception here
//            System.err.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//
//
//
//
//
//
//
//
//
//    public static void main(String[] args) {
//
//        LocalDateTime start = LocalDateTime.now();
//
//        CsvImporterV2 importer = new CsvImporterV2("Postnummerregister.csv",
//               "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
//        importer.importData();
//        importer.insertTerminals();
//        //importer.importCustomers();
//
//
//        LocalDateTime after = LocalDateTime.now();
//        long timeTaken = ChronoUnit.MILLIS.between(start,after);
//        System.out.println("Time taken in READCSV method:  " + timeTaken + " milliseconds");
//
//
//    }
//}
