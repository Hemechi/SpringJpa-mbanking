package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.createNew(accountCreateRequest);
    }
    //HW, when response, need to have user and accounttype , every associate resource
    @GetMapping("{actNo}")
    AccountResponse findAccountByActNo (@PathVariable String actNo){
        return  accountService.findAccountByActNumber(actNo);
    }
}