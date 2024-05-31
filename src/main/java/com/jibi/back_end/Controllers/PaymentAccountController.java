package com.jibi.back_end.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.back_end.dto.AccountDto;
import com.jibi.back_end.mapper.AccountMapper;
import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.services.PaymentAccountService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/cmi/account")
@AllArgsConstructor
public class PaymentAccountController {

    private PaymentAccountService accountService;

    @GetMapping("")
    public ResponseEntity<AccountDto> getSolde(@RequestParam Long id){
        PaymentAccount account = accountService.getPaymentAccountByClientId(id);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccountDto>(AccountMapper.mapAccountToAccountDTO(account),HttpStatus.OK);
    }

    
}
