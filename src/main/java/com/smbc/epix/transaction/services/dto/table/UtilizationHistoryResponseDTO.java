package com.smbc.epix.transaction.services.dto.table;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UtilizationHistoryResponseDTO implements Serializable{

    private static final long serialVersionUID = 5615246540105438061L;

    private String utlizationDetails;

    private String userName;

    private String action;

    private String dateTime;
}
