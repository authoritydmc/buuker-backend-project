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
    @Query("select t from TransactionLedger t where t.customerID = ?1")
    List<TransactionLedger> getAllCustomerTransactions(String customerID);
}
