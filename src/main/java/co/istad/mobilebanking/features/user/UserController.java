package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.base.BasedMessage;
import co.istad.mobilebanking.features.user.dto.*;
import co.istad.mobilebanking.status.DisableUser;
import co.istad.mobilebanking.status.EnableUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody UserCreateRequest request){
        userService.createNew(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/change-password/{oldPassword}")
    void editPassword(@PathVariable String oldPassword, @RequestBody PasswordEditRequest request){
        userService.editPassword(oldPassword, request);
    }

    @PatchMapping("uuid/{uuid}")
    void editUserByUuid(@PathVariable String uuid, @RequestBody UserEditRequest request){
        userService.editUserByUuid(uuid,request);
    }
    @PatchMapping("/{uuid}")
    UserResponse updateByUuid(@PathVariable String uuid, @RequestBody UserUpdateRequest userUpdateRequest){
        return userService.updateByUuid(uuid,userUpdateRequest);
    }
    @GetMapping
    public Page<UserResponse> findList(@RequestParam(value = "page", required = false, defaultValue = "0")int page,
                                       @RequestParam(value = "limit", required = false, defaultValue = "2")int limit){
        return userService.findList(page,limit);
    }
    @GetMapping("/{uuid}")
    UserDetailResponse getAllUserByUuid(@PathVariable String uuid){
        return userService.getAllUserByUuid(uuid);
    }
    @PutMapping("/{uuid}/block")
    BasedMessage blockByUuid(@PathVariable String uuid){
        return userService.blockByUuid(uuid);
    }
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserByUuid(@PathVariable String uuid){
        userService.deleteUserByUuid(uuid);
    }
    @PutMapping("/{uuid}/disable")
    DisableUser disableUser(@PathVariable String uuid){
        return userService.disableUser(uuid);
    }
    @PutMapping("/{uuid}/enable")
    EnableUser enableUser(@PathVariable String uuid){
        return userService.enableUser(uuid);
    }

}
