package com.jibi.back_end.models;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Client extends User{
    

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private PaymentAccount account;

    private String password;
    private boolean passwordChanged;
    private Double solde;
    @Lob
    private byte[] carteRecto;
    @Lob
    private byte[] carteVerso;

    @PrePersist
    public void initPersist(){
        passwordChanged=false;
        solde=0D;
    }

    @Builder
    public Client(Long id, String phoneNumber, String name, String email,
                  LocalDateTime createdAt, LocalDateTime updatedAt,
                  PaymentAccount account,String password,boolean passwordChanged,Double solde,byte[] carteRecto,byte[]carteVerso) {
        super(id, phoneNumber, name, email, createdAt, updatedAt);
        this.account = account;
        this.password=password;
        this.passwordChanged=passwordChanged;
        this.carteRecto=carteRecto;
        this.carteVerso=carteVerso;
    }
}
