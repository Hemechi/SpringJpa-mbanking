package co.istad.mobilebanking.features.transaction;

import co.istad.mobilebanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mobilebanking.features.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    TransactionResponse transfer(@Valid @RequestBody TransactionCreateRequest
                                         transactionCreateRequest) {
        return transactionService.transfer(transactionCreateRequest);
    }
    //can't transfer to own account,
    //HW, show up of all transaction history and sort all transaction by date
    //(bonus, filter, can be ASC or DSC) (bonus,filter,can be transaction or payment and if they didn't pick any, take both of it)
    //pagination (default: page=0, size=25)

    @GetMapping
    Page<TransactionResponse> getTransactionHistory(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "25") int size,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) String transactionType
    ){
        return transactionService.getTransactionHistory(page, size, sortDirection, transactionType);
    }
}
