package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.features.user.dto.PasswordEditRequest;
import co.istad.mobilebanking.features.user.dto.UserCreateRequest;
import co.istad.mobilebanking.features.user.dto.UserEditRequest;

public interface UserService {
    void createNew(UserCreateRequest userCreateRequest);
    void editPassword(String oldPassword, PasswordEditRequest request);
    void editUserByUuid(String uuid, UserEditRequest request);
}
