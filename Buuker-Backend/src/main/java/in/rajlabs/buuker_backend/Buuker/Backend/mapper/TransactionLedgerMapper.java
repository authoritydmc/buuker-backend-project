package in.rajlabs.buuker_backend.Buuker.Backend.mapper;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerDTO;
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
    public TransactionLedger toEntity(TransactionLedgerDTO dto) {
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
     * Converts TransactionLedger entity to TransactionLedgerDTO.
     *
     * @param entity TransactionLedger entity
     * @return TransactionLedgerDTO
     */
    public TransactionLedgerDTO toDTO(TransactionLedger entity) {
        if (entity == null) {
            return null;
        }

        TransactionLedgerDTO dto = new TransactionLedgerDTO();
        dto.setTransactionID(entity.getTransactionID());
        dto.setBuyPrice(entity.getBuyPrice());
        dto.setFinalReceiveAmount(entity.getFinalReceiveAmount());
        dto.setCommission(entity.getCommission());
        dto.setUnit(entity.getUnit());
        dto.setMerchantID(entity.getMerchantID());
        dto.setBookingPlatform(entity.getBookingPlatform());
        dto.setBookingMobNo(entity.getBookingMobNo());
        dto.setShippedFrom(entity.getShippedFrom());
        dto.setShippingTrackingID(entity.getShippingTrackingID());
        dto.setCustomerID(entity.getCustomerID());
//        dto.setRunningBalance(entity.getRunningBalance());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setUpdatedOn(entity.getUpdatedOn());
        dto.setBookedViaCard(entity.isBookedViaCard());
        dto.setRemark(entity.getRemark());
        dto.setOrderStatus(entity.getOrderStatus());
        dto.setProductName(entity.getProductName());
        dto.setProductLink(entity.getProductLink());

        return dto;
    }
}
