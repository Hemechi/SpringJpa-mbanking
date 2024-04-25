package co.istad.mobilebanking.features.init;

import co.istad.mobilebanking.domain.AccountType;
import co.istad.mobilebanking.domain.Authority;
import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.features.account_type.AccountTypeRepository;
import co.istad.mobilebanking.features.user.AuthorityRepository;
import co.istad.mobilebanking.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final AccountTypeRepository accountTypeRepository;
    @PostConstruct
    void initRole(){

        if (authorityRepository.count() < 1) {
            Authority userRead = new Authority();
            userRead.setName("user:read");
            Authority userWrite = new Authority();
            userWrite.setName("user:write");
            Authority transactionRead = new Authority();
            transactionRead.setName("transaction:read");
            Authority transactionWrite = new Authority();
            transactionWrite.setName("transaction:write");
            Authority accountRead = new Authority();
            accountRead.setName("account:read");
            Authority accountWrite = new Authority();
            accountWrite.setName("account:write");
            Authority accountTypeRead = new Authority();
            accountTypeRead.setName("accountType:read");
            Authority accountTypeWrite = new Authority();
            accountTypeWrite.setName("accountType:write");

            //Auto generated role (USER, CUSTOMER, STAFF, ADMIN)
            Role user = new Role();
            user.setName("USER");
            user.setAuthorities(List.of(
                    userRead, transactionRead,
                    accountRead, accountTypeRead
            ));


            Role customer = new Role();
            customer.setName("CUSTOMER");
            customer.setAuthorities(List.of(
                    userWrite, transactionWrite,
                    accountWrite
            ));

            Role staff = new Role();
            staff.setName("STAFF");
            staff.setAuthorities(List.of(
                    accountTypeWrite
            ));

            Role admin = new Role();
            admin.setName("ADMIN");
            admin.setAuthorities(List.of(
                    userWrite, accountWrite,
                    accountTypeWrite
            ));


            roleRepository.saveAll(List.of(
                    user, customer,staff,admin
            ));
        }
    }

    void initAccountType() {
        AccountType savingActType = new AccountType();
        savingActType.setName("Saving Account");
        savingActType.setAlias("saving-account");
        savingActType.setDescription("A saving account is a deposit account");
        accountTypeRepository.save(savingActType);

        AccountType payrollActType = new AccountType();
        payrollActType.setName("Payroll Account");
        payrollActType.setAlias("payroll-account");
        payrollActType.setDescription("A payroll account is a checking account");
    }
}
