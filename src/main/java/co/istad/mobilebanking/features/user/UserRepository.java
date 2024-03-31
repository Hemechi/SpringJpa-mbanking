package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalCardId(String nationalCardId);
    boolean existsByStudentIdCard(String studentIDCard);
    boolean existsByUuid(String uuid);
}
