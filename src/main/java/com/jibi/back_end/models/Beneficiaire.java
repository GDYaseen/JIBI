package com.jibi.back_end.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Beneficiaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String clientName;

    private Long userId;

    public Beneficiaire(String clientName, String phoneNumber, Long userId){
        this.phoneNumber = phoneNumber;
        this.clientName = clientName;
        this.userId = userId;
    }
}
