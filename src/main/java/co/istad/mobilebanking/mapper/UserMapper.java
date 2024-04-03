package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.features.user.dto.UserCreateRequest;
import co.istad.mobilebanking.features.user.dto.UserDetailResponse;
import co.istad.mobilebanking.features.user.dto.UserResponse;
import co.istad.mobilebanking.features.user.dto.UserUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

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
}
