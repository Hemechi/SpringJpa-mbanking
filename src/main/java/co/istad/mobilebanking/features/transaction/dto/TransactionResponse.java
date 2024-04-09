package co.istad.mobilebanking.features.transaction.dto;

import co.istad.mobilebanking.features.account.dto.AccountSnippetResponse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        AccountSnippetResponse owner,
        AccountSnippetResponse transferReceiver,
        BigDecimal amount,
        String remark,
        String transactionType,
        Boolean status,
        LocalDate transactionAt

) {
}
