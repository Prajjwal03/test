package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import lombok.Data;

@Data
public class CallbackActionHistoryDTO implements Serializable {

    private static final long serialVersionUID = -3052776652170948858L;

    private String actionUser;
    private String action;
    private String itemIndex;
}
