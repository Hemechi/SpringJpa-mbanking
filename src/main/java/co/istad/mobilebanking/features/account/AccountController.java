package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import co.istad.mobilebanking.features.account.dto.AccountRenameRequest;
import co.istad.mobilebanking.features.account.dto.AccountUpdateTransferLimitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.createNew(accountCreateRequest);
    }
    //HW, when response, need to have user and accounttype , every associate resource
    @GetMapping("/{actNo}")
    AccountResponse findAccountByActNo (@PathVariable String actNo){
        return accountService.findByActNo(actNo);
    }

    @PutMapping("/{actNo}/rename")
    AccountResponse renameByActNo(@PathVariable String actNo,
                                  @Valid @RequestBody AccountRenameRequest accountRenameRequest) {
        return accountService.renameByActNo(actNo,accountRenameRequest );
    }

    @PutMapping("/{actNo}/hide")
    void hideAccount(@PathVariable String actNo) {
        accountService.hideAccount(actNo);
    }
    @GetMapping
    Page<AccountResponse> findList(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "2") int size
    ){
        return accountService.findList(page, size);
    }
    @PutMapping("/{actNo}/updateLimit")
    AccountResponse updateTransferLimit(@PathVariable String actNo,@Valid @RequestBody AccountUpdateTransferLimitRequest accountUpdateTransferLimitRequest){
        return accountService.updateTransferLimit(actNo,accountUpdateTransferLimitRequest);
    }

}
