package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.Account;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.UserAccount;
import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import co.istad.mobilebanking.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromAccountCreateRequest(AccountCreateRequest accountCreateRequest);

    @Mapping(source = "userAccountList", target = "user", qualifiedByName = "mapUserResponse")
    AccountResponse toAccountResponse(Account account);
    @Named("mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccounts) {
        //your own logic of mapping here...
        return toUserResponse(userAccounts.get(0).getUser());
    }
    UserResponse toUserResponse(User user);

}
