package JOOQ.Controllers;

import JOOQ.service.ShipmentService;
import org.jooq.meta.derby.sys.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/shipment/eval")
    public List<String> getShipmentEval() {
        List<String> timeTakenList = shipmentService.getTimeTakenList();
        //System.out.println(timeTakenList);
        return timeTakenList;
    }

}
