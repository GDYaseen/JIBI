package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.Agent;
import com.jibi.back_end.request.AgentRequest;
import com.jibi.back_end.services.AgentService;

@RestController
@RequestMapping("/api/v1/agent")
@AllArgsConstructor
public class AgentController {

    private AgentService agentService;

    @PostMapping("/create")
    public ResponseEntity<Agent> createAgent(@RequestBody AgentRequest agentRequest){
        Agent agent = agentService.getAgentByEmail(agentRequest.getEmail());
        if (agent != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Agent newAgent = Agent.builder()
                .phoneNumber(agentRequest.getPhoneNumber())
                .email(agentRequest.getEmail())
                .name(agentRequest.getName())
                .carteRecto(agentRequest.getCarteRecto())
                .carteVerso(agentRequest.getCarteVerso())
                .password("12345")
                .adresse(agentRequest.getAdresse())
                .cin(agentRequest.getCin())
                .dateNaissance(LocalDateTime.parse(agentRequest.getDateNaissance()))
                .patente(agentRequest.getPatente())
                .immatriculationCommerce(agentRequest.getImmatriculationCommerce())
                .build();

        Agent createdAgent = agentService.addAgent(newAgent);
        return new ResponseEntity<>(createdAgent, HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<Agent> getAgent(@RequestParam String email){
        if(email == null)
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        Agent agent = agentService.getAgentByEmail(email);
        if(agent == null)
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(agent,HttpStatus.OK);
    }

   
}
