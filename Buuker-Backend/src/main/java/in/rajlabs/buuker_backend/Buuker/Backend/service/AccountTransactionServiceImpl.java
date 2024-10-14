package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.exception.ResourceNotFoundException;
import in.rajlabs.buuker_backend.Buuker.Backend.mapper.AccountTransactionMapper;
import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import in.rajlabs.buuker_backend.Buuker.Backend.repository.AccountTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountTransactionMapper mapper;

    public AccountTransactionServiceImpl(AccountTransactionRepository accountTransactionRepository, AccountTransactionMapper mapper) {
        this.accountTransactionRepository = accountTransactionRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public AccountTransactionDTO createTransaction(AccountTransactionDTO accountTransactionDTO) {

        var accountTransaction = mapper.toEntity(accountTransactionDTO);
        accountTransactionRepository.save(accountTransaction);
        return mapper.toDTO(accountTransaction);

    }


    @Override
    public List<AccountTransactionOutputDTO> getTransactionsByAccountId(String accountId) {
        return accountTransactionRepository.findByAccountId(accountId).stream().map(mapper::toOutputDTO).collect(Collectors.toList());
    }

    @Override
    public List<AccountTransactionOutputDTO> getTransactionsByAccountIdAndType(String accountId, TransactionType transactionType) {
        return accountTransactionRepository.findByAccountIdAndTransactionType(accountId, transactionType).stream().map(mapper::toOutputDTO).collect(Collectors.toList());
    }

    @Override
    public AccountTransactionOutputDTO getTransactionById(UUID transactionId) {
        var accEntity = accountTransactionRepository.findById(transactionId).orElseThrow();
        return mapper.toOutputDTO(accEntity);
    }

    @Override
    public AccountTransactionOutputDTO updateTransaction(UUID transactionId, AccountTransactionDTO accountTransactionDTO) {
        return null;
    }

    @Override
    public void deleteTransaction(UUID transactionId) {

    }

    @Override
    public void saveDebitTransaction(TransactionLedger transaction) {

        AccountTransaction accountTransaction = mapper.toEntity(transaction);
        log.info("Saving debit transaction" + accountTransaction);
        accountTransactionRepository.save(accountTransaction);
    }

    @Override
    public AccountTransactionOutputDTO getTransactionByTransactionId(String transactionId) {
        var accEntity = accountTransactionRepository.getByTransactionID(transactionId).orElseThrow();
        return mapper.toOutputDTO(accEntity);
    }

    @Override
    public void updateTransaction(TransactionLedger updatedTransaction) {
        var existingTransaction = accountTransactionRepository.getByTransactionID(updatedTransaction.getTransactionID());
        if (existingTransaction.isPresent()) {
            var accountTransaction = existingTransaction.get();
            accountTransaction.setAmount(BigDecimal.valueOf(updatedTransaction.getFinalReceiveAmount()));
            accountTransaction.setTransactionType(TransactionType.DEBIT);
            accountTransaction.setDescription(updatedTransaction.getRemark());
            accountTransactionRepository.save(accountTransaction);

        }

    }

    @Override
    public void deleteTransaction(TransactionLedger existingTransaction) {
        var transaction = accountTransactionRepository.getByTransactionID(existingTransaction.getTransactionID());
        if (transaction.isPresent()) {
            log.info("Transaction ID exist :: Deleting : {}", transaction.get().getId());
            accountTransactionRepository.delete(transaction.get());
            return;
        }
        log.info("Transaction ID does not exist :: Deleting : {}", existingTransaction.getTransactionID());

    }
}



