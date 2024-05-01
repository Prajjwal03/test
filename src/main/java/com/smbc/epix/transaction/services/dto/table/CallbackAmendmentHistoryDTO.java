package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CallbackAmendmentHistoryDTO implements Serializable {

    private static final long serialVersionUID = -5324792181557709253L;

    private int id;

    private String itemIndex;

    private String contractNo;

    private String callBackWaived;

    private String reasonNotRequired;

    private String riskDisclosureChecked;

    private String customerName;

    private Date confirmedDate;

    private String confirmedTime;

    private String confirmedBy;

    private int callbackID;

    private String action;

    private String actionUser;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    private Date actionDateTime;

    private String comments;
}
