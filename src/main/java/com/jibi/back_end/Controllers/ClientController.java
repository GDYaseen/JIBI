package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.Client;
import com.jibi.back_end.models.CreditCard;
import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.request.ClientRequest;
import com.jibi.back_end.request.ChangePassRequest;
import com.jibi.back_end.services.ClientService;
import com.jibi.back_end.services.SMSService;

@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private SMSService smsService;

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


        byte[] carteRecto=null;
        byte[] carteVerso=null;
        if(clientRequest.getCarteRecto()!=null) carteRecto = Base64.getDecoder().decode(clientRequest.getCarteRecto());
        if(clientRequest.getCarteVerso()!=null) carteVerso = Base64.getDecoder().decode(clientRequest.getCarteVerso());
        Client newClient = Client.builder()
                .account(p)
                .phoneNumber(clientRequest.getPhoneNumber())
                .email(clientRequest.getEmail())
                .name(clientRequest.getName())
                .carteRecto(carteRecto)
                .carteVerso(carteVerso)
                .password(passwordEncoder.encode(clientRequest.getPassword()))
                .build();

        p.setClient(newClient);
        Client createdClient = clientService.saveClient(newClient);
        return new ResponseEntity<>(createdClient, HttpStatus.OK);
    }

    @GetMapping("/{phone}")
    public ResponseEntity<Client> getClient(@PathVariable String phone){
        if(phone == null)
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        Client client = clientService.getClientByPhoneNumber(phone);
        if(client == null)
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(client,HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Client>> getAllClients(){
        System.out.println("Accessing /api/v1/client");
        List<Client> clients = clientService.getAllClients();
        return new ResponseEntity<>(clients,HttpStatus.OK);
    }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteClient(@PathVariable Long id){
    clientService.deleteClient(id);
        return ResponseEntity.ok("Client deleted");
   }

   @PutMapping("/changepass/{id}")
   public ResponseEntity<?> changePassword(@RequestBody ChangePassRequest request,@PathVariable Long id){
    System.out.println("inside /changepass/{id}");
        Client client = clientService.getClientById(id);
        if(client==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        if(request.getNewPassword()==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        if(!client.getResetCodePass().equals(request.getCode())){
            System.out.println(client.getResetCodePass()+"   "+ request.getCode());
            return new ResponseEntity<>("Wrong code",HttpStatus.BAD_REQUEST);
        }
        client.setPassword(passwordEncoder.encode(request.getNewPassword()));
        client.setPasswordChanged(true);
        client.setResetCodePass(null);
        clientService.saveClient(client);

        return new ResponseEntity<Client>(client,HttpStatus.OK);
   }

    @PutMapping("/createResetCode/{id}")
    public ResponseEntity<?> createResetCode(@PathVariable Long id){
     Client client = clientService.getClientById(id);
     if(client==null){
         return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
     }
     String code = String.format("%06d", new Random().nextInt(999999));
     client.setResetCodePass(code);
     smsService.sendSMS("Hello, your verification code is "+code, client.getPhoneNumber().replaceFirst("0","212"));
     clientService.saveClient(client);
     return new ResponseEntity<Client>(client,HttpStatus.OK);
    }

   @PutMapping("/modify/{id}")
   public ResponseEntity<Client> modifyClient(@RequestBody ClientRequest clientRequest,@PathVariable Long id){
    Client client = clientService.getClientById(id);
        System.out.println(clientRequest);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client testClient = clientService.getClientByEmail(clientRequest.getEmail());
        if(testClient!=null && testClient.getId()!=client.getId()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        byte[] carteRecto=null;
        byte[] carteVerso=null;
        if(clientRequest.getCarteRecto()!=null) carteRecto = Base64.getDecoder().decode(clientRequest.getCarteRecto());
        if(clientRequest.getCarteVerso()!=null) carteVerso = Base64.getDecoder().decode(clientRequest.getCarteVerso());
        
        client.setPhoneNumber(clientRequest.getPhoneNumber());
        client.setName(clientRequest.getName());
        client.setCarteRecto(carteRecto);
        client.setCarteVerso(carteVerso);
        client.setPassword(passwordEncoder.encode(clientRequest.getPassword()));

        return new ResponseEntity<>(client, HttpStatus.OK);
   }
}
