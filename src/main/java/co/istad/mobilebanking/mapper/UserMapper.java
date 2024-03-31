package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.User;
import co.istad.mobilebanking.features.user.dto.UserCreateRequest;
import co.istad.mobilebanking.features.user.dto.UserDetailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel="spring")
public interface UserMapper {

    //SourceType = UserCreateRequest (Parameter)
    //TargetType = User (ReturnType)

    User fromUserCreateRequest(UserCreateRequest userCreateRequest);

    //partially update
//    void fromUserCreateRequest2(@MappingTarget User user, UserCreateRequest userCreateRequest);

    UserDetailResponse toUserDetailResponse(User user);

}
