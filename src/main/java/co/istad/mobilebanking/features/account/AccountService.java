package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountRenameRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import co.istad.mobilebanking.features.account.dto.AccountUpdateTransferLimitRequest;
import org.springframework.data.domain.Page;

public interface AccountService {
    void createNew(AccountCreateRequest accountCreateRequest);
    AccountResponse findByActNo(String actNo);
    void hideAccount(String actNo);
    AccountResponse renameByActNo(String actNo, AccountRenameRequest accountRenameRequest);
    Page<AccountResponse> findList(int page, int size);
    AccountResponse updateTransferLimit(String actNo, AccountUpdateTransferLimitRequest accountUpdateTransferLimitRequest);

}
