package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;

import java.util.List;
import java.util.UUID;

public interface AccountTransactionService {

    /**
     * Creates a new account transaction.
     *
     * @param accountTransactionDTO DTO containing transaction details
     * @return AccountTransactionDTO of the created transaction
     */
    AccountTransactionDTO createTransaction(AccountTransactionDTO accountTransactionDTO);

    /**
     * Retrieves all transactions for a specific account.
     *
     * @param accountId ID of the account
     * @return List of AccountTransactionDTO
     */
    List<AccountTransactionOutputDTO> getTransactionsByAccountId(String accountId);

    /**
     * Retrieves transactions for a specific account and transaction type.
     *
     * @param accountId       ID of the account
     * @param transactionType Type of transaction (CREDIT or DEBIT)
     * @return List of AccountTransactionDTO
     */
    List<AccountTransactionOutputDTO> getTransactionsByAccountIdAndType(String accountId, TransactionType transactionType);

    /**
     * Retrieves a specific transaction by its UUID.
     *
     * @param transactionId UUID of the transaction
     * @return AccountTransactionDTO of the found transaction
     */
    AccountTransactionOutputDTO getTransactionById(UUID transactionId);

    /**
     * Updates an existing transaction.
     *
     * @param transactionId         UUID of the transaction to update
     * @param accountTransactionDTO DTO containing updated transaction details
     * @return AccountTransactionDTO of the updated transaction
     */
    AccountTransactionOutputDTO updateTransaction(UUID transactionId, AccountTransactionDTO accountTransactionDTO);

    /**
     * Deletes a transaction by its UUID.
     *
     * @param transactionId UUID of the transaction to delete
     */
    void deleteTransaction(UUID transactionId);

    void deleteTransaction(TransactionLedger existingTransaction);

    void updateTransaction(TransactionLedger updatedTransaction);

    void saveDebitTransaction(TransactionLedger transaction);

    AccountTransactionOutputDTO getTransactionByTransactionId(String transactionId);
}
