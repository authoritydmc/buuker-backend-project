package in.rajlabs.buuker_backend.Buuker.Backend.mapper;

import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.model.AccountTransaction;
import in.rajlabs.buuker_backend.Buuker.Backend.util.AccountUtils;
import org.springframework.stereotype.Component;

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
}
