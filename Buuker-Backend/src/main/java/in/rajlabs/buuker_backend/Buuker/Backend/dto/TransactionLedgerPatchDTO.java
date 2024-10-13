package in.rajlabs.buuker_backend.Buuker.Backend.dto;

import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
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
    private double runningBalance;
    private String bookingCardDetails;
    private String remark;
    private OrderStatus orderStatus;
    private String productName;
    private String productLink;
    private Long updatedOn;
}
