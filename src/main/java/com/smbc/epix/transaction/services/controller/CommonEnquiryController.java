package com.smbc.epix.transaction.services.controller;

import javax.validation.Valid;

import com.amazonaws.util.json.JSONObject;
import com.smbc.epix.core.utils.Result;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.service.CommonEnquiryService;
import com.smbc.epix.transaction.services.utils.NGLogger;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/enquiry", produces = "application/json")
public class CommonEnquiryController {
    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private CommonEnquiryService commonEnquiryService;

    @ApiOperation(value = "Fetch Data For  Enquiry")
    @PostMapping(path = "/getEnquiryDetails/")
    public ResponseEntity<Result> geCommonEnquiryDetails(@Valid @RequestBody String body) {
        Result result;
        try {
            JSONObject obj = new JSONObject(body);
            JSONObject fields = new JSONObject(obj.getString("field"));
            JSONObject tableDetails = new JSONObject(fields.getString("tableDetails"));
            DynamicTable dynamicQuery = new DynamicTable();
            dynamicQuery.setSearchString1("'" + tableDetails.getString("branchCode") + "'");
            dynamicQuery.setSearchString2("'" + tableDetails.getString("productCode") + "'");
            dynamicQuery.setPageNo(Integer.parseInt(tableDetails.getString("pageNo")));
            dynamicQuery.setPageSize(Integer.parseInt(tableDetails.getString("pageSize")));
            dynamicQuery.setCount(Integer.parseInt(tableDetails.getString("count")));
            dynamicQuery.setOrderBy((tableDetails.getString("orderBy")));
            dynamicQuery.setSortBy((tableDetails.getString("sortBy")));
            dynamicQuery.setPassingValue1(Integer.parseInt(tableDetails.getString("userIndex")));
            dynamicQuery.setSearchDetails(fields);
            result = new Result(commonEnquiryService.getEnquiryData(dynamicQuery));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            ngLogger.consoleLog("exception " + e);
            result = new Result(e);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
