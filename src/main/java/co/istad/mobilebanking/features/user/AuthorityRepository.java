package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
}
