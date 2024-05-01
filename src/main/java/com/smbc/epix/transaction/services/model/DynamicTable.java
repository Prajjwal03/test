package com.smbc.epix.transaction.services.model;

import com.amazonaws.util.json.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class DynamicTable {
    @Getter
    @Setter
    private JSONObject searchDetails;
    @Getter
    @Setter
    private String listQueueParamQuery;
    @Getter
    @Setter
    private String dynamicDashBoadQuery;
    @Getter
    @Setter
    private String dynamicWhereClauseCase1;
    @Getter
    @Setter
    private String dynamicWhereClauseCase2;
    @Getter
    @Setter
    private int passingValue1;
    @Getter
    @Setter
    private int passingValue2;
    @Getter
    @Setter
    private int passingValue3;
    @Getter
    @Setter
    private String searchString1;
    @Getter
    @Setter
    private String searchString2;
    @Getter
    @Setter
    private String searchString3;
    @Getter
    @Setter
    private int pageSize;
    @Getter
    @Setter
    private int pageNo;
    @Getter
    @Setter
    private String sortBy;
    @Getter
    @Setter
    private String orderBy;
    @Getter
    @Setter
    private int count;

    //Added by Ronit 31-Apr-2021 for Version history
    @Getter
    @Setter
    private String contractNo;
    @Getter
    @Setter
    private String transactionID;

}
