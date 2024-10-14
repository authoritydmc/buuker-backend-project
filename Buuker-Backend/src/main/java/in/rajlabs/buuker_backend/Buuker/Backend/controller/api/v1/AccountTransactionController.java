package in.rajlabs.buuker_backend.Buuker.Backend.controller.api.v1;

import in.rajlabs.buuker_backend.Buuker.Backend.controller.api.API;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import in.rajlabs.buuker_backend.Buuker.Backend.service.AccountTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(API.API_VERSION_V1 + "account-transactions")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    public AccountTransactionController(AccountTransactionService accountTransactionService) {
        this.accountTransactionService = accountTransactionService;
    }

    /**
     * Creates a new account transaction (credit or debit).
     *
     * @param accountTransactionDTO DTO containing transaction details
     * @param principal             The authenticated user principal
     * @return ResponseEntity with the created AccountTransactionDTO
     */
    @PostMapping
    public ResponseEntity<AccountTransactionDTO> createTransaction(
            @Valid @RequestBody AccountTransactionDTO accountTransactionDTO,
            Principal principal) {

        // Optionally, set customerID or merchantID based on authenticated user
        // If customerID is derived from the authenticated user, ignore it in DTO and set it here
        // For this example, assuming customerID is provided in DTO

        AccountTransactionDTO createdTransaction = accountTransactionService.createTransaction(accountTransactionDTO);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific transaction by its UUID.
     *
     * @param transactionId UUID of the transaction
     * @return ResponseEntity containing the AccountTransactionDTO
     */
    @GetMapping("/getByTransactionID/{transactionId}")
    public ResponseEntity<AccountTransactionOutputDTO> getTransactionById(@PathVariable String transactionId) {
        AccountTransactionOutputDTO transaction = accountTransactionService.getTransactionByTransactionId(transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    /**
     * Retrieves all transactions for a specific account.
     *
     * @param accountId ID of the account
     * @return ResponseEntity containing a list of AccountTransactionDTO
     */
    @GetMapping
    public ResponseEntity<List<AccountTransactionOutputDTO>> getTransactionsByAccountId(
            @RequestParam String accountId) {
        List<AccountTransactionOutputDTO> transactions = accountTransactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Retrieves transactions for a specific account and transaction type.
     *
     * @param accountId       ID of the account
     * @param transactionType Type of transaction (CREDIT or DEBIT)
     * @return ResponseEntity containing a list of AccountTransactionDTO
     */
    @GetMapping("/filter")
    public ResponseEntity<List<AccountTransactionOutputDTO>> getTransactionsByAccountIdAndType(
            @RequestParam String accountId,
            @RequestParam TransactionType transactionType) {
        List<AccountTransactionOutputDTO> transactions = accountTransactionService.getTransactionsByAccountIdAndType(accountId, transactionType);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    /**
     * Updates an existing account transaction.
     *
     * @param transactionId         UUID of the transaction to update
     * @param accountTransactionDTO DTO containing updated transaction details
     * @param principal             The authenticated user principal
     * @return ResponseEntity containing the updated AccountTransactionDTO
     */
    @PutMapping("/{transactionId}")
    public ResponseEntity<AccountTransactionOutputDTO> updateTransaction(
            @PathVariable UUID transactionId,
            @Valid @RequestBody AccountTransactionDTO accountTransactionDTO,
            Principal principal) {

        // Optionally, set updatedBy based on authenticated user
        AccountTransactionOutputDTO updatedTransaction = accountTransactionService.updateTransaction(transactionId, accountTransactionDTO);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    /**
     * Deletes a transaction by its UUID.
     *
     * @param transactionId UUID of the transaction to delete
     * @return ResponseEntity with HTTP status
     */
    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId) {
        accountTransactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
