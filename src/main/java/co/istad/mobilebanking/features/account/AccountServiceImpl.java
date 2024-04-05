package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.domain.Account;
import co.istad.mobilebanking.domain.AccountType;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.UserAccount;
import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import co.istad.mobilebanking.features.account_type.AccountTypeRepository;
import co.istad.mobilebanking.features.account_type.dto.AccountTypeResponse;
import co.istad.mobilebanking.features.user.UserRepository;
import co.istad.mobilebanking.mapper.AccountMapper;
import co.istad.mobilebanking.mapper.AccountTypeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final UserAccountRepository userAccountRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountMapper accountMapper;
    private final AccountTypeMapper accountTypeMapper;
    @Override
    public void createNew(AccountCreateRequest accountCreateRequest) {
        //check account type
        AccountType accountType = accountTypeRepository.findByAlias(accountCreateRequest.alias())
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Invalid account type"
                        ));
        //check user by uuid
        User user = userRepository.findByUuid(accountCreateRequest.userUuid())
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found"
                        ));
        //map account dto to entity
        Account account = accountMapper.fromAccountCreateRequest(accountCreateRequest);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo("123456789");
        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setIsHidden(false);

        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setUser(user);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccountRepository.save(userAccount);

    }
    @Override
    public AccountResponse findAccountByActNumber(String accountNumber) {
        Account account = accountRepository.findAccountByActNo(accountNumber);
        AccountType accountType = account.getAccountType();
        AccountTypeResponse accountTypeResponse = accountTypeMapper.toAccountTypeResponse(accountType);
        AccountResponse accountResponse = new AccountResponse(account.getActName(), account.getAlias(),
                account.getBalance(), accountTypeResponse);
        return accountResponse;
    }
}
