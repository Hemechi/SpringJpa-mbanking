package co.istad.mobilebanking.features.account_type;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/account-types")
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> findAllAccountType(){
        return ResponseEntity.accepted().body(
                Map.of(
                        "data", accountTypeService.findAccountType()
                )
        );
    }

    @GetMapping("/{alias}")
    ResponseEntity<?> findAccountTypeByAlias(@PathVariable String alias){
        return ResponseEntity.accepted().body(
                Map.of(
                        "data", accountTypeService.findAccountTypeByAlias(alias)
                )
        );
    }
}
