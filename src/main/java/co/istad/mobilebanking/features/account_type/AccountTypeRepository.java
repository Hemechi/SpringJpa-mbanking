package co.istad.mobilebanking.features.account_type;

import co.istad.mobilebanking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {
    Boolean existsByAlias(String alias);
    Optional<AccountType> findByAlias(String alias);
}
