package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.domain.UserAccount;
import co.istad.mobilebanking.features.user.dto.UserCreateRequest;
import co.istad.mobilebanking.features.user.dto.UserDetailResponse;
import co.istad.mobilebanking.features.user.dto.UserResponse;
import co.istad.mobilebanking.features.user.dto.UserUpdateRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper {

    //SourceType = UserCreateRequest (Parameter)
    //TargetType = User (ReturnType)

    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    //partially update
//    void fromUserCreateRequest2(@MappingTarget User user, UserCreateRequest userCreateRequest);

    UserDetailResponse toUserDetailResponse(User user);

    //to map partially, info that doesn't update won't be null
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    UserResponse toUserResponse(User user);
    @Named("mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccounts) {
        //your own logic of mapping here...
        return toUserResponse(userAccounts.get(0).getUser());
    }
}
