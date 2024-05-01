package com.smbc.epix.transaction.services.dto.table;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UtilizationHistoryDTO {

    private int recordID;
    private String ccy1;
    private String ccy2;
    private BigDecimal ccy1UtilizationAmount;
    private BigDecimal ccy2UtilizationAmount;
    private String transactionRefNo;
    private String department;
    private String utilizationStatus;
    private String inputBuyAmount;
    private int inputBuySellAmount;
    private String action;
    private String actionUser;
    private Date actionDateTime;
    private String comments;
}
