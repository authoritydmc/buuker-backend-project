package in.rajlabs.buuker_backend.Buuker.Backend.repository;

import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, UUID> {
    
    /**
     * Retrieves all transactions for a specific account.
     *
     * @param accountId ID of the account
     * @return List of AccountTransaction
     */
    List<AccountTransaction> findByAccountId(String accountId);
    
    /**
     * Retrieves all transactions for a specific account and transaction type.
     *
     * @param accountId ID of the account
     * @param transactionType Type of transaction (CREDIT or DEBIT)
     * @return List of AccountTransaction
     */
    List<AccountTransaction> findByAccountIdAndTransactionType(String accountId, TransactionType transactionType);
}
