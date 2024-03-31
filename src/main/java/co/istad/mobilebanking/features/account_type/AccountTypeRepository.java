package co.istad.mobilebanking.features.account_type;

import co.istad.mobilebanking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {
    Boolean existsByAlias(String alias);
}
