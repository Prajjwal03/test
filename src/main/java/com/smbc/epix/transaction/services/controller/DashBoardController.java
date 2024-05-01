package com.smbc.epix.transaction.services.controller;

import javax.validation.Valid;

import com.amazonaws.util.json.JSONObject;
import com.smbc.epix.core.utils.Result;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.service.DashBoardService;
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
@RequestMapping(path = "/dashBoard", produces = "application/json")
public class DashBoardController {
    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private DashBoardService dashBoardService;

    @ApiOperation(value = "Get To-DO List")
    @PostMapping(path = "/toDO/")
    public ResponseEntity<Result> getToDoList(@Valid @RequestBody String body) {
        Result result;
        ngLogger.consoleLog("request body " + body);
        try {
            JSONObject obj = new JSONObject(body);
            JSONObject fields = new JSONObject(obj.getString("field"));
            JSONObject tableDetails = new JSONObject(fields.getString("tableDetails"));
            DynamicTable dynamicQuery = new DynamicTable();
            dynamicQuery.setSearchString1("'" + tableDetails.getString("branchCode") + "'");
            dynamicQuery.setSearchString2("'" + tableDetails.getString("productCode") + "'");
            dynamicQuery.setSearchString3("'" + tableDetails.getString("tableName") + "'");
            dynamicQuery.setPageNo(Integer.parseInt(tableDetails.getString("pageNo")));
            dynamicQuery.setPageSize(Integer.parseInt(tableDetails.getString("pageSize")));
            dynamicQuery.setCount(Integer.parseInt(tableDetails.getString("count")));
            dynamicQuery.setOrderBy((tableDetails.getString("orderBy")));
            dynamicQuery.setSortBy((tableDetails.getString("sortBy")));
            dynamicQuery.setPassingValue1(Integer.parseInt(tableDetails.getString("userIndex")));
            dynamicQuery.setSearchDetails(fields);

            result = new Result(dashBoardService.getDashBoardDetailsCommon(dynamicQuery));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            ngLogger.consoleLog("Exception " + e);
            result = new Result(e);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

    }

    @ApiOperation(value = "Get MyWatch List")
    @PostMapping(path = "/myWacthList/")
    public ResponseEntity<Result> getMyWatchList(@Valid @RequestBody String body) {
        Result result;
        try {
            JSONObject obj = new JSONObject(body);
            JSONObject fields = new JSONObject(obj.getString("field"));
            JSONObject tableDetails = new JSONObject(fields.getString("tableDetails"));
            DynamicTable dynamicQuery = new DynamicTable();
            dynamicQuery.setSearchString1("'" + tableDetails.getString("branchCode") + "'");
            dynamicQuery.setSearchString2("'" + tableDetails.getString("productCode") + "'");
            dynamicQuery.setSearchString3("'" + tableDetails.getString("tableName") + "'");
            dynamicQuery.setPageNo(Integer.parseInt(tableDetails.getString("pageNo")));
            dynamicQuery.setPageSize(Integer.parseInt(tableDetails.getString("pageSize")));
            dynamicQuery.setCount(Integer.parseInt(tableDetails.getString("count")));

            if (dynamicQuery.getSearchString2().equalsIgnoreCase("'VS'")) {
                dynamicQuery.setOrderBy((tableDetails.getString("orderBy")));
            } else {
                if (dynamicQuery.getSearchString2().equalsIgnoreCase("'DFX'")) {
                    dynamicQuery.setOrderBy("Value_Date_To");
                }
            }
            dynamicQuery.setSortBy((tableDetails.getString("sortBy")));
            dynamicQuery.setPassingValue1(Integer.parseInt(tableDetails.getString("userIndex")));
            dynamicQuery.setSearchDetails(fields);
            result = new Result(dashBoardService.getMyWatchList(dynamicQuery));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            result = new Result(e);
            ngLogger.consoleLog("Exception " + e);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
