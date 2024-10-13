package in.rajlabs.buuker_backend.Buuker.Backend;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerInputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.mapper.TransactionLedgerMapper;
import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionLedgerMapperTest {

    private TransactionLedgerMapper mapper;

    @BeforeEach
    void setUp() {
        // Initialize the mapper before each test
        mapper = new TransactionLedgerMapper();
    }

    @Test
    void testToEntity_NullInput() {
        // Test conversion with null input
        TransactionLedger result = mapper.toEntity(null);
        assertNull(result, "Expected null when input DTO is null");
    }

    @Test
    void testToEntity_ValidInput() {
        // Create a valid TransactionLedgerInputDTO
        TransactionLedgerInputDTO dto = TransactionLedgerInputDTO.builder()
                .transactionID("testTransactionId")
                .buyPrice(100.0)
                .finalReceiveAmount(95.0)
                .commission(5.0)
                .unit(1)
                .merchantID("testMerchantId")
                .bookingPlatform("testPlatform")
                .bookingMobNo("1234567890")
                .shippedFrom("testLocation")
                .shippingTrackingID("testTrackingId")
                .customerID("testCustomerId")
                .bookingCardDetails("SBI-0011")
                .remark("testRemark")
                .orderStatus(OrderStatus.DELIVERED)  // Assuming OrderStatus is an Enum
                .productName("testProduct")
                .productLink("http://example.com/testProduct")
                .build();

        // Convert to TransactionLedger entity
        TransactionLedger entity = mapper.toEntity(dto);

        // Assertions to verify the properties are correctly mapped
        assertNotNull(entity, "Expected entity to be created");
        assertEquals(dto.getTransactionID(), entity.getTransactionID());
        assertEquals(dto.getBuyPrice(), entity.getBuyPrice());
        assertEquals(dto.getFinalReceiveAmount(), entity.getFinalReceiveAmount());
        assertEquals(dto.getCommission(), entity.getCommission());
        assertEquals(dto.getUnit(), entity.getUnit());
        assertEquals(dto.getMerchantID(), entity.getMerchantID());
        assertEquals(dto.getBookingPlatform(), entity.getBookingPlatform());
        assertEquals(dto.getBookingMobNo(), entity.getBookingMobNo());
        assertEquals(dto.getShippedFrom(), entity.getShippedFrom());
        assertEquals(dto.getShippingTrackingID(), entity.getShippingTrackingID());
        assertEquals(dto.getCustomerID(), entity.getCustomerID());
        assertEquals(dto.getBookingCardDetails(), entity.getBookingCardDetails());
        assertEquals(dto.getRemark(), entity.getRemark());
        assertEquals(dto.getOrderStatus(), entity.getOrderStatus());
        assertEquals(dto.getProductName(), entity.getProductName());
        assertEquals(dto.getProductLink(), entity.getProductLink());
    }

    @Test
    void testToDTO_NullInput() {
        // Test conversion with null input
        TransactionLedgerOutputDTO result = mapper.toDTO(null);
        assertNull(result, "Expected null when input entity is null");
    }

    @Test
    void testToDTO_ValidInput() {
        // Create a valid TransactionLedger entity
        TransactionLedger entity = TransactionLedger.builder()
                .transactionID("testTransactionId")
                .buyPrice(100.0)
                .finalReceiveAmount(95.0)
                .commission(5.0)
                .unit(1)
                .deletedOn(-1L)
                .merchantID("testMerchantId")
                .bookingPlatform("testPlatform")
                .bookingMobNo("1234567890")
                .shippedFrom("testLocation")
                .shippingTrackingID("SBI-0011")
                .shippingTrackingID("testTrackingId")
                .customerID("testCustomerId")
                .runningBalance(1000.0)
                .createdOn(System.currentTimeMillis())
                .updatedOn(System.currentTimeMillis())
                .remark("testRemark")
                .orderStatus(OrderStatus.ORDERED)  // Assuming OrderStatus is an Enum
                .productName("testProduct")
                .productLink("http://example.com/testProduct")
                .build();

        // Convert to TransactionLedgerOutputDTO
        TransactionLedgerOutputDTO dto = mapper.toDTO(entity);

        // Assertions to verify the properties are correctly mapped
        assertNotNull(dto, "Expected DTO to be created");
        assertEquals(entity.getTransactionID(), dto.getTransactionID());
        assertEquals(entity.getBuyPrice(), dto.getBuyPrice());
        assertEquals(entity.getFinalReceiveAmount(), dto.getFinalReceiveAmount());
        assertEquals(entity.getCommission(), dto.getCommission());
        assertEquals(entity.getUnit(), dto.getUnit());
        assertEquals(entity.getMerchantID(), dto.getMerchantID());
        assertEquals(entity.getBookingPlatform(), dto.getBookingPlatform());
        assertEquals(entity.getBookingMobNo(), dto.getBookingMobNo());
        assertEquals(entity.getDeletedOn(),-1L);
        assertEquals(entity.getShippedFrom(), dto.getShippedFrom());
        assertEquals(entity.getCustomerID(), dto.getCustomerID());
        assertEquals(entity.getShippingTrackingID(), dto.getShippingTrackingID());
        assertEquals(entity.getRunningBalance(), dto.getRunningBalance());
        assertEquals(entity.getCreatedOn(), dto.getCreatedOn());
        assertEquals(entity.getUpdatedOn(), dto.getUpdatedOn());
        assertEquals(entity.getBookingCardDetails(), dto.getBookingCardDetails());
        assertEquals(entity.getRemark(), dto.getRemark());
        assertEquals(entity.getOrderStatus(), dto.getOrderStatus());
        assertEquals(entity.getProductName(), dto.getProductName());
        assertEquals(entity.getProductLink(), dto.getProductLink());
    }
}
