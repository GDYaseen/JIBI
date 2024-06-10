package com.jibi.back_end.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditCard_id", nullable = false)
    @JsonIgnore
    private CreditCard creditCard;
    
    @OneToOne(mappedBy = "account")
    @JsonIgnore
    private Client client;
    @OneToOne(mappedBy = "account")
    @JsonIgnore
    private ClientProfessionel clientProfessionel;
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void initPersist(){
        balance=BigDecimal.ZERO;
        createdAt=LocalDateTime.now();
    }
}
