//package java.no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.CheckpointTests;
//
//import java.no.ntnu.bachelor_group3.dataaccessevaluation.EntityTests.TestConfiguration;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CheckpointService;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@Import(TestConfiguration.class) //to load the required beans for the services
//public class CreateCheckpointTest {
//
//    @Autowired
//    CheckpointService checkpointService;
//
////    @Test
////    public void addCheckpointToDatabasePositiveTest() {
////        Checkpoint checkpoint = new Checkpoint("Myrteigen 5", Checkpoint.CheckpointType.Collected);
////        checkpointService.addCheckpoint(checkpoint);
////        System.out.println(checkpointService.findByID(checkpoint.getCheckpoint_id()));
////
////        assertNotNull(checkpointService.findByID(checkpoint.getCheckpoint_id()));
////    }
//}
