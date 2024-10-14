package in.rajlabs.buuker_backend.Buuker.Backend.dto;

import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for creating/updating TransactionLedger.
 */
@Data
@Builder
public class TransactionLedgerInputDTO {

    /**
     * Unique identifier for the transaction.
     * This may be provided during updates.
     */
    private String transactionID;

    @NotNull(message = "Buy price is required")
    @Min(value = 1,message = "finalReceiveAmount can't be zero")

    private double buyPrice;

    @NotNull(message = "Final receive amount is required")
    @Min(value = 1,message = "finalReceiveAmount can't be zero")
    private double finalReceiveAmount;

    @NotNull(message = "Commission is required")
    @Min(value = 0,message = "finalReceiveAmount can not be less than 0")
    private double commission;

    @NotNull(message = "Unit is required")
    @Min(value = 1, message = "Unit must be at least 1")
    private int unit;

    @NotBlank(message = "Merchant ID is required")
    private String merchantID;

    @NotBlank(message = "Booking platform is required")
    private String bookingPlatform;

    @NotBlank(message = "Booking mobile number is required")
    private String bookingMobNo;

    private String shippedFrom;

    private String shippingTrackingID;

    @NotBlank(message = "Customer ID is required")
    private String customerID;

    @NotNull(message = "CreatedOn timestamp is required")
    @Min(value = 1690000000000L, message = "createdOn is epoch timestamp !! please pass correct value ")
    private Long createdOn;

    @NotNull(message = "updatedOn timestamp is required")
    @Min(value = 1690000000000L, message = "updatedOn is epoch timestamp !! please pass correct value ")

    private Long updatedOn;

    private String bookingCardDetails;

    private String remark;

    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String productLink;
}
