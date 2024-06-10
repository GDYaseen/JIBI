package com.jibi.back_end.Controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.back_end.Controllers.response.TransactionResponse;
import com.jibi.back_end.Enum.TransactionStatus;
import com.jibi.back_end.dto.UserDto;
import com.jibi.back_end.mapper.UserMapper;
import com.jibi.back_end.models.Client;
import com.jibi.back_end.models.ClientProfessionel;
import com.jibi.back_end.models.Impaye;
import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.models.Transaction;
import com.jibi.back_end.models.User;
import com.jibi.back_end.request.PaymentBodyRequest;
import com.jibi.back_end.services.ClientProfessionelService;
import com.jibi.back_end.services.ClientService;
import com.jibi.back_end.services.ImpayeService;
import com.jibi.back_end.services.PaymentAccountService;
import com.jibi.back_end.services.SMSService;
import com.jibi.back_end.services.TransactionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/cmi/transaction")
@AllArgsConstructor
public class TransactionController {

    private final ClientService clientService;
    private final ClientProfessionelService clientProfessionelService;
    private PaymentAccountService accountService;
    private TransactionService transactionService;
    private ImpayeService impayeService;
    private SMSService smsService;
    @GetMapping("/confirmpay")
    public ResponseEntity<Transaction> confirmPayer(@RequestParam Long id){
        Transaction t = transactionService.getTransactionById(id);
        PaymentAccount p = null;
        
         if (t.getSender() instanceof Client) {
            p = ((Client) t.getSender()).getAccount();
         } else if (t.getSender() instanceof ClientProfessionel) {
            p = ((ClientProfessionel) t.getSender()).getAccount();
         }
         if (p == null || p.getBalance().compareTo(t.getAmount()) < 0) {
            t.setStatus(TransactionStatus.FAILED);
            transactionService.saveTransaction(t); // Ensure the failed status is saved
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        
        t.setStatus(TransactionStatus.CONFIRMED);
        p.setBalance(p.getBalance().subtract(t.getAmount()));
        
        transactionService.saveTransaction(t); //modify
        accountService.savePaymentAccount(p);//modify
        return new ResponseEntity<>(t,HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody PaymentBodyRequest body){
        if(body.getSenderPhone() == null || (body.getReceiverPhone() == null && body.getImpayeId()==null)) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

        User sender = findUserByPhoneNumber(body.getSenderPhone());
    if (sender == null) {
        return new ResponseEntity<>("{\"message\":\"Sender not found\"}", HttpStatus.NOT_FOUND);
    }

    User receiver = null;
    if (body.getReceiverPhone() != null) {
        receiver = findUserByPhoneNumber(body.getReceiverPhone());
    }
    
        Impaye impaye = null;
        if(body.getImpayeId()!=null) impaye = impayeService.getImpayeById(body.getImpayeId());

        if(sender == null || (receiver == null && impaye==null)) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        
        Transaction t = Transaction.builder()
            .amount(body.getAmount())
            .sender(sender)
            .receiver(receiver)
            .impaye(impaye)
            .status(TransactionStatus.PENDING)
            .transactionDate(LocalDateTime.now())
            .build();
        transactionService.saveTransaction(t);
        
        smsService.sendSMS("Hello, please confirm your transaction of "+t.getAmount()+" DH - http://localhost:8009/api/v1/cmi/transaction/confirmPay?id="+t.getId(), t.getSender().getPhoneNumber().replaceFirst("0","212"));
        return new ResponseEntity<Transaction>(t,HttpStatus.OK);
    }

    @GetMapping("/{phone}")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions(@PathVariable String phone){
        List<Transaction> transactions = transactionService.getAllTransactions(phone);

        List<TransactionResponse> response = new ArrayList<TransactionResponse>();
        for(Transaction t: transactions){
            UserDto senderDTO = UserMapper.mapUserToUserDTO(t.getSender());
            UserDto receiverDTO = UserMapper.mapUserToUserDTO(t.getReceiver());
            response.add(new TransactionResponse(t, senderDTO, receiverDTO));
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/past7months/{id}")
    public ResponseEntity<?> pastSevenMonths(@PathVariable Long id){
        if(id==null) return new ResponseEntity<String>("{\"message\":\"No Id Provided\"}",HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(transactionService.getClientProfTransactionsByMonth(id),HttpStatus.OK);
    }
    private User findUserByPhoneNumber(String phoneNumber) {
        User user = clientService.getClientByPhoneNumber(phoneNumber);
        if (user == null) {
            user = clientProfessionelService.getClientProfessionelByPhoneNumber(phoneNumber);
        }
        return user;
    }

}
