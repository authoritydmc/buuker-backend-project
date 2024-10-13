package in.rajlabs.buuker_backend.Buuker.Backend.controller.api.v1;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.service.TransactionLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing TransactionLedger entities.
 */
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionLedgerService service;

    /**
     * Retrieves all transactions.
     *
     * @return ResponseEntity containing a list of TransactionLedgerDTO
     */
    @GetMapping
    public ResponseEntity<List<TransactionLedgerDTO>> getAllTransactions() {
        List<TransactionLedgerDTO> transactions = service.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Retrieves a specific transaction by its UUID.
     *
     * @param id UUID of the transaction
     * @return ResponseEntity containing the TransactionLedgerDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionLedgerDTO> getTransactionById(@PathVariable("id") String id) {
        TransactionLedgerDTO transaction = service.getTransactionById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    /**
     * Creates a new transaction.
     *
     * @param transactionDTO DTO containing transaction details
     * @return ResponseEntity containing the created TransactionLedgerDTO
     */
    @PostMapping
    public ResponseEntity<TransactionLedgerDTO> createTransaction(
            @Validated @RequestBody TransactionLedgerDTO transactionDTO) {
        TransactionLedgerDTO createdTransaction = service.createTransaction(transactionDTO);
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
    public ResponseEntity<TransactionLedgerDTO> updateTransaction(
            @PathVariable("id") String id,
            @Validated @RequestBody TransactionLedgerDTO transactionDTO) {
        TransactionLedgerDTO updatedTransaction = service.updateTransaction(id, transactionDTO);
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
