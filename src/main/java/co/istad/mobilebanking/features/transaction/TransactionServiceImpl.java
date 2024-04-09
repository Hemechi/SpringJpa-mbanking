package co.istad.mobilebanking.features.transaction;

import co.istad.mobilebanking.domain.Account;
import co.istad.mobilebanking.domain.Transaction;
import co.istad.mobilebanking.features.account.AccountRepository;
import co.istad.mobilebanking.features.transaction.dto.TransactionCreateRequest;
import co.istad.mobilebanking.features.transaction.dto.TransactionResponse;
import co.istad.mobilebanking.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;
    @Override
    public TransactionResponse transfer(TransactionCreateRequest transactionCreateRequest) {
        log.info("transfer(TransactionCreateRequest transactionCreateRequest) : {}", transactionCreateRequest);

        //validate account number
        Account owner = accountRepository.findByActNo(transactionCreateRequest.ownerActNo())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account owner has not been found"
                ));

        //validate transferReceiver account number
        Account transferReceiver = accountRepository.findByActNo(transactionCreateRequest.transferReceiverActNo())
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account Receiver has not been found"
                ));

        //check amount transfer (amount that want to transfer must be <= balance) (act owner only)
        if (owner.getBalance().doubleValue() < transactionCreateRequest.amount().doubleValue()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient balance"
            );
        }

        //check amount transfer limit
        if (owner.getTransferLimit().doubleValue() <= transactionCreateRequest.amount().doubleValue()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Transaction has been over the transfer limit"
            );
        }

        //withdrawal of money from the owner
        owner.setBalance(owner.getBalance().subtract(transactionCreateRequest.amount()));

        //deposit into the account
        transferReceiver.setBalance(transferReceiver.getBalance().add(transactionCreateRequest.amount()));
        Transaction transaction = transactionMapper.fromTransactionCreateRequest(transactionCreateRequest);
        transaction.setOwner(owner);
        transaction.setTransferReceiver(transferReceiver);
        transaction.setTransactionType("TRANSFER");
        transaction.setStatus(true);
        transaction.setTransactionAt(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(transaction);
    }

    @Override
    public Page<TransactionResponse> getTransactionHistory(int page, int size, String sortDirection, String transactionType) {
        // Validate page and size
        if (page < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page number must be greater than or equal to zero"
            );
        }
        if (size < 1) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page size must be greater than or equal to one"
            );
        }

        // Define sorting direction
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
// Define sorting by date
        Sort sort;
        if ("type".equalsIgnoreCase(transactionType)) {
            sort = Sort.by(direction, "transactionType");
        } else {
            sort = Sort.by(direction, "transactionAt");
        }

        // Create page request
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        // Fetch transactions from repository based on transaction type
        Page<Transaction> transactionsPage;
        if ("transfer".equalsIgnoreCase(transactionType)) {
            transactionsPage = transactionRepository.findByTransactionType("TRANSFER", pageRequest);
        } else if ("payment".equalsIgnoreCase(transactionType)) {
            transactionsPage = transactionRepository.findByTransactionType("PAYMENT", pageRequest);
        } else {
            transactionsPage = transactionRepository.findAll(pageRequest);
        }

        // Map transactions to TransactionResponse and return page
        return transactionsPage.map(transactionMapper::toTransactionResponse);
    }
}
