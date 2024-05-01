package com.smbc.epix.transaction.services.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class WatchListDetails implements Serializable {

    private static final long serialVersionUID = -1973696972447903458L;

    private String transactionRefNo;

    private String createdBy;
}
