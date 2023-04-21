package JOOQ.csvimport;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvException;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.opencsv.CSVReader;

import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.TERMINAL_ID;
import static no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.Tables.VALID_POSTAL_CODES;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class CsvImporterV2 {

    private final String filename;
    private final String url;
    private final String username;
    private final String password;

    public CsvImporterV2(String filename, String url, String username, String password) {
        this.filename = filename;
        this.url = url;
        this.username = username;
        this.password = password;
    }


    public void importData() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            try (CSVReader reader = new CSVReader(new FileReader(filename))) {
                List<String[]> rows = reader.readAll();
                int batchSize = 1000;
                for (int i = 0; i < rows.size(); i += batchSize) {
                    List<String[]> batchRows = rows.subList(i, Math.min(i + batchSize, rows.size()));
                    dsl.batch(batchRows.stream().map(row -> {
                        return dsl.insertInto(table("valid_postal_codes"))
                                .set(field("postal_code"), row[0])
                                .set(field("county"), row[4])
                                .set(field("municipality"), row[3])
                                .onConflict(field("postal_code")).doNothing(); // add ON CONFLICT clause
                    }).collect(Collectors.toList())).execute();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void insertTerminals() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            String[] terminalAddresses = {"OSLO", "AKERSHUS", "ØSTFOLD", "HEDMARK", "OPPLAND", "BUSKERUD", "VESTFOLD", "TELEMARK",
                    "ROGALAND", "VEST-AGDER", "AUST-AGDER", "HORDALAND", "SOGN OG FJORDANE", "MØRE OG ROMSDAL", "SØR-TRØNDELAG", "NORD-TRØNDELAG",
                    "NORDLAND", "TROMS", "FINNMARK"};
            List<Query> queries = Arrays.stream(terminalAddresses)
                    .map(a -> {
                        // Insert into terminal table and get the primary key
                        int terminalId = Objects.requireNonNull(dsl.insertInto(TERMINAL).set(TERMINAL.ADDRESS, a).returning(TERMINAL.TERMINAL_ID).fetchOne()).getTerminalId();
                        // Get the primary key of the valid_postal_codes row with postal_code = a
                        var postalCodeId = dsl.select(VALID_POSTAL_CODES.POSTAL_CODE).from(VALID_POSTAL_CODES).where(VALID_POSTAL_CODES.POSTAL_CODE.eq(a)).fetchOne(VALID_POSTAL_CODES.POSTAL_CODE);
                        // Insert into terminal_id table with the primary keys of terminal and valid_postal_codes rows
                        return dsl.insertInto(TERMINAL_ID).set(TERMINAL_ID.TERMINAL_ID_TERMINAL_ID, terminalId).set(VALID_POSTAL_CODES.POSTAL_CODE, postalCodeId);
                    })
                    .collect(Collectors.toList());
            dsl.batch(queries).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }







    public static void main(String[] args) {

        LocalDateTime start = LocalDateTime.now();

        CsvImporterV2 importer = new CsvImporterV2("Postnummerregister.csv",
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        importer.importData();
        importer.insertTerminals();

        LocalDateTime after = LocalDateTime.now();
        long timeTaken = ChronoUnit.MILLIS.between(start,after);
        System.out.println("Time taken in READCSV method:  " + timeTaken + " milliseconds");


    }
}
