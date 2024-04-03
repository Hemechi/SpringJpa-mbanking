package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.AccountType;
import co.istad.mobilebanking.features.account_type.dto.AccountTypeResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);

    List<AccountTypeResponse> toAccountTypeResponseList(List<AccountType> accountTypes);
}
