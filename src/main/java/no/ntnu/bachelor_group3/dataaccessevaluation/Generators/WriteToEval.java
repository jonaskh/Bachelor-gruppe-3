package no.ntnu.bachelor_group3.dataaccessevaluation.Generators;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToEval {

    public void write(String[] string) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("eval.csv", true));
            writer.writeNext(string);
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not print");
            throw new RuntimeException(e);
        }
    }
}
