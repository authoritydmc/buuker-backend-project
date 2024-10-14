package in.rajlabs.buuker_backend.Buuker.Backend.mapper;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionOutputDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionLedger;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import in.rajlabs.buuker_backend.Buuker.Backend.util.AccountUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Mapper class to convert between AccountTransaction and AccountTransactionDTO.
 */
@Component
public class AccountTransactionMapper {

    /**
     * Converts AccountTransactionDTO to AccountTransaction entity.
     *
     * @param dto The DTO containing transaction details.
     * @return The AccountTransaction entity.
     */
    public AccountTransaction toEntity(AccountTransactionDTO dto) {
        if (dto == null) {
            return null;
        }

        AccountTransaction entity = AccountTransaction.builder()
                .transactionType(dto.getTransactionType())
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .accountId(AccountUtils.getAccountID(dto.getCustomerID(), dto.getMerchantID()))
                .createdBy("TBD")
                .updatedBy("SET THIS VAL FROM CONTROLLER BY PARSING authentication in future ")
                .createdOn(dto.getCreatedOn())
                .updatedOn(dto.getUpdatedOn())
                // createdOn and updatedOn are handled by @PrePersist and @PreUpdate
                .build();

        return entity;
    }

    /**
     * Converts AccountTransaction entity to AccountTransactionDTO.
     *
     * @param entity The AccountTransaction entity.
     * @return The AccountTransactionDTO.
     */
    public AccountTransactionDTO toDTO(AccountTransaction entity) {
        if (entity == null) {
            return null;
        }

        return AccountTransactionDTO.builder()
                .transactionType(entity.getTransactionType())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .build();
    }

    public AccountTransaction toEntity(TransactionLedger transaction) {
        if (transaction == null) {
            return null;
        }

        AccountTransaction entity = AccountTransaction.builder()
                .id(UUID.randomUUID())
                .transactionType(AccountUtils.getTransactionTypeFromOrderStatus(transaction.getOrderStatus()))
                .transactionId(transaction.getTransactionID())
                .amount(BigDecimal.valueOf(transaction.getFinalReceiveAmount()))
                .description(transaction.getRemark())
                .accountId(AccountUtils.getAccountID(transaction.getCustomerID(), transaction.getMerchantID()))
                .createdBy(transaction.getCustomerID())
                .updatedBy(transaction.getCustomerID())
                .updatedOn(transaction.getUpdatedOn())
                .createdOn(transaction.getCreatedOn())
                .build();

        return entity;

    }

    public AccountTransactionOutputDTO toOutputDTO(AccountTransaction entity) {
        if (entity == null) {
            return null;
        }

        return AccountTransactionOutputDTO.builder()
                .transactionType(entity.getTransactionType())
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .id(entity.getId())
                .transactionId(entity.getTransactionId())
                .accountId(entity.getAccountId())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdOn(entity.getCreatedOn())
                .updatedOn(entity.getUpdatedOn())
                .build();

    }
}
