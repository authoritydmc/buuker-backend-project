package in.rajlabs.buuker_backend.Buuker.Backend.repository;

import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for TransactionLedger entities.
 */
@Repository
public interface TransactionLedgerRepository extends JpaRepository<TransactionLedger, String> {
    // Additional query methods can be defined here if needed
}
