package com.smbc.epix.transaction.services.dto.table;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class TeamToDOTableDetailsDTO {

    @Getter
    @Setter
    private int count;
    @Getter
    @Setter
    private List<Map> teamToDoDetails;
}
