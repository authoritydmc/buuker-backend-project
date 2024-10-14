package in.rajlabs.buuker_backend.Buuker.Backend.dto;

import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionLedgerPatchDTO {
    private double buyPrice;
    private double finalReceiveAmount;
    private double commission;
    private int unit;
    private String customerID;
    private String merchantID;
    private String bookingPlatform;
    private String bookingMobNo;
    private String shippedFrom;
    private String shippingTrackingID;
    private String bookingCardDetails;
    private String remark;
    private OrderStatus orderStatus;
    private String productName;
    private String productLink;

    @NotNull(message = "updatedOn value is required")
    @Min(value = 1690000000000L, message = "updatedOn is epoch timestamp !! please pass correct value ")
    private Long updatedOn;
//    1620000000000L -> after  July 22, 2023, only allowed
}
