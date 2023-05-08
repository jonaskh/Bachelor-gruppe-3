//package no.ntnu.bachelor_group3.dataaccessevaluation.Generators;
//
//import com.github.javafaker.Faker;
//import com.opencsv.CSVReader;
//import com.opencsv.CSVWriter;
//import com.opencsv.exceptions.CsvValidationException;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import java.util.Random;
//
///**
// * Writes 100 000 customers using java faker to a csv file with name, address and zip code
// */
//public class CustomerGenerator {
//    private static final Faker faker = new Faker(new Locale("nb-NO"));
//
//    public List<String> createZips() {
//        String zipFile = "Postnummerregister.csv";
//        List<String> zipCodes = null;
//        try {
//            CSVReader reader = new CSVReader(new FileReader(zipFile));
//
//            zipCodes = new ArrayList<>();
//            String[] nextLine;
//            while ((nextLine = reader.readNext()) != null) {
//
//                String fullAddress = nextLine[0]; // assuming fullAddress is in the second column
//                zipCodes.add(fullAddress);
//            }
//
//            reader.close();
//        } catch (IOException | CsvValidationException e) {
//            throw new RuntimeException(e);
//        }
//        return zipCodes;
//    }
//    public String[] generateFakeData(List<String> zips) {
//        Random random = new Random();
//
//        String name = faker.name().firstName();
//        String address = faker.address().streetAddress();
//        String zip = zips.get(random.nextInt(zips.size() - 1));
//        System.out.println("Zip: " + zip);
//        return new String[]{name, address, zip};
//    }
//
//    public void writeFakeDataToCsv(int numLines) throws IOException, CsvValidationException {
//        List<String> zips = createZips();
//        try {
//            CSVWriter writer = new CSVWriter(new FileWriter("customers.csv", true));
//
//            for (int i = 0; i < numLines; i++) {
//                String[] data = generateFakeData(zips);
//                writer.writeNext(data);
//                System.out.println("Added customer " + i);
//            }
//
//            writer.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
