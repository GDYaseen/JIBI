package com.jibi.cmi_service.mapper;

import com.jibi.cmi_service.dto.AccountDto;
import com.jibi.cmi_service.models.PaymentAccount;
import org.springframework.beans.BeanUtils;

public class AccountMapper {
    public static AccountDto mapAccountToAccountDTO(PaymentAccount account){
        AccountDto accountDto = new AccountDto();
        BeanUtils.copyProperties(account,accountDto);
        return accountDto;
    }
}
