package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByActNo(String ActNumber);
}
