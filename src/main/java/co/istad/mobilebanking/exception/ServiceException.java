package co.istad.mobilebanking.exception;

import co.istad.mobilebanking.base.BaseError;
import co.istad.mobilebanking.base.BasedErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class ServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleServiceErrors(ResponseStatusException ex){
        BaseError<String> baseError = new BaseError<>();
        baseError.setCode(ex.getStatusCode().toString());
        baseError.setDescription(ex.getReason());

        BasedErrorResponse basedErrorResponse = new BasedErrorResponse();
        basedErrorResponse.setError(baseError);

        return ResponseEntity.status(ex.getStatusCode())
                .body(basedErrorResponse);
    }

}
