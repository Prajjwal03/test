package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import lombok.Data;

@Data
public class CallbackStatus implements Serializable {

    private static final long serialVersionUID = -4173802653281308666L;

    private String utilizationStatus;
    private String transactionStatus;
    private String itemIndex;
}
