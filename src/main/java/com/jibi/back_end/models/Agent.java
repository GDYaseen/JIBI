package com.jibi.back_end.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
public class Agent extends User{

    private String password;
    private boolean passwordChanged;
    private String cin;
    private String adresse;
    private LocalDateTime dateNaissance;
    private String immatriculationCommerce;
    private String patente;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] carteRecto;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] carteVerso;

    @PrePersist
    public void initPersist(){
        passwordChanged=false;
    }

    @Builder
    public Agent(Long id, String phoneNumber, String name, String email,
                  LocalDateTime createdAt, LocalDateTime updatedAt,
                  String password,boolean passwordChanged,
                  String cin,String adresse,LocalDateTime dateNaissance,
                  String immatriculationCommerce,String patente,
                  byte[] carteRecto,byte[]carteVerso) {
        super(id, phoneNumber, name, email, null,createdAt, updatedAt);
        this.password=password;
        this.passwordChanged=passwordChanged;
        this.carteRecto=carteRecto;
        this.carteVerso=carteVerso;
        this.cin = cin;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.immatriculationCommerce = immatriculationCommerce;
        this.patente = patente;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Agent.class.getName()));
    }
}
