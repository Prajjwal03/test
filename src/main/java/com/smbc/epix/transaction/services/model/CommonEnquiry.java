package com.smbc.epix.transaction.services.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public class CommonEnquiry {
    @Getter
    @Setter
    private List<String> dynamicJoin;
    @Getter
    @Setter
    private List<String> dynamicWhere;
    @Getter
    @Setter
    private List<String> dynamicSearchWhere;
    @Getter
    @Setter
    private String dynamicSelect;
    @Getter
    @Setter
    private int searchParam;
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
    private String branch;
    @Getter
    @Setter
    private String product;
    @Getter
    @Setter
    private String orderBy;
    @Getter
    @Setter
    private int count;
    @Getter
    @Setter
    @JsonProperty("ExtTablename")
    private String extTableName;

}
