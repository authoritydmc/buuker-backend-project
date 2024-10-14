package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.exception.ResourceNotFoundException;
import in.rajlabs.buuker_backend.Buuker.Backend.mapper.AccountTransactionMapper;
import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import in.rajlabs.buuker_backend.Buuker.Backend.repository.AccountTransactionRepository;
import in.rajlabs.buuker_backend.Buuker.Backend.util.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        accountTransaction.setRunningBalance(calculateRunningBalance(accountTransaction));
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
        //TODO: Implement this
    }

    @Override
    public void saveDebitTransaction(TransactionLedger transaction) {

        AccountTransaction accountTransaction = mapper.toEntity(transaction);
        accountTransaction.setRunningBalance(calculateRunningBalance(accountTransaction));

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
            accountTransaction.setTransactionType(AccountUtils.getTransactionTypeFromOrderStatus(updatedTransaction.getOrderStatus()));
            accountTransaction.setRunningBalance(calculateRunningBalance(accountTransaction));
            accountTransaction.setDescription(updatedTransaction.getRemark());
            accountTransactionRepository.save(accountTransaction);

        }

    }

    private BigDecimal calculateRunningBalance(AccountTransaction accountTransaction) {
        log.info("Calculating running balance for account " + accountTransaction.getAccountId() + "at " + accountTransaction.getId());
        Optional<AccountTransaction> latestTransaction = accountTransactionRepository.getPreviousAccountTransaction(accountTransaction.getUpdatedOn(), accountTransaction.getAccountId(), accountTransaction.getId());
        BigDecimal runningBalance = BigDecimal.ZERO;
        if (latestTransaction.isPresent()) {
            runningBalance = latestTransaction.get().getRunningBalance();
        }
        log.info("Initial  running balance is {}", runningBalance);

        if (accountTransaction.getTransactionType().equals(TransactionType.DEBIT)) {
            runningBalance = runningBalance.subtract(accountTransaction.getAmount());
        } else {
            //this is credit
            runningBalance = runningBalance.add(accountTransaction.getAmount());

        }
        log.info("Final  running balance is " + runningBalance);

        //before returning also update the running balance of other items
        List<AccountTransaction> idsToUpdate = accountTransactionRepository.getAllAccountsToUpdate(accountTransaction.getUpdatedOn(), accountTransaction.getAccountId(), accountTransaction.getId());
        updateRunningBalance(idsToUpdate, runningBalance);
        return runningBalance;
    }



    @Override
    public void deleteTransaction(TransactionLedger existingTransaction) {
        var transaction = accountTransactionRepository.getByTransactionID(existingTransaction.getTransactionID());
        if (transaction.isPresent()) {
            log.info("Transaction ID exist :: Deleting : {}", transaction.get().getId());
            //update running balance
            updateRunningBalanceForRecordDeletion(transaction.get());
            accountTransactionRepository.delete(transaction.get());
            return;
        }
        log.info("Transaction ID does not exist :: Deleting : {}", existingTransaction.getTransactionID());

    }

    private void updateRunningBalanceForRecordDeletion(AccountTransaction accountTransaction) {
        log.info("Calculating running balance for account " + accountTransaction.getAccountId());
        Optional<AccountTransaction> latestTransaction = accountTransactionRepository.getPreviousAccountTransaction(accountTransaction.getUpdatedOn(), accountTransaction.getAccountId(), accountTransaction.getId());
        BigDecimal runningBalance = BigDecimal.ZERO;
        if (latestTransaction.isPresent()) {
            runningBalance = latestTransaction.get().getRunningBalance();
        }
        log.info("Initial  running balance is {}", runningBalance);
        //before returning also update the running balance of other items
        List<AccountTransaction> idsToUpdate = accountTransactionRepository.getAllAccountsToUpdate(accountTransaction.getUpdatedOn(), accountTransaction.getAccountId(), accountTransaction.getId());
        updateRunningBalance(idsToUpdate, runningBalance);
    }


    /**
     * Updates the running balance for all transactions associated with the given account ID.
     *
     * @param accountTransactionsToUpdate LIST of transactions where we need to do the update
     * @param currentBalance              The current balance at the update/insertion point
     */
    private void updateRunningBalance(List<AccountTransaction> accountTransactionsToUpdate, BigDecimal currentBalance) {
        log.info("Total " + accountTransactionsToUpdate.size() + " account transactions need to be updated.");
        log.info("Initial Current balance is " + currentBalance);

        BigDecimal runningBalance = currentBalance; // Start from the current balance

        // Calculate the new running balance
        for (AccountTransaction transaction : accountTransactionsToUpdate) {
            // Update running balance based on transaction type
            if (transaction.getTransactionType() == TransactionType.CREDIT) {
                runningBalance = runningBalance.add(transaction.getAmount());
            } else if (transaction.getTransactionType() == TransactionType.DEBIT) {
                runningBalance = runningBalance.subtract(transaction.getAmount());
            }

            // Set the calculated running balance for the transaction
            transaction.setRunningBalance(runningBalance);

            // Save the updated transaction to the repository
            accountTransactionRepository.save(transaction);
        }
    }


}



