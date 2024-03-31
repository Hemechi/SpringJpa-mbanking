package co.istad.mobilebanking.features.account_type;

import co.istad.mobilebanking.features.account_type.dto.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findAccountType();
    AccountTypeResponse findAccountTypeByAlias(String alias);
}
