package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomDashboardDataCurrency implements Serializable {
    private static final long serialVersionUID = 6299127107395303193L;

    @JsonProperty("sell_side_currency")
    private String sellSideCurrency;

    @JsonProperty("Department")
    private String department;

    @JsonProperty("PendingTreasuryBack")
    private int pendingTreasuryBack;

    @JsonProperty("PendingOperationMaker")
    private int pendingOperationMaker;

    @JsonProperty("PendingOperationChecker")
    private int pendingOperationChecker;

    @JsonProperty("UtilizationCompleted")
    private int utilizationCompleted;
}
