package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VersionHistoryDTO implements Serializable {

    private static final long serialVersionUID = -5160533706347130609L;

    @JsonProperty("Contract_No")
    private String contractNo;

    @JsonProperty("LockStatus")
    private String lockStatus;

    @JsonProperty("ProcessInstanceID")
    private String processInstanceID;

    private String versionNumber;

    @JsonProperty("TransactionID")
    private String transactionID;
}
