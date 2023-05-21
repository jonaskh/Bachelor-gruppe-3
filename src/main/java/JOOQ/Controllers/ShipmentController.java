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

    /**
     * Creates a new instance of ShipmentController.
     *
     * @param shipmentService - the ShipmentService dependency to be injected.
     */
    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /**
     * Retrieves the list of time taken for each shipment evaluation.
     *
     * @return List<String> - the list of time taken for each shipment evaluation.
     */
    @GetMapping("/shipment/eval")
    public List<String> getShipmentEval() {
        List<String> timeTakenList = shipmentService.getTimeTakenList();
        return timeTakenList;
    }

}
