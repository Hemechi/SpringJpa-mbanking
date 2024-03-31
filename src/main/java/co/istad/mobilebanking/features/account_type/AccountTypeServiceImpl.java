package co.istad.mobilebanking.features.account_type;

import co.istad.mobilebanking.features.account_type.dto.AccountTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{
    private final AccountTypeRepository accountTypeRepository;
    public List<AccountTypeResponse> findAccountType() {
        return accountTypeRepository.findAll()
                .stream().map(
                        accountType -> new AccountTypeResponse(
                                accountType.getAlias(),
                                accountType.getName(),
                                accountType.getDescription(),
                                accountType.getIsDeleted()
                        )
                ).collect(Collectors.toList());
    }

    @Override
    public AccountTypeResponse findAccountTypeByAlias(String alias) {
        if (!accountTypeRepository.existsByAlias(alias)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account type doesn't exist"
            );
        }
        return accountTypeRepository.findAll()
                .stream()
                .filter(accountType -> accountType.getAlias().equals(alias))
                .map(accountType -> new AccountTypeResponse(
                        accountType.getAlias(),
                        accountType.getName(),
                        accountType.getDescription(),
                        accountType.getIsDeleted()
                ))
                .findFirst().orElseThrow();
    }
}
