package co.istad.mobilebanking.features.init;

import co.istad.mobilebanking.domain.AccountType;
import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.features.account_type.AccountTypeRepository;
import co.istad.mobilebanking.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    @PostConstruct
    void initRole(){
        if (roleRepository.count() < 1) {
            //Auto generated role (USER, CUSTOMER, STAFF, ADMIN)
            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role staff = new Role();
            staff.setName("STAFF");

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.saveAll(
                    List.of(user,customer,staff,admin)
            );
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
