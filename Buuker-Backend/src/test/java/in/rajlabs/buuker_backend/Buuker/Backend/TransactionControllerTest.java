package in.rajlabs.buuker_backend.Buuker.Backend;

import in.rajlabs.buuker_backend.Buuker.Backend.controller.api.v1.TransactionController;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerInputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import in.rajlabs.buuker_backend.Buuker.Backend.service.TransactionLedgerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private TransactionLedgerService service;

    @InjectMocks
    private TransactionController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to create a sample TransactionLedgerOutputDTO
    private TransactionLedgerOutputDTO createSampleOutputDTO() {
        return TransactionLedgerOutputDTO.builder()
                .transactionID("testTransactionId")
                .buyPrice(100.0)
                .finalReceiveAmount(95.0)
                .commission(5.0)
                .unit(1)
                .merchantID("testMerchantId")
                .bookingPlatform("testPlatform")
                .bookingMobNo("1234567890")
                .shippedFrom("testLocation")
                .customerID("testCustomerId")
                .shippingTrackingID("testTrackingId")
                .runningBalance(1000.0)
                .createdOn(System.currentTimeMillis())
                .updatedOn(System.currentTimeMillis())
                .bookedViaCard(true)
                .remark("testRemark")
                .orderStatus(OrderStatus.ORDERED)
                .productName("testProduct")
                .productLink("http://example.com/testProduct")
                .build();
    }

    // Helper method to create a sample TransactionLedgerInputDTO
    private TransactionLedgerInputDTO createSampleInputDTO() {
        return TransactionLedgerInputDTO.builder()
                .transactionID("testTransactionId")
                .buyPrice(100.0)
                .finalReceiveAmount(95.0)
                .commission(5.0)
                .unit(1)
                .merchantID("testMerchantId")
                .bookingPlatform("testPlatform")
                .bookingMobNo("1234567890")
                .shippedFrom("testLocation")
                .customerID("testCustomerId")
                .bookedViaCard(true)
                .remark("testRemark")
                .orderStatus(OrderStatus.SHIPPED)
                .productName("testProduct")
                .productLink("http://example.com/testProduct")
                .build();
    }

    @Test
    void testGetAllTransactions() {
        // Arrange
        String customerID = "testCustomerId";
        TransactionLedgerOutputDTO transaction = createSampleOutputDTO();
        when(service.getAllTransactions(customerID)).thenReturn(List.of(transaction));

        // Act
        ResponseEntity<HashMap<String, Object>> response = controller.getAllTransactions(customerID);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("transactions"));
        assertTrue(response.getBody().containsKey("total"));

        @SuppressWarnings("unchecked")
        List<TransactionLedgerOutputDTO> transactions = (List<TransactionLedgerOutputDTO>) response.getBody().get("transactions");
        assertEquals(1, transactions.size());
        assertEquals(transaction.getTransactionID(), transactions.get(0).getTransactionID());
        assertEquals(1, response.getBody().get("total"));
    }

    @Test
    void testGetTransactionById() {
        // Arrange
        String transactionId = "testTransactionId";
        TransactionLedgerOutputDTO transaction = createSampleOutputDTO();
        when(service.getTransactionById(transactionId)).thenReturn(transaction);

        // Act
        ResponseEntity<TransactionLedgerOutputDTO> response = controller.getTransactionById(transactionId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(transactionId, response.getBody().getTransactionID());
    }

    @Test
    void testCreateTransaction() {
        // Arrange
        TransactionLedgerInputDTO inputDTO = createSampleInputDTO();
        TransactionLedgerOutputDTO outputDTO = createSampleOutputDTO();
        when(service.createTransaction(any(TransactionLedgerInputDTO.class))).thenReturn(outputDTO);

        // Act
        ResponseEntity<TransactionLedgerOutputDTO> response = controller.createTransaction(inputDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(outputDTO.getTransactionID(), response.getBody().getTransactionID());
    }

    @Test
    void testUpdateTransaction() {
        // Arrange
        String transactionId = "testTransactionId";
        TransactionLedgerInputDTO inputDTO = createSampleInputDTO();
        TransactionLedgerOutputDTO outputDTO = createSampleOutputDTO();
        when(service.updateTransaction(anyString(), any(TransactionLedgerInputDTO.class))).thenReturn(outputDTO);

        // Act
        ResponseEntity<TransactionLedgerOutputDTO> response = controller.updateTransaction(transactionId, inputDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(outputDTO.getTransactionID(), response.getBody().getTransactionID());
    }

    @Test
    void testDeleteTransaction() {
        // Arrange
        String transactionId = "testTransactionId";

        // Act
        ResponseEntity<Void> response = controller.deleteTransaction(transactionId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteTransaction(transactionId);
    }
}
