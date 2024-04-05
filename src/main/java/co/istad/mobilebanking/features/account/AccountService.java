package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import co.istad.mobilebanking.features.user.dto.UserCreateRequest;

public interface AccountService {
    void createNew(AccountCreateRequest accountCreateRequest);
    AccountResponse findAccountByActNumber(String accountNumber);

}
