package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.features.user.dto.PasswordEditRequest;
import co.istad.mobilebanking.features.user.dto.UserCreateRequest;
import co.istad.mobilebanking.features.user.dto.UserEditRequest;
import co.istad.mobilebanking.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public void createNew(UserCreateRequest userCreateRequest) {
        if(userRepository.existsByPhoneNumber(userCreateRequest.phoneNumber())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number has already been existed"
            );
        }
        if(userRepository.existsByNationalCardId(userCreateRequest.nationalCardId())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National card id has already been existed"
            );
        }
        if(userRepository.existsByStudentIdCard(userCreateRequest.studentIdCard())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Student card id has already been existed"
            );
        }
        if (!userCreateRequest.password()
                .equals(userCreateRequest.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password doesn't match!"
            );
        }

        //DTO pattern
        User user = userMapper.fromUserCreateRequest(userCreateRequest);
        user.setUuid(UUID.randomUUID().toString());

        user.setProfileImage("avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);

        //Assign default user role
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER")
                        .orElseThrow(()->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Role User has not been found")
                                );
                        roles.add(userRole);

//         use for dynamic roles , down here is code error
//        Role subscriber = new Role();
//        subscriber.setId(5);
//        subscriber.setName("SUBSCRIBER");
//        roles.add(subscriber);

        user.setRoles(roles);

        userRepository.save(user);
        //auto management transaction: if success, commit. else fail, rollback
    }

    @Override
    public void editPassword(String oldPassword, PasswordEditRequest request) {
        if (!oldPassword.equals(request.password())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Wrong password, Please try again!"
            );
        }
        if (!request.newPassword().equals(request.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password doesn't match!"
            );
        }
        User user = userRepository.findAll().stream().findFirst().orElseThrow();
        user.setPassword(request.newPassword());
        userRepository.save(user);
    }

    @Override
    public void editUserByUuid(String uuid, UserEditRequest request) {
        if (!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User has not been found"
            );
        }
        User user = userRepository.findAll().stream()
                .filter(u -> u.getUuid().equals(uuid))
                .findFirst().orElseThrow();
        user.setCityOrProvince(request.cityOrProvince());
        user.setKhanOrDistrict(request.khanOrDistrict());
        user.setSangkatOrCommune(request.sangkatOrCommune());
        user.setEmployeeType(request.employeeType());
        user.setPosition(request.position());
        user.setCompanyName(request.companyName());
        user.setMainSourceOfIncome(request.mainSourceOfIncome());
        user.setMonthlyIncomeRange(request.monthlyIncomeRange());
        userRepository.save(user);
    }
}
