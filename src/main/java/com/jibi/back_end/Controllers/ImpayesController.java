package com.jibi.back_end.Controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jibi.back_end.Enum.ImpayeType;
import com.jibi.back_end.dto.MappedTransaction;
import com.jibi.back_end.models.Creance;
import com.jibi.back_end.models.Client;
import com.jibi.back_end.models.Impaye;
import com.jibi.back_end.request.ImpayeRequest;
import com.jibi.back_end.services.ClientService;
import com.jibi.back_end.services.CreanceService;
import com.jibi.back_end.services.ImpayeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/cmi/impaye")
@AllArgsConstructor
public class ImpayesController {
    private ImpayeService impayeService;
    private CreanceService creanceService;
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<Impaye> createImpaye(@RequestBody ImpayeRequest body){
        if(body.getSenderPhone() == null || body.getCreanceId() == null) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

        Client sender = clientService.getClientByPhoneNumber(body.getSenderPhone());
        Creance creance = creanceService.getCreance(body.getCreanceId());
        if(sender == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

        Impaye impaye = Impaye.builder()
                .client(sender)
                .creance(creance)
                .amount(body.getAmount())
                .type(ImpayeType.valueOf(body.getImpayeType()))
                .dueDate(LocalDateTime.now())
                .build();

        Impaye savedImpaye = impayeService.saveImpaye(impaye);
        return new ResponseEntity<Impaye>(savedImpaye, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<MappedTransaction>> getImpayes(@RequestParam Long userId,@RequestParam Long creanceId){
        List<MappedTransaction> list = impayeService.getImpayesByClientIdAndImpayeId(userId, creanceId);
        return new ResponseEntity<List<MappedTransaction>>(list,HttpStatus.OK);
    }
}
