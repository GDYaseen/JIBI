package com.jibi.cmi_service.services.impl;


import com.jibi.cmi_service.repos.PaymentAccountRepository;
import com.jibi.cmi_service.services.PaymentAccountService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional
@AllArgsConstructor
@Service
public class PaymentAccountImpl implements PaymentAccountService {

    private PaymentAccountRepository paymentAccountRepository;

}
