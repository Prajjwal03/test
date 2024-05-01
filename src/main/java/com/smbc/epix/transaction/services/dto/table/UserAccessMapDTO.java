package com.smbc.epix.transaction.services.dto.table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class UserAccessMapDTO {
    @Getter
    @Setter
    private String branchCode;
    @Getter
    @Setter
    private String productCode;
    @Getter
    @Setter
    private String txnTypeCode;
}
