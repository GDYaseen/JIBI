package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.ClientProfessionel;
import com.jibi.back_end.models.CreditCard;
import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.request.ClientProfessionelRequest;
import com.jibi.back_end.services.ClientProfessionelService;

@RestController
@RequestMapping("/api/v1/clientprofessionel")
@AllArgsConstructor
public class ClientProfessionelController {

    private ClientProfessionelService clientProfessionelService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<ClientProfessionel> createClientProfessionel(@RequestBody ClientProfessionelRequest clientProfessionelRequest){
        ClientProfessionel clientProfessionel = clientProfessionelService.getClientProfessionelByPhoneNumber(clientProfessionelRequest.getPhoneNumber());
        if (clientProfessionel != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CreditCard c = CreditCard.builder()
                .cvv(clientProfessionelRequest.getPaymentAccountRequest().getCreditCardRequest().getCvv())
                .cardHolderName(clientProfessionelRequest.getPaymentAccountRequest().getCreditCardRequest().getCardHolderName())
                .cardNumber(clientProfessionelRequest.getPaymentAccountRequest().getCreditCardRequest().getCardNumber())
                .expirationDate(clientProfessionelRequest.getPaymentAccountRequest().getCreditCardRequest().getExpirationDate())
                .billingAddress(clientProfessionelRequest.getPaymentAccountRequest().getCreditCardRequest().getBillingAddress())
                .build();
        System.out.println("Testing the credit card request:"+ c.getCardNumber());
        PaymentAccount p = PaymentAccount.builder()
                .creditCard(c)
                .build();
        c.setAccount(p);

        ClientProfessionel newClientProfessionel = ClientProfessionel.builder()
                .account(p)
                .phoneNumber(clientProfessionelRequest.getPhoneNumber())
                .email(clientProfessionelRequest.getEmail())
                .name(clientProfessionelRequest.getName())
                .carteRecto(clientProfessionelRequest.getCarteRecto())
                .carteVerso(clientProfessionelRequest.getCarteVerso())
                .password(passwordEncoder.encode(clientProfessionelRequest.getPassword()))
                .build();

        p.setClientProfessionel(newClientProfessionel);
        ClientProfessionel createdClientProfessionel = clientProfessionelService.saveClientProfessionel(newClientProfessionel);
        return new ResponseEntity<>(createdClientProfessionel, HttpStatus.OK);
    }

   
}
