package com.jibi.back_end.Controllers;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.jibi.back_end.models.Agent;
import com.jibi.back_end.request.AgentRequest;
import com.jibi.back_end.request.ChangePassRequest;
import com.jibi.back_end.services.AgentService;
import com.jibi.back_end.services.SMSService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/agent")
@AllArgsConstructor
public class AgentController {

    private AgentService agentService;
    private final PasswordEncoder passwordEncoder;
    private SMSService smsService;

    @PostMapping("/create")
    public ResponseEntity<Agent> createAgent(@RequestBody AgentRequest agentRequest){
        Agent agent = agentService.getAgentByEmail(agentRequest.getEmail());
        System.out.println(agentRequest);
        if (agent != null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] carteRecto=null;
        byte[] carteVerso=null;
        if(agentRequest.getCarteRecto()!=null) carteRecto = Base64.getDecoder().decode(agentRequest.getCarteRecto());
        if(agentRequest.getCarteVerso()!=null) carteVerso = Base64.getDecoder().decode(agentRequest.getCarteVerso());
        Agent newAgent = Agent.builder()
                .phoneNumber(agentRequest.getPhoneNumber())
                .email(agentRequest.getEmail())
                .name(agentRequest.getName())
                .carteRecto(carteRecto)
                .carteVerso(carteVerso)
                .password(passwordEncoder.encode(agentRequest.getPassword()))
                .adresse(agentRequest.getAdresse())
                .cin(agentRequest.getCin())
                .dateNaissance(LocalDateTime.parse(agentRequest.getDateNaissance()))
                .patente(agentRequest.getPatente())
                .immatriculationCommerce(agentRequest.getImmatriculationCommerce())
                .build();

        Agent createdAgent = agentService.saveAgent(newAgent);
        return new ResponseEntity<>(createdAgent, HttpStatus.OK);
    }
    @GetMapping("/{email}")
    public ResponseEntity<Agent> getAgent(@PathVariable String email){
        if(email == null)
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        Agent agent = agentService.getAgentByEmail(email);
        if(agent == null)
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(agent,HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<Agent>> getAllAgents(){
        List<Agent> agents = agentService.getAllAgents();
        return new ResponseEntity<>(agents,HttpStatus.OK);
    }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteAgent(@PathVariable Long id){
        agentService.deleteAgent(id);
        return ResponseEntity.ok("Agent deleted");
   }

   @PutMapping("/changepass/{id}")
   public ResponseEntity<?> changePassword(@RequestBody ChangePassRequest request,@PathVariable Long id){
        Agent agent = agentService.getAgentById(id);
        if(agent==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        if(request.getNewPassword()==null || request.getCode()==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        if(!agent.getResetCodePass().equals(request.getCode())){
            System.out.println(agent.getResetCodePass()+"   "+ request.getCode());
            return new ResponseEntity<>("Wrong code",HttpStatus.BAD_REQUEST);
        }
        agent.setPassword(passwordEncoder.encode(request.getNewPassword()));
        agent.setPasswordChanged(true);
        agent.setResetCodePass(null);
        agentService.saveAgent(agent);
        return new ResponseEntity<Agent>(agent,HttpStatus.OK);
   }
   @PutMapping("/createResetCode/{id}")
   public ResponseEntity<?> createResetCode(@PathVariable Long id){
    Agent agent = agentService.getAgentById(id);
    if(agent==null){
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    String code = String.format("%06d", new Random().nextInt(999999));
    agent.setResetCodePass(code);
    smsService.sendSMS("Hello, your verification code is "+code, agent.getPhoneNumber().replaceFirst("0","212"));
    agentService.saveAgent(agent);
    return new ResponseEntity<Agent>(agent,HttpStatus.OK);
}

   @PutMapping("/modify/{id}")
   public ResponseEntity<Agent> modifyAgent(@RequestBody AgentRequest agentRequest,@PathVariable Long id){
    Agent agent = agentService.getAgentById(id);
        System.out.println(agentRequest);
        if (agent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Agent testAgent = agentService.getAgentByEmail(agentRequest.getEmail());
        if(testAgent!=null && testAgent.getId()!=agent.getId()){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        byte[] carteRecto=null;
        byte[] carteVerso=null;
        if(agentRequest.getCarteRecto()!=null) carteRecto = Base64.getDecoder().decode(agentRequest.getCarteRecto());
        if(agentRequest.getCarteVerso()!=null) carteVerso = Base64.getDecoder().decode(agentRequest.getCarteVerso());
        Agent newAgent = Agent.builder()
                .id(id)
                .phoneNumber(agentRequest.getPhoneNumber())
                .email(agentRequest.getEmail())
                .name(agentRequest.getName())
                .carteRecto(carteRecto)
                .carteVerso(carteVerso)
                .password(passwordEncoder.encode(agentRequest.getPassword()))
                .adresse(agentRequest.getAdresse())
                .cin(agentRequest.getCin())
                .dateNaissance(LocalDateTime.parse(agentRequest.getDateNaissance()))
                .patente(agentRequest.getPatente())
                .immatriculationCommerce(agentRequest.getImmatriculationCommerce())
                .build();

        Agent createdAgent = agentService.saveAgent(newAgent);
        return new ResponseEntity<>(createdAgent, HttpStatus.OK);
   }
}
