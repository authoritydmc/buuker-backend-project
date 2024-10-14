package in.rajlabs.buuker_backend.Buuker.Backend.repository;

import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for TransactionLedger entities.
 */
@Repository
public interface TransactionLedgerRepository extends JpaRepository<TransactionLedger, String> {
    // Additional query methods can be defined here if needed
    @Query("select t from TransactionLedger t where t.customerID = ?1 ")
    List<TransactionLedger> getAllCustomerTransactions(String customerID);
    @Query("select t from TransactionLedger t where t.customerID = ?1 and t.isDeleted <> true")
    List<TransactionLedger> getAllCustomerTransactionsNonDeleted(String customerID);



    @Query("SELECT SUM(t.finalReceiveAmount) FROM TransactionLedger t WHERE t.customerID = :customerId")
    Double sumAmountByCustomerId(String customerId);

    @Query("select count(t) from TransactionLedger t where t.isDeleted <> true and t.customerID = ?1")
    long getCustomerTransactionCountNonDeleted(String customerID);
}

