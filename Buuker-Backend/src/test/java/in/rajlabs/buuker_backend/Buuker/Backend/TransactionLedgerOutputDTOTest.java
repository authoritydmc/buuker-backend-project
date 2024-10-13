package in.rajlabs.buuker_backend.Buuker.Backend;

import static org.junit.jupiter.api.Assertions.*;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import org.junit.jupiter.api.Test;

class TransactionLedgerOutputDTOTest {

    @Test
    void testTransactionLedgerOutputDTO() {
        // Arrange
        TransactionLedgerOutputDTO transactionOutput = TransactionLedgerOutputDTO.builder()
                .transactionID("TX12345")
                .buyPrice(150.00)
                .finalReceiveAmount(145.00)
                .commission(5.00)
                .unit(10)
                .merchantID("M123")
                .bookingPlatform("Online")
                .bookingMobNo("1234567890")
                .shippedFrom("Warehouse A")
                .customerID("C456")
                .shippingTrackingID("TRACK123")
                .runningBalance(1000.00)
                .createdOn(System.currentTimeMillis())
                .updatedOn(System.currentTimeMillis())
                .bookedViaCard(true)
                .remark("Fast delivery")
                .orderStatus(OrderStatus.ORDERED)
                .productName("Example Product")
                .productLink("http://example.com/product")
                .build();

        // Assert
        assertEquals("TX12345", transactionOutput.getTransactionID());
        assertEquals(150.00, transactionOutput.getBuyPrice());
        assertEquals(145.00, transactionOutput.getFinalReceiveAmount());
        assertEquals(5.00, transactionOutput.getCommission());
        assertEquals(10, transactionOutput.getUnit());
        assertEquals("M123", transactionOutput.getMerchantID());
        assertEquals("Online", transactionOutput.getBookingPlatform());
        assertEquals("1234567890", transactionOutput.getBookingMobNo());
        assertEquals("Warehouse A", transactionOutput.getShippedFrom());
        assertEquals("C456", transactionOutput.getCustomerID());
        assertEquals("TRACK123", transactionOutput.getShippingTrackingID());
        assertEquals(1000.00, transactionOutput.getRunningBalance());
        assertNotNull(transactionOutput.getCreatedOn());
        assertNotNull(transactionOutput.getUpdatedOn());
        assertTrue(transactionOutput.isBookedViaCard());
        assertEquals("Fast delivery", transactionOutput.getRemark());
        assertEquals(OrderStatus.ORDERED, transactionOutput.getOrderStatus());
        assertEquals("Example Product", transactionOutput.getProductName());
        assertEquals("http://example.com/product", transactionOutput.getProductLink());
    }
}
