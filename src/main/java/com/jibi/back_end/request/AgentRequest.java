package com.jibi.back_end.request;

import lombok.Data;

@Data
public class AgentRequest {
    private String phoneNumber;
    private String email;
    private String name;
    private String cin;
    private String adresse;
    private String dateNaissance;
    private String password;
    private String immatriculationCommerce;
    private String patente;
    private byte[] carteRecto;
    private byte[] carteVerso;

}
