package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.Client;
import com.jibi.back_end.models.CreditCard;
import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.request.ClientRequest;
import com.jibi.back_end.services.ClientService;

@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<Client> createClient(@RequestBody ClientRequest clientRequest){
        Client client = clientService.getClientByPhoneNumber(clientRequest.getPhoneNumber());
        if (client != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CreditCard c = CreditCard.builder()
                .cvv(clientRequest.getPaymentAccountRequest().getCreditCardRequest().getCvv())
                .cardHolderName(clientRequest.getPaymentAccountRequest().getCreditCardRequest().getCardHolderName())
                .cardNumber(clientRequest.getPaymentAccountRequest().getCreditCardRequest().getCardNumber())
                .expirationDate(clientRequest.getPaymentAccountRequest().getCreditCardRequest().getExpirationDate())
                .billingAddress(clientRequest.getPaymentAccountRequest().getCreditCardRequest().getBillingAddress())
                .build();
        System.out.println("Testing the credit card request:"+ c.getCardNumber());
        PaymentAccount p = PaymentAccount.builder()
                .creditCard(c)
                .build();
        c.setAccount(p);

        Client newClient = Client.builder()
                .account(p)
                .phoneNumber(clientRequest.getPhoneNumber())
                .email(clientRequest.getEmail())
                .name(clientRequest.getName())
                .carteRecto(clientRequest.getCarteRecto())
                .carteVerso(clientRequest.getCarteVerso())
                .password(passwordEncoder.encode(clientRequest.getPassword()))
                .build();

        p.setClient(newClient);
        Client createdClient = clientService.saveClient(newClient);
        return new ResponseEntity<>(createdClient, HttpStatus.OK);
    }

   
}
