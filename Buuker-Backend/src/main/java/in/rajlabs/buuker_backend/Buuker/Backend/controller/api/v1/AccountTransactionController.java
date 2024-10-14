package in.rajlabs.buuker_backend.Buuker.Backend.controller.api.v1;

import in.rajlabs.buuker_backend.Buuker.Backend.controller.api.API;
import in.rajlabs.buuker_backend.Buuker.Backend.dto.AccountTransactionDTO;
import in.rajlabs.buuker_backend.Buuker.Backend.service.AccountTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(API.API_VERSION_V1 + "account-transactions")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    public AccountTransactionController(AccountTransactionService accountTransactionService) {
        this.accountTransactionService = accountTransactionService;
    }





}
