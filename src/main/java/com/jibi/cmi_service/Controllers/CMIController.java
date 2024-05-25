package com.jibi.cmi_service.Controllers;

import com.jibi.cmi_service.models.CreditCard;
import com.jibi.cmi_service.models.PaymentAccount;
import com.jibi.cmi_service.models.User;
import com.jibi.cmi_service.request.UserRequest;
import com.jibi.cmi_service.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cmi")
@AllArgsConstructor
public class CMIController {

    private UserService userService;

    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest){
        User user = userService.getUserByPhoneNumber(userRequest.getPhoneNumber());
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CreditCard c = CreditCard.builder()
                .cvv(userRequest.getPaymentAccountRequest().getCreditCardRequest().getCvv())
                .cardHolderName(userRequest.getPaymentAccountRequest().getCreditCardRequest().getCardHolderName())
                .cardNumber(userRequest.getPaymentAccountRequest().getCreditCardRequest().getCardNumber())
                .expirationDate(userRequest.getPaymentAccountRequest().getCreditCardRequest().getExpirationDate())
                .billingAddress(userRequest.getPaymentAccountRequest().getCreditCardRequest().getBillingAddress())
                .build();
        System.out.println("Testing the credit card request:"+ c.getCardNumber());
        PaymentAccount p = PaymentAccount.builder()
                .creditCard(c)
                .build();
        c.setAccount(p);

        User newUser = User.builder()
                .phoneNumber(userRequest.getPhoneNumber())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .account(p)
                .build();
        p.setUser(newUser);
        User createdUser = userService.addUser(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
