package com.jibi.back_end.mapper;

import org.springframework.beans.BeanUtils;

import com.jibi.back_end.dto.AccountDto;
import com.jibi.back_end.models.PaymentAccount;

public class AccountMapper {
    public static AccountDto mapAccountToAccountDTO(PaymentAccount account){
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account,accountDto);
        return accountDto;
    }
}
