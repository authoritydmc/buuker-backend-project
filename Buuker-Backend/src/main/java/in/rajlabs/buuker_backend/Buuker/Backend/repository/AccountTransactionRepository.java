package in.rajlabs.buuker_backend.Buuker.Backend.repository;

import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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

    @Query("select a from AccountTransaction a where a.transactionId = ?1")
    Optional<AccountTransaction> getByTransactionID(String transactionId);


    /**
     * Finds all transactions updated after the specified timestamp and orders them by updatedOn in ascending order.
     *
     * @param afterUpdatedOn the timestamp to filter transactions
     * @return a list of AccountTransaction sorted from first to last
     */
    @Query("SELECT a FROM AccountTransaction a WHERE a.updatedOn >= :afterUpdatedOn and a.accountId = :accountID ORDER BY a.updatedOn ASC")
    List<AccountTransaction> getAllAccountsToUpdate(@Param("afterUpdatedOn") Long afterUpdatedOn, @Param("accountID") String accountID);

    /**
     * Finds the previous account transaction before the specified updatedOn timestamp for a specific account ID.
     *
     * @param beforeUpdatedOn the timestamp to filter transactions
     * @param accountID the ID of the account
     * @return an Optional containing the previous AccountTransaction, if present
     */
    @Query("SELECT a FROM AccountTransaction a WHERE a.updatedOn <= :beforeUpdatedOn AND a.accountId = :accountID ORDER BY a.updatedOn DESC LIMIT 1")
   Optional< AccountTransaction> getPreviousAccountTransaction(@Param("beforeUpdatedOn") Long beforeUpdatedOn, @Param("accountID") String accountID);
}
