package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.Result;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerInputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerOutputDTO;

import java.util.List;

/**
 * Service interface for managing TransactionLedger operations.
 */
public interface TransactionLedgerService {

    /**
     * Retrieves all transactions.
     *
     * @return List of TransactionLedgerDTO
     */
    List<TransactionLedgerOutputDTO> getAllTransactions(String customerID, boolean includeDeleted);

    /**
     * Retrieves a transaction by its UUID.
     *
     * @param transUuid Unique identifier of the transaction
     * @return TransactionLedgerDTO
     */
    TransactionLedgerOutputDTO getTransactionById(String transUuid);

    /**
     * Creates a new transaction.
     *
     * @param transactionDTO Data Transfer Object containing transaction details
     * @return Created TransactionLedgerDTO
     */
    TransactionLedgerOutputDTO createTransaction(TransactionLedgerInputDTO transactionDTO);

    /**
     * Updates an existing transaction.
     *
     * @param transUuid     Unique identifier of the transaction to update
     * @param transactionDTO Data Transfer Object containing updated transaction details
     * @return Updated TransactionLedgerDTO
     */
    TransactionLedgerOutputDTO updateTransaction(String transUuid, TransactionLedgerInputDTO transactionDTO);

    /**
     * Deletes a transaction by its UUID.
     *
     * @param transUuid Unique identifier of the transaction to delete
     * @return
     */
    Result<Void> deleteTransaction(String transUuid);

    Result<Void> restoreTransaction(String id);
}
