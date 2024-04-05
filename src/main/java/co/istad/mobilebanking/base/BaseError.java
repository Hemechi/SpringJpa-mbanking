package co.istad.mobilebanking.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseError<T> {
    //Request entity too large, bad request, ...
    //7003 : custom error code
    private String code;    //code error for developer

    private T description; //Detail error for user
}
