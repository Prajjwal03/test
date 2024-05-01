package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import lombok.Data;

@Data
public class CallbackAmendmentHistoryResponseDTO implements Serializable {

    private static final long serialVersionUID = 1139703424110347179L;

    private String callbackDetails;

    private String userName;

    private String action;

    private String dateTime;
}
