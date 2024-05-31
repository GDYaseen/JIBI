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

    public Agent addAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    public void deleteAgentById(Long id) {
        agentRepository.deleteById(id);
    }

    public Agent getAgentById(Long id) {
        return agentRepository.findById(id).orElse(null);
    }

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Agent getAgentByEmail(String email) {
        return agentRepository.findByEmail(email)
                .orElse(null);
    }
}
