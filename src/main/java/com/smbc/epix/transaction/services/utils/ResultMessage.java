package com.smbc.epix.transaction.services.utils;

import lombok.Getter;
import lombok.Setter;

public class ResultMessage {
    @Getter
    @Setter
    private String messageCode;
    @Getter
    @Setter
    private String fieldName;

    public ResultMessage(String messageCode) {
        this.messageCode = messageCode;
    }

    public ResultMessage(String messageCode, String fieldName) {
        this.messageCode = messageCode;
        this.fieldName = fieldName;
    }
}