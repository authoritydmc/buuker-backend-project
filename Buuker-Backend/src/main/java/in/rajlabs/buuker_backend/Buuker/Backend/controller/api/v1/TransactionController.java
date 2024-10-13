package in.rajlabs.buuker_backend.Buuker.Backend.controller.api.v1;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerInputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.service.TransactionLedgerService;
import in.rajlabs.buuker_backend.Buuker.Backend.controller.api.API; // Import the API class
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * REST controller for managing TransactionLedger entities.
 */
@RestController
@RequestMapping(API.API_VERSION_V1 + "transactions") // Use the API constant for versioning
public class TransactionController {

    private final TransactionLedgerService service;

    public TransactionController(TransactionLedgerService service) {
        this.service = service;
    }

    /**
     * Retrieves all transactions.
     *
     * @return ResponseEntity containing a list of TransactionLedgerDTO
     */
    @GetMapping
    public ResponseEntity<?> getAllTransactions(@RequestParam String customerID) {
        List<TransactionLedgerOutputDTO> transactions = service.getAllTransactions(customerID);
        var responseMap=new HashMap<String, Object>();
        responseMap.put("transactions", transactions);
        responseMap.put("total", transactions.size());
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    /**
     * Retrieves a specific transaction by its UUID.
     *
     * @param id UUID of the transaction
     * @return ResponseEntity containing the TransactionLedgerDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionLedgerOutputDTO> getTransactionById(@PathVariable("id") String id) {
        TransactionLedgerOutputDTO transaction = service.getTransactionById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    /**
     * Creates a new transaction.
     *
     * @param transactionDTO DTO containing transaction details
     * @return ResponseEntity containing the created TransactionLedgerDTO
     */
    @PostMapping
    public ResponseEntity<TransactionLedgerOutputDTO> createTransaction(
            @Validated @RequestBody TransactionLedgerInputDTO transactionDTO) {
        TransactionLedgerOutputDTO createdTransaction = service.createTransaction(transactionDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    /**
     * Updates an existing transaction.
     *
     * @param id             UUID of the transaction to update
     * @param transactionDTO DTO containing updated transaction details
     * @return ResponseEntity containing the updated TransactionLedgerDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransactionLedgerOutputDTO> updateTransaction(
            @PathVariable("id") String id,
            @Validated @RequestBody TransactionLedgerInputDTO transactionDTO) {
        TransactionLedgerOutputDTO updatedTransaction = service.updateTransaction(id, transactionDTO);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    /**
     * Deletes a transaction by its UUID.
     *
     * @param id UUID of the transaction to delete
     * @return ResponseEntity with HTTP status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") String id) {
        service.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
