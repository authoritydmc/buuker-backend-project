package in.rajlabs.buuker_backend.Buuker.Backend.mapper;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerInputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import org.springframework.stereotype.Component;

/**
 * Mapper class to convert between TransactionLedger and TransactionLedgerDTO.
 */
@Component
public class TransactionLedgerMapper {

    /**
     * Converts TransactionLedgerDTO to TransactionLedger entity.
     *
     * @param dto Data Transfer Object
     * @return TransactionLedger entity
     */
    public TransactionLedger toEntity(TransactionLedgerInputDTO dto) {
        if (dto == null) {
            return null;
        }

        TransactionLedger.TransactionLedgerBuilder builder = TransactionLedger.builder()
                .buyPrice(dto.getBuyPrice())
                .finalReceiveAmount(dto.getFinalReceiveAmount())
                .commission(dto.getCommission())
                .unit(dto.getUnit())
                .merchantID(dto.getMerchantID())
                .bookingPlatform(dto.getBookingPlatform())
                .bookingMobNo(dto.getBookingMobNo())
                .shippedFrom(dto.getShippedFrom())
                .shippingTrackingID(dto.getShippingTrackingID())
                .customerID(dto.getCustomerID())
                .bookedViaCard(dto.isBookedViaCard())
                .remark(dto.getRemark())
                .orderStatus(dto.getOrderStatus())
                .productName(dto.getProductName())
                .productLink(dto.getProductLink());

        // Set transactionID only if present (useful for updates)
        if (dto.getTransactionID() != null) {
            builder.transactionID(dto.getTransactionID());
        }

        // createdOn and updatedOn are managed automatically via @PrePersist and @PreUpdate

        return builder.build();
    }

    /**
     * Converts TransactionLedger entity to TransactionLedgerOutputDTO.
     *
     * @param entity TransactionLedger entity
     * @return TransactionLedgerOutputDTO
     */
    public TransactionLedgerOutputDTO toDTO(TransactionLedger entity) {
        if (entity == null) {
            return null;
        }

        return TransactionLedgerOutputDTO.builder()
                .transactionID(entity.getTransactionID())
                .buyPrice(entity.getBuyPrice())
                .finalReceiveAmount(entity.getFinalReceiveAmount())
                .commission(entity.getCommission())
                .unit(entity.getUnit())
                .merchantID(entity.getMerchantID())
                .bookingPlatform(entity.getBookingPlatform())
                .bookingMobNo(entity.getBookingMobNo())
                .shippedFrom(entity.getShippedFrom())
                .customerID(entity.getCustomerID())
                .shippingTrackingID(entity.getShippingTrackingID())
                .runningBalance(entity.getRunningBalance())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .bookedViaCard(entity.isBookedViaCard())
                .remark(entity.getRemark())
                .orderStatus(entity.getOrderStatus())
                .productName(entity.getProductName())
                .productLink(entity.getProductLink())
                .build();
    }
}
