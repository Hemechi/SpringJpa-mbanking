package co.istad.mobilebanking.mapper;

import co.istad.mobilebanking.domain.Transaction;
import co.istad.mobilebanking.domain.UserAccount;
import co.istad.mobilebanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mobilebanking.features.transaction.dto.TransactionResponse;
import co.istad.mobilebanking.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface TransactionMapper {
    TransactionResponse toTransactionResponse(Transaction transaction);

    Transaction fromTransactionCreateRequest(TransactionCreateRequest transactionCreateRequest);

}
