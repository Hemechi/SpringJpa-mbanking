package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.Account;
import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AccountMapper {
    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);
    AccountResponse toAccountCreateRequest(Account account);
}
