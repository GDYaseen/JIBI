package com.jibi.back_end.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Agent;
import com.jibi.back_end.repos.AgentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@AllArgsConstructor
@Service
public class AgentService {

    private AgentRepository agentRepository;

    public Agent saveAgent(Agent agent) {
        return this.agentRepository.save(agent);
    }

    public void deleteAgent(Long id){
        this.agentRepository.deleteById(id);
    }

    public Agent getAgentById(Long id) {
        return this.agentRepository.findById(id).orElse(null);
    }

    public List<Agent> getAllAgents() {
        return this.agentRepository.findAll();
    }

    public Agent getAgentByEmail(String email) {
        return this.agentRepository.findByEmail(email)
                .orElse(null);
    }
}
