package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;

import java.util.List;

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
    List<AccountTransactionDTO> getTransactionsByAccountId(String accountId);
    
    /**
     * Retrieves transactions for a specific account and transaction type.
     *
     * @param accountId ID of the account
     * @param transactionType Type of transaction (CREDIT or DEBIT)
     * @return List of AccountTransactionDTO
     */
    List<AccountTransactionDTO> getTransactionsByAccountIdAndType(String accountId, TransactionType transactionType);
}
