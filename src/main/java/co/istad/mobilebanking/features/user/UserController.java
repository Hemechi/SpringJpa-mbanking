package co.istad.mobilebanking.features.user;

import co.istad.mobilebanking.features.user.dto.PasswordEditRequest;
import co.istad.mobilebanking.features.user.dto.UserCreateRequest;
import co.istad.mobilebanking.features.user.dto.UserEditRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
