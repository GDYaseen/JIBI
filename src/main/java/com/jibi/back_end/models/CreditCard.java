package com.jibi.back_end.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "creditCard")
    @JsonIgnore
    private PaymentAccount account;

    @Column(nullable = false)
    private String cardNumber; // Should be stored encrypted

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private String expirationDate;

    @Column(nullable = false)
    private String cvv; // Should be stored encrypted

    private String billingAddress;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
