package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;

public interface AccountService {
    void createNew(AccountCreateRequest accountCreateRequest);
    AccountResponse findByActNo(String actNo);

}
