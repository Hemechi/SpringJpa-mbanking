package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.base.BasedMessage;
import co.istad.mobilebanking.features.user.dto.*;
import co.istad.mobilebanking.status.DisableUser;
import co.istad.mobilebanking.status.EnableUser;
import org.springframework.data.domain.Page;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);
    void editPassword(String oldPassword, PasswordEditRequest request);
    void editUserByUuid(String uuid, UserEditRequest request);
    UserResponse updateByUuid(String uuid, UserUpdateRequest userUpdateRequest);
    UserDetailResponse getAllUserByUuid(String uuid);
    BasedMessage blockByUuid(String uuid);
    void deleteUserByUuid(String uuid);
    DisableUser disableUser(String uuid);
    EnableUser enableUser(String uuid);
    Page<UserResponse> findList(int page, int limit);

}
