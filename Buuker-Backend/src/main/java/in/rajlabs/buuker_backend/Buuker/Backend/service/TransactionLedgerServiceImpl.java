package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.*;
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

    private final AccountTransactionService accountTransactionService;

    public TransactionLedgerServiceImpl(TransactionLedgerRepository repository, TransactionLedgerMapper mapper, AccountTransactionService accountTransactionService) {
        this.repository = repository;
        this.mapper = mapper;
        this.accountTransactionService = accountTransactionService;
    }

    @Override
    public List<TransactionLedgerOutputDTO> getAllTransactions(String customerID, boolean includeDeleted) {
        List<TransactionLedger> transactions = includeDeleted ? repository.getAllCustomerTransactions(customerID) : repository.getAllCustomerTransactionsNonDeleted(customerID);
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
        accountTransactionService.saveDebitTransaction(transaction);
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
        existingTransaction.setBookingCardDetails(transactionDTO.getBookingCardDetails());
        existingTransaction.setCustomerID(transactionDTO.getCustomerID());
        existingTransaction.setRemark(transactionDTO.getRemark());
        existingTransaction.setBookingCardDetails(transactionDTO.getBookingCardDetails());
        existingTransaction.setOrderStatus(transactionDTO.getOrderStatus());
        existingTransaction.setProductName(transactionDTO.getProductName());
        existingTransaction.setProductLink(transactionDTO.getProductLink());
        existingTransaction.setUpdatedOn(transactionDTO.getUpdatedOn());
        TransactionLedger updatedTransaction = repository.save(existingTransaction);
        accountTransactionService.updateTransaction(updatedTransaction);

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
        accountTransactionService.deleteTransaction(existingTransaction);
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
        accountTransactionService.saveDebitTransaction(existingTransaction);
        return new Result<>(true, "Transaction restored successfully.");
    }

    /**
     * Updates an existing transaction ledger partially.
     *
     * @param transactionId The ID of the transaction to update.
     * @param patchDTO      DTO containing the fields to be updated.
     * @return The updated TransactionLedger entity.
     */
    public TransactionLedgerOutputDTO patchTransaction(String transactionId, TransactionLedgerPatchDTO patchDTO) {
        TransactionLedger existingTransaction = repository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + transactionId));

        // Update fields if provided in the patchDTO
        if (patchDTO.getBuyPrice() > 0) existingTransaction.setBuyPrice(patchDTO.getBuyPrice());
        if (patchDTO.getFinalReceiveAmount() > 0)
            existingTransaction.setFinalReceiveAmount(patchDTO.getFinalReceiveAmount());
        if (patchDTO.getCommission() > 0) existingTransaction.setCommission(patchDTO.getCommission());
        if (patchDTO.getUnit() > 0) existingTransaction.setUnit(patchDTO.getUnit());
        if (patchDTO.getCustomerID() != null) existingTransaction.setCustomerID(patchDTO.getCustomerID());
        if (patchDTO.getMerchantID() != null) existingTransaction.setMerchantID(patchDTO.getMerchantID());
        if (patchDTO.getBookingPlatform() != null)
            existingTransaction.setBookingPlatform(patchDTO.getBookingPlatform());
        if (patchDTO.getBookingMobNo() != null) existingTransaction.setBookingMobNo(patchDTO.getBookingMobNo());
        if (patchDTO.getShippedFrom() != null) existingTransaction.setShippedFrom(patchDTO.getShippedFrom());
        if (patchDTO.getShippingTrackingID() != null)
            existingTransaction.setShippingTrackingID(patchDTO.getShippingTrackingID());
        if (patchDTO.getBookingCardDetails() != null)
            existingTransaction.setBookingCardDetails(patchDTO.getBookingCardDetails());
        if (patchDTO.getRemark() != null) existingTransaction.setRemark(patchDTO.getRemark());
        if (patchDTO.getOrderStatus() != null) existingTransaction.setOrderStatus(patchDTO.getOrderStatus());
        if (patchDTO.getProductName() != null) existingTransaction.setProductName(patchDTO.getProductName());
        if (patchDTO.getProductLink() != null) existingTransaction.setProductLink(patchDTO.getProductLink());
        if (patchDTO.getUpdatedOn() != null) existingTransaction.setUpdatedOn(patchDTO.getUpdatedOn());
        accountTransactionService.updateTransaction(existingTransaction);
        return mapper.toDTO(repository.save(existingTransaction));
    }

    @Override
    public TransactionSummaryDTO getTransactionSummaryByCustomer(String customerId) {
        Long totalTransactions = repository.getCustomerTransactionCountNonDeleted(customerId);
        Double totalAmount = repository.sumAmountByCustomerId(customerId);

        return new TransactionSummaryDTO(customerId, totalTransactions, totalAmount);
    }
}
