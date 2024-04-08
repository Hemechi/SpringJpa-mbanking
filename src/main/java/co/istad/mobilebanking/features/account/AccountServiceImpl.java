package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.domain.Account;
import co.istad.mobilebanking.domain.AccountType;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.UserAccount;
import co.istad.mobilebanking.features.account.dto.AccountCreateRequest;
import co.istad.mobilebanking.features.account.dto.AccountRenameRequest;
import co.istad.mobilebanking.features.account.dto.AccountResponse;
import co.istad.mobilebanking.features.account.dto.AccountUpdateTransferLimitRequest;
import co.istad.mobilebanking.features.account_type.AccountTypeRepository;
import co.istad.mobilebanking.features.user.UserRepository;
import co.istad.mobilebanking.mapper.AccountMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public void createNew(AccountCreateRequest accountCreateRequest) {

        // check account type
        AccountType accountType = accountTypeRepository.findByAlias(accountCreateRequest.accountTypeAlias())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Invalid account type"));

        // check user by UUID
        User user = userRepository.findByUuid(accountCreateRequest.userUuid())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "User has not been found"));

        // map account dto to account entity
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
    public AccountResponse findByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account Number is invalid!"
                        ));
//        AccountType accountType = account.getAccountType();
        AccountResponse accountResponse = accountMapper.toAccountResponse(account);
        return accountResponse;
    }

    @Transactional
    @Override
    public void hideAccount(String actNo) {
        if (!accountRepository.existsByActNo(actNo)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account has not found!"
            );
        }
        try {
            accountRepository.hideAccountByActNo(actNo);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong"
            );
        }
    }

    @Override
    public AccountResponse renameByActNo(String actNo, AccountRenameRequest accountRenameRequest){
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account has not found!"
                        ));
        // check alias
        if(account.getAlias().equals(accountRenameRequest.newName()))
        {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "New name must not the name as the name exists!"
            );
        }
        account.setAlias(accountRenameRequest.newName());
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public Page<AccountResponse> findList(int page, int size) {
        //validate page and size
        if (page < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page number must be greater than or equal to zero"
            );
        }
        if (size < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page size must be greater than or equal to one"
            );
        }
        Sort sortByActName = Sort.by(Sort.Direction.ASC, "actName");
        PageRequest pageRequest = PageRequest.of(page, size, sortByActName);
        Page<Account> accounts = accountRepository.findAll(pageRequest);

        return accounts.map(accountMapper::toAccountResponse);
    }

    @Override
    public AccountResponse updateTransferLimit(String actNo, AccountUpdateTransferLimitRequest accountUpdateTransferLimitRequest) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Account has not found!"
                        ));
        account.setTransferLimit(accountUpdateTransferLimitRequest.transferLimit());
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

}
