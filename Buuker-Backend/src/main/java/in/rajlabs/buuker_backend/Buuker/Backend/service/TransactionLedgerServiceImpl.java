package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.Result;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerInputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.exception.ResourceAlreadyExistsException;
import in.rajlabs.buuker_backend.Buuker.Backend.exception.ResourceNotFoundException;
import in.rajlabs.buuker_backend.Buuker.Backend.mapper.TransactionLedgerMapper;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import in.rajlabs.buuker_backend.Buuker.Backend.repository.TransactionLedgerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of TransactionLedgerService interface.
 */
@Service
public class TransactionLedgerServiceImpl implements TransactionLedgerService {

    private final TransactionLedgerRepository repository;

    private final TransactionLedgerMapper mapper;

    public TransactionLedgerServiceImpl(TransactionLedgerRepository repository, TransactionLedgerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TransactionLedgerOutputDTO> getAllTransactions(String customerID, boolean includeDeleted) {
        List<TransactionLedger> transactions =includeDeleted? repository.getAllCustomerTransactions(customerID): repository.getAllCustomerTransactionsNonDeleted(customerID);
        return transactions.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionLedgerOutputDTO getTransactionById(String transUuid) {
        TransactionLedger transaction = repository.findById(transUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transUuid));
        return mapper.toDTO(transaction);
    }

    /**
     * Create a new transaction if the transaction ID does not already exist.
     *
     * @param transactionDTO the TransactionLedgerInputDTO containing transaction details
     * @return TransactionLedgerOutputDTO the created transaction
     */
    @Override
    public TransactionLedgerOutputDTO createTransaction(TransactionLedgerInputDTO transactionDTO) {
        // Check if the transaction ID already exists
        Optional<TransactionLedger> existingTransaction = repository.findById(transactionDTO.getTransactionID());

        // If it exists, throw a ResourceAlreadyExistsException
        if (existingTransaction.isPresent()) {
            throw new ResourceAlreadyExistsException("Transaction ID " + transactionDTO.getTransactionID() + " already exists.");
        }

        // If it does not exist, create a new transaction
        TransactionLedger transaction = mapper.toEntity(transactionDTO);
        TransactionLedger savedTransaction = repository.save(transaction);
        return mapper.toDTO(savedTransaction);
    }

    @Override
    public TransactionLedgerOutputDTO updateTransaction(String transUuid, TransactionLedgerInputDTO transactionDTO) {
        TransactionLedger existingTransaction = repository.findById(transUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transUuid));

        // Update fields
        existingTransaction.setBuyPrice(transactionDTO.getBuyPrice());
        existingTransaction.setFinalReceiveAmount(transactionDTO.getFinalReceiveAmount());
        existingTransaction.setCommission(transactionDTO.getCommission());
        existingTransaction.setUnit(transactionDTO.getUnit());
        existingTransaction.setMerchantID(transactionDTO.getMerchantID());
        existingTransaction.setBookingPlatform(transactionDTO.getBookingPlatform());
        existingTransaction.setBookingMobNo(transactionDTO.getBookingMobNo());
        existingTransaction.setShippedFrom(transactionDTO.getShippedFrom());
        existingTransaction.setShippingTrackingID(transactionDTO.getShippingTrackingID());
        existingTransaction.setRunningBalance(calculateRunningBalance(transactionDTO.getCustomerID()));
        existingTransaction.setBookedViaCard(transactionDTO.isBookedViaCard());
        existingTransaction.setCustomerID(transactionDTO.getCustomerID());
        existingTransaction.setRemark(transactionDTO.getRemark());
        existingTransaction.setOrderStatus(transactionDTO.getOrderStatus());
        existingTransaction.setProductName(transactionDTO.getProductName());
        existingTransaction.setProductLink(transactionDTO.getProductLink());

        // updatedOn is handled automatically via @PreUpdate in the entity
        TransactionLedger updatedTransaction = repository.save(existingTransaction);
        return mapper.toDTO(updatedTransaction);
    }

    private double calculateRunningBalance(String customerID) {
        return 0.0;
    }

    @Override
    public Result<Void> deleteTransaction(String transUuid) {
        TransactionLedger existingTransaction = repository.findById(transUuid)
                .orElse(null); // No exception, just null if not found

        if (existingTransaction == null) {
            return new Result<>(false, "Transaction not found."); // Return result without data
        }

        if (existingTransaction.isDeleted()) {
            return new Result<>(false, "Transaction already deleted."); // Return result without data
        }

        // Mark the transaction as deleted
        existingTransaction.setDeleted(true);
        existingTransaction.setDeletedOn(System.currentTimeMillis());
        repository.save(existingTransaction);

        return new Result<>(true, "Transaction deleted successfully."); // Return result without data
    }


    @Override
    public Result<Void> restoreTransaction(String transUuid) {
        TransactionLedger existingTransaction = repository.findById(transUuid)
                .orElse(null); // No exception, just null if not found

        if (existingTransaction == null) {
            return new Result<>(false, "Transaction not found."); // Return result without data
        }

        if (!existingTransaction.isDeleted()) {
            return new Result<>(false, "Transaction already restored."); // Return result without data
        }

        // Mark the transaction as deleted
        existingTransaction.setDeleted(false);
        existingTransaction.setDeletedOn(-1L);
        repository.save(existingTransaction);

        return new Result<>(true, "Transaction restored successfully.");
    }
}
