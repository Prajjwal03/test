package com.smbc.epix.transaction.services.dto.table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class ListQueueParamsDTO {

    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String param;
}
