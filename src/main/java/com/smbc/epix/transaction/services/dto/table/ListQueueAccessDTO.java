package com.smbc.epix.transaction.services.dto.table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class ListQueueAccessDTO {

    @Getter
    @Setter
    private String createdBy;
    @Getter
    @Setter
    private String qUserID;
    @Getter
    @Setter
    private String lockStatus;

}
