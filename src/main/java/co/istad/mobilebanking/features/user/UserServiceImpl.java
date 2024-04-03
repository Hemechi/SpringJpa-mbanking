package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.base.BasedMessage;
import co.istad.mobilebanking.domain.Role;
import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.features.user.dto.*;
import co.istad.mobilebanking.mapper.UserMapper;
import co.istad.mobilebanking.status.DisableUser;
import co.istad.mobilebanking.status.EnableUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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
        userCreateRequest.roles().forEach(r -> {
            Role newRole = roleRepository.findByName(r.name())
                    .orElseThrow(()-> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Role "+r.name()+" has not been found"
                    ));
            roles.add(newRole);
        });
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

    @Override
    public UserResponse updateByUuid(String uuid, UserUpdateRequest userUpdateRequest) {
        //check uuid if exists
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User hasn't been found"
                        )
                );
        log.info("before user: {}"+user.getPhoneNumber());
        log.info("before user: {}"+user.getName());
        log.info("before user: {}"+user.getGender());
        log.info("before user: {}"+user.getDob());
        log.info("before user: {}"+user.getStudentIdCard());

        userMapper.fromUserUpdateRequest(userUpdateRequest,user);
        log.info("after user: {}"+user.getName());
        log.info("after user: {}"+user.getGender());
        log.info("after user: {}"+user.getDob());
        log.info("after user: {}"+user.getStudentIdCard());
        user = userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserDetailResponse getAllUserByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User hasn't been found"
                        ));
        return userMapper.toUserDetailResponse(user);
    }

    @Transactional
    @Override
    public BasedMessage blockByUuid(String uuid) {

        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User has not been found!");
        }

        userRepository.blockByUuid(uuid);

        return new BasedMessage("User has been blocked");
    }

    @Override
    public void deleteUserByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User uuid has been not found!"
                        ));
        userRepository.delete(user);
    }
    @Override
    @Transactional
    public DisableUser disableUser(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User is enable"
            );
        }
        userRepository.disableByUuid(uuid);
        return new DisableUser("User is disable");

    }
    @Override
    @Transactional
    public EnableUser enableUser(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User is enable"
            );
        }
        userRepository.enableByUuid(uuid);
        return new EnableUser("User is enable");
    }

    @Override
    public Page<UserResponse> findList(int page, int limit) {
        //create pageRequest object
        PageRequest pageRequest=PageRequest.of(page,limit);
        //invoke findAll(pageRequest)
        Page<User> users=userRepository.findAll(pageRequest);
        //Map result of pagination to response
        return users.map(userMapper::toUserResponse);
    }
}
