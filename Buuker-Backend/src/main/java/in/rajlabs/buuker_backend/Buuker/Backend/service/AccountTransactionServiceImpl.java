package in.rajlabs.buuker_backend.Buuker.Backend.service;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.mapper.AccountTransactionMapper;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import in.rajlabs.buuker_backend.Buuker.Backend.repository.AccountTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<AccountTransactionDTO> getTransactionsByAccountId(String accountId) {
        return accountTransactionRepository.findByAccountId(accountId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<AccountTransactionDTO> getTransactionsByAccountIdAndType(String accountId, TransactionType transactionType) {
        return accountTransactionRepository.findByAccountIdAndTransactionType(accountId, transactionType).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void saveDebitTransaction(TransactionLedger transaction) {

        var accountTransaction = mapper.toEntity(transaction);
        accountTransactionRepository.save(accountTransaction);
    }


}
