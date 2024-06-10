package com.jibi.back_end.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(CompositeKey.class)
public class Beneficiaire {
    @Id
    private Long accountNumber;
    
    @Id
    private String clientName;
    
    @Id
    private Long userId;
}
