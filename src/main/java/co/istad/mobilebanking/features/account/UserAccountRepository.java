package co.istad.mobilebanking.features.account;

import co.istad.mobilebanking.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
