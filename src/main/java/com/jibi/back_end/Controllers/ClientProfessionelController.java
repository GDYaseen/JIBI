package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.ClientProfessionel;
import com.jibi.back_end.models.CreditCard;
import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.request.ChangePassRequest;
import com.jibi.back_end.request.ClientProfessionelRequest;
import com.jibi.back_end.services.ClientProfessionelService;
import com.jibi.back_end.services.SMSService;

@RestController
@RequestMapping("/api/v1/clientprofessionel")
@AllArgsConstructor
public class ClientProfessionelController {

    private ClientProfessionelService clientProfessionelService;
    private final PasswordEncoder passwordEncoder;
    private SMSService smsService;

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

        byte[] carteRecto=null;
        byte[] carteVerso=null;
        if(clientProfessionelRequest.getCarteRecto()!=null) carteRecto = Base64.getDecoder().decode(clientProfessionelRequest.getCarteRecto());
        if(clientProfessionelRequest.getCarteVerso()!=null) carteVerso = Base64.getDecoder().decode(clientProfessionelRequest.getCarteVerso());
        ClientProfessionel newClientProfessionel = ClientProfessionel.builder()
                .account(p)
                .phoneNumber(clientProfessionelRequest.getPhoneNumber())
                .email(clientProfessionelRequest.getEmail())
                .name(clientProfessionelRequest.getName())
                .carteRecto(carteRecto)
                .carteVerso(carteVerso)
                .password(passwordEncoder.encode(clientProfessionelRequest.getPassword()))
                .build();

        p.setClientProfessionel(newClientProfessionel);
        ClientProfessionel createdClientProfessionel = clientProfessionelService.saveClientProfessionel(newClientProfessionel);
        return new ResponseEntity<>(createdClientProfessionel, HttpStatus.OK);
    }

   @GetMapping("/{email}")
    public ResponseEntity<ClientProfessionel> geClient(@PathVariable String email){
        if(email == null)
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        ClientProfessionel clientProfessionel = clientProfessionelService.getClientProfessionelByEmail(email);
        if(clientProfessionel == null)
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(clientProfessionel,HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<ClientProfessionel>> getAllClients(){
        System.out.println("Accessing /api/v1/client");
        List<ClientProfessionel> clientProfessionels = clientProfessionelService.getAllClientsProfessionel();
        return new ResponseEntity<>(clientProfessionels,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientProfessionel(@PathVariable Long id){
        clientProfessionelService.deleteClientProfessionel(id);
        return ResponseEntity.ok("Client deleted");
    }

    @PutMapping("/changepass/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassRequest request, @PathVariable Long id){
        ClientProfessionel clientProfessionel = clientProfessionelService.getClientProfessionelById(id);
        if(clientProfessionel==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        if(request.getNewPassword()==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        if(!clientProfessionel.getResetCodePass().equals(request.getCode())){
            System.out.println(clientProfessionel.getResetCodePass()+"   "+ request.getCode());
            return new ResponseEntity<>("Wrong code",HttpStatus.BAD_REQUEST);
        }
        clientProfessionel.setPassword(passwordEncoder.encode(request.getNewPassword()));
        clientProfessionel.setPasswordChanged(true);
        
        clientProfessionel.setResetCodePass(null);
        clientProfessionelService.saveClientProfessionel(clientProfessionel);
        return new ResponseEntity<ClientProfessionel>(clientProfessionel,HttpStatus.OK);
    }
    @PutMapping("/createResetCode/{id}")
    public ResponseEntity<?> createResetCode(@PathVariable Long id){
     ClientProfessionel clientProfessionel = clientProfessionelService.getClientProfessionelById(id);
     if(clientProfessionel==null){
         return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
     }
     String code = String.format("%06d", new Random().nextInt(999999));
     clientProfessionel.setResetCodePass(code);
     smsService.sendSMS("Hello, your verification code is "+code, clientProfessionel.getPhoneNumber().replaceFirst("0","212"));
     clientProfessionelService.saveClientProfessionel(clientProfessionel);
     return new ResponseEntity<ClientProfessionel>(clientProfessionel,HttpStatus.OK);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<ClientProfessionel> modifyClient(@RequestBody ClientProfessionelRequest clientProfessionelRequest, @PathVariable Long id){
        ClientProfessionel clientProfessionel = clientProfessionelService.getClientProfessionelById(id);
        System.out.println(clientProfessionelRequest);
        if (clientProfessionel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ClientProfessionel testClientProfessionel = clientProfessionelService.getClientProfessionelByEmail(clientProfessionelRequest.getEmail());
        if(testClientProfessionel!=null && testClientProfessionel.getId()!=clientProfessionel.getId()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        byte[] carteRecto=null;
        byte[] carteVerso=null;
        if(clientProfessionelRequest.getCarteRecto()!=null) carteRecto = Base64.getDecoder().decode(clientProfessionelRequest.getCarteRecto());
        if(clientProfessionelRequest.getCarteVerso()!=null) carteVerso = Base64.getDecoder().decode(clientProfessionelRequest.getCarteVerso());

        clientProfessionel.setPhoneNumber(clientProfessionelRequest.getPhoneNumber());
        clientProfessionel.setName(clientProfessionelRequest.getName());
        clientProfessionel.setCarteRecto(carteRecto);
        clientProfessionel.setCarteVerso(carteVerso);
        clientProfessionel.setPassword(passwordEncoder.encode(clientProfessionelRequest.getPassword()));

        return new ResponseEntity<>(clientProfessionel, HttpStatus.OK);
    }
}
