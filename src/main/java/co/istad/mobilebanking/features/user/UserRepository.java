package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalCardId(String nationalCardId);
    boolean existsByStudentIdCard(String studentIDCard);
    boolean existsByUuid(String uuid);

    //Native sql query
//    @Query(value = "SELECT * FROM users WHERE uuid=?1",nativeQuery = true);
//    Optional<User> findByUuid(String uuid);

    //JPQL
    @Query("SELECT u FROM User AS u WHERE u.uuid=:uuid")
    Optional<User> findByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isBlocked = TRUE WHERE u.uuid = ?1")
    void blockByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isBlocked = TRUE WHERE u.uuid = ?1")
    void enableByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isBlocked = FALSE WHERE u.uuid = ?1")
    void disableByUuid(String uuid);

}

