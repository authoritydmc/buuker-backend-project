package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerDTO;

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
    List<TransactionLedgerDTO> getAllTransactions();

    /**
     * Retrieves a transaction by its UUID.
     *
     * @param transUuid Unique identifier of the transaction
     * @return TransactionLedgerDTO
     */
    TransactionLedgerDTO getTransactionById(String transUuid);

    /**
     * Creates a new transaction.
     *
     * @param transactionDTO Data Transfer Object containing transaction details
     * @return Created TransactionLedgerDTO
     */
    TransactionLedgerDTO createTransaction(TransactionLedgerDTO transactionDTO);

    /**
     * Updates an existing transaction.
     *
     * @param transUuid     Unique identifier of the transaction to update
     * @param transactionDTO Data Transfer Object containing updated transaction details
     * @return Updated TransactionLedgerDTO
     */
    TransactionLedgerDTO updateTransaction(String transUuid, TransactionLedgerDTO transactionDTO);

    /**
     * Deletes a transaction by its UUID.
     *
     * @param transUuid Unique identifier of the transaction to delete
     */
    void deleteTransaction(String transUuid);
}
