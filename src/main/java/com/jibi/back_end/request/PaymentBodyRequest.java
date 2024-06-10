package com.jibi.back_end.request;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentBodyRequest {

    //Selon l'usage de la transaction, si c'est envoyer a une personne, laissez impayId null. Sinon, laissez receiverPhone null.
    private String receiverPhone;
    private String senderPhone;
    private BigDecimal amount;
    private Long impayeId;
}
