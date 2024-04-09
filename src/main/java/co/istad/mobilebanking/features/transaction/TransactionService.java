package co.istad.mobilebanking.features.transaction;

import co.istad.mobilebanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mobilebanking.features.transaction.dto.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {

    TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest);
    Page<TransactionResponse> getTransactionHistory(int page, int size, String sortDirection, String transactionType);

}
