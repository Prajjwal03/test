package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SwapHistory implements Serializable {
    private static final long serialVersionUID = -7546748608344327374L;

    @JsonProperty("Contract_No")
    private String contractNo;

    @JsonProperty("TransactionID")
    private String transactionID;

    private String versionNumber;

    @JsonProperty("ProcessInstanceID")
    private String processInstanceID;

    @JsonProperty("LockStatus")
    private String lockStatus;

    @JsonProperty("PackageNo")
    private String packageNo;
}
