package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.TransactionLedgerDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.exception.ResourceNotFoundException;
import in.rajlabs.buuker_backend.Buuker.Backend.mapper.TransactionLedgerMapper;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import in.rajlabs.buuker_backend.Buuker.Backend.repository.TransactionLedgerRepository;
import in.rajlabs.buuker_backend.Buuker.Backend.service.TransactionLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of TransactionLedgerService interface.
 */
@Service
public class TransactionLedgerServiceImpl implements TransactionLedgerService {

    @Autowired
    private TransactionLedgerRepository repository;

    @Autowired
    private TransactionLedgerMapper mapper;

    @Override
    public List<TransactionLedgerDTO> getAllTransactions() {
        List<TransactionLedger> transactions = repository.findAll();
        return transactions.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionLedgerDTO getTransactionById(String transUuid) {
        TransactionLedger transaction = repository.findById(transUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transUuid));
        return mapper.toDTO(transaction);
    }

    @Override
    public TransactionLedgerDTO createTransaction(TransactionLedgerDTO transactionDTO) {
        TransactionLedger transaction = mapper.toEntity(transactionDTO);
        TransactionLedger savedTransaction = repository.save(transaction);
        return mapper.toDTO(savedTransaction);
    }

    @Override
    public TransactionLedgerDTO updateTransaction(String transUuid, TransactionLedgerDTO transactionDTO) {
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
    public void deleteTransaction(String transUuid) {
        TransactionLedger existingTransaction = repository.findById(transUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transUuid));
        repository.delete(existingTransaction);
    }
}
