//package java.no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.SmallTests;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class EvaluationMethodsTests {
//
//    private static List<String> timestamps = new ArrayList<>();
//
//
//    //used for testing evaluation of timestamp
//    public static class testClass {
//        public String timeStampCheck() {
//            var current = System.currentTimeMillis();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return System.currentTimeMillis() - current + ", create shipment";
//        }
//    }
//
//    @Test
//    @DisplayName("Check one way to run the evaluation, each method returns a string with timestamp " +
//            "and type of operation to a static list")
//    public void addToList() {
//        testClass test = new testClass();
//        for (int i = 0; i < 10; i++) {
//            timestamps.add(test.timeStampCheck());
//        }
//        timestamps.forEach(System.out::println);
//        assertEquals(10,timestamps.size());
//    }
//}
