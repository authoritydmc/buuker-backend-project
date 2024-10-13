package in.rajlabs.buuker_backend.Buuker.Backend.controller.api.v1;

import in.rajlabs.buuker_backend.Buuker.Backend.controller.api.API;
import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * REST controller for managing OrderStatus.
 */
@RestController
@RequestMapping(API.API_VERSION_V1+"order-status") // Base path for OrderStatus endpoints
public class OrderStatusController {

    /**
     * Retrieves all available OrderStatus choices.
     *
     * @return ResponseEntity containing an array of OrderStatus values
     */
    @GetMapping("/choices") // Updated endpoint to better reflect purpose
    public ResponseEntity<OrderStatus[]> getAllOrderStatusChoices() {
        // Get all enum values
        OrderStatus[] statuses = OrderStatus.values();
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
}
