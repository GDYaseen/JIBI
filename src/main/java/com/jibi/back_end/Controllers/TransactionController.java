package com.jibi.back_end.Controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.back_end.Enum.TransactionStatus;
import com.jibi.back_end.models.Impaye;
import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.models.Transaction;
import com.jibi.back_end.request.PaymentBodyRequest;
import com.jibi.back_end.services.ImpayeService;
import com.jibi.back_end.services.PaymentAccountService;
import com.jibi.back_end.services.TransactionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/cmi/transaction")
@AllArgsConstructor
public class TransactionController {

    private PaymentAccountService accountService;
    private ImpayeService impayeService;
    private TransactionService transactionService;
    
    @PutMapping("/confirmpay")
    public ResponseEntity<Transaction> confirmPayer(@RequestParam Long id){
        Transaction t = transactionService.getTransactionById(id);
        PaymentAccount p = t.getUser().getAccount();

        if(p.getBalance() < t.getImpaye().getAmount()){
            t.setStatus(TransactionStatus.FAILED);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        
        t.setStatus(TransactionStatus.CONFIRMED);
        p.setBalance(p.getBalance() - t.getImpaye().getAmount());
        
        transactionService.addTransaction(t); //modify
        accountService.addPaymentAccount(p);//modify
        return new ResponseEntity<>(t,HttpStatus.OK);
        
    }
    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody PaymentBodyRequest body){
        if(body.getAccountId() == null || body.getImpayeId() == null) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

        PaymentAccount account = accountService.getPaymentAccountById(body.getAccountId());
        Impaye impaye = impayeService.getImpayeById(body.getImpayeId());
        
        if(account == null || impaye == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        Transaction t = Transaction.builder()
            .amount(impaye.getAmount())
            .impaye(impaye)
            .user(account.getUser())
            .status(TransactionStatus.PENDING)
            .transactionDate(LocalDateTime.now())
            .build();
        
        transactionService.addTransaction(t);
        return new ResponseEntity<Transaction>(t,HttpStatus.OK);
    }
}
