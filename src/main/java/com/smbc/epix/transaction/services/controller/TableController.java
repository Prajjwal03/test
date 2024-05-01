package com.smbc.epix.transaction.services.controller;

import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.amazonaws.util.json.JSONObject;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.service.TableService;
import com.smbc.epix.transaction.services.utils.NGLogger;
import com.smbc.epix.transaction.services.utils.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/table", produces = "application/json")
public class TableController {
    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private TableService tableService;

    /**
     * Get list of application and its url
     *
     * @return list of {@link com.smbc.epix.transaction.services.dto.table.ListApplicationDTO}
     */
    @ApiOperation(value = "Fetch all application details")
    @GetMapping(path = "/applications")
    public ResponseEntity<Result> getApplicationList() {
        ngLogger.consoleLog("Attempting to fetch list of application");
        Result result = new Result(tableService.getApplication());
        ngLogger.consoleLog("Successfully fetched list of application: " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Data For Fields For ToDo Table")
    @GetMapping(path = "/toDoTableFields/")
    public ResponseEntity<Result> getToDoTableFields(@RequestParam(value = "branchCode") String branchCode,
                                                     @RequestParam(value = "productCode") String productCode,
                                                     @RequestParam(value = "userIndex") int userIndex) {
        DynamicTable dynamicQuery = new DynamicTable();
        dynamicQuery.setSearchString1("'" + branchCode + "'");
        dynamicQuery.setSearchString2("'" + productCode + "'");
        dynamicQuery.setPassingValue1(userIndex);
        Result result = new Result(tableService.getToDoTableFields(dynamicQuery));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Data For Fields For ToDo Column Table")
    @GetMapping(path = "/toDoColumnTableFields/")
    public ResponseEntity<Result> getToDoColumnTableFields(@RequestParam(value = "branchCode") String branchCode,
                                                           @RequestParam(value = "productCode") String productCode,
                                                           @RequestParam(value = "userIndex") int userIndex) {
        DynamicTable dynamicQuery = new DynamicTable();
        dynamicQuery.setSearchString1("'" + branchCode + "'");
        dynamicQuery.setSearchString2("'" + productCode + "'");
        dynamicQuery.setPassingValue1(userIndex);
        Result result = new Result(tableService.getToDoColumnTableFields(dynamicQuery));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Data For Fields For ToDo Table")
    @GetMapping(path = "/enquiryTableFields/")
    public ResponseEntity<Result> getEnquiryTableFields(@RequestParam(value = "branchCode") String branchCode,
                                                        @RequestParam(value = "productCode") String productCode,
                                                        @RequestParam(value = "userIndex") int userIndex) {
        DynamicTable dynamicQuery = new DynamicTable();
        dynamicQuery.setSearchString1("'" + branchCode + "'");
        dynamicQuery.setSearchString2("'" + productCode + "'");
        dynamicQuery.setPassingValue1(userIndex);
        Result result = new Result(tableService.getEnquiryTableFields(dynamicQuery));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Data For Fields For ToDo Column Table")
    @GetMapping(path = "/enquiryColumnTableFields/")
    public ResponseEntity<Result> getEnquiryColumnTableFields(@RequestParam(value = "branchCode") String branchCode,
                                                              @RequestParam(value = "productCode") String productCode,
                                                              @RequestParam(value = "userIndex") int userIndex) {
        DynamicTable dynamicQuery = new DynamicTable();
        dynamicQuery.setSearchString1("'" + branchCode + "'");
        dynamicQuery.setSearchString2("'" + productCode + "'");
        dynamicQuery.setPassingValue1(userIndex);
        Result result = new Result(tableService.getEnquiryColumnTableFields(dynamicQuery));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Data DropDown")
    @GetMapping(path = "/dropDownValues/")
    public ResponseEntity<Result> getDropDownValues(@RequestParam(value = "tableName") String tableName,
                                                    @RequestParam(value = "columnName") String columnName) {
        DynamicTable dt = new DynamicTable();
        dt.setSearchString1(tableName);
        dt.setSearchString2(columnName);
        Result result = new Result(tableService.getDropDownFields(dt));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Branch Based on UserIndex")
    @GetMapping(path = "/getBranch")
    public ResponseEntity<Result> getBranch(@RequestParam(value = "userIndex") int userIndex) {
        Result result = new Result(tableService.getBranch(userIndex));
        ngLogger.consoleLog("Branch " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch UserName Based on UserIndex")
    @GetMapping(path = "/getUserName")
    public ResponseEntity<Result> getUserName(@RequestParam(value = "userIndex") int userIndex) {
        Result result = new Result(tableService.getUserName(userIndex));
        ngLogger.consoleLog("Branch " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Product Based on UserIndex and Branch")
    @GetMapping(path = "/getProduct")
    public ResponseEntity<Result> getProduct(@RequestParam(value = "userIndex") int userIndex,
                                             @RequestParam(value = "branchName") String branchName) {
        DynamicTable dt = new DynamicTable();
        dt.setPassingValue1(userIndex);
        dt.setSearchString1("'" + branchName + "'");
        Result result = new Result(tableService.getProduct(dt));
        ngLogger.consoleLog("Product " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Enquiry Search Column")
    @GetMapping(path = "/getEnquiryColumn")
    public ResponseEntity<Result> getEnquiry(@RequestParam(value = "branchCode") String branchCode,
                                             @RequestParam(value = "productCode") String productCode) {
        DynamicTable dt = new DynamicTable();
        dt.setSearchString1("'" + branchCode + "'");
        dt.setSearchString2("'" + productCode + "'");
        Result result = new Result(tableService.getEnquirySearchFilter(dt));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Data DropDown with parent")
    @GetMapping(path = "/getDropDownValuesForTransactionType/")
    public ResponseEntity<Result> getDropDownValuesForTransactionType(@RequestParam(value = "userIndex") int userIndex,
                                                                      @RequestParam(value = "branchCode") String branchCode,
                                                                      @RequestParam(value = "productCode") String productCode) {
        DynamicTable dt = new DynamicTable();
        dt.setPassingValue1(userIndex);
        dt.setSearchString1("'" + branchCode + "'");
        dt.setSearchString2("'" + productCode + "'");
        Result result = new Result(tableService.getDropDownFieldsForTransactionType(dt));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Fetch Data DropDown For Case status")
    @GetMapping(path = "/getCaseStatus/")
    public ResponseEntity<Result> getCaseStatus(@RequestParam(value = "branchCode") String branchCode,
                                                @RequestParam(value = "productCode") String productCode) {

        DynamicTable dt = new DynamicTable();
        dt.setSearchString1("'" + branchCode + "'");
        dt.setSearchString2("'" + productCode + "'");
        Result result = new Result(tableService.getCaseStatus(dt));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Create Auth")
    @GetMapping(path = "/getCreateAuth/")
    public ResponseEntity<Result> getCreateAuth(@RequestParam("userIndex") int userIndex) {
        Result result = new Result(tableService.getCreateAuth(userIndex));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Role Name")
    @GetMapping(path = "/getRoleName/")
    public ResponseEntity<Result> getRoleName(@RequestParam("userIndex") int userIndex) {
        Result result = new Result(tableService.getRoleName(userIndex));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Comment History")
    @GetMapping(path = "/getCommentHistory/")
    public ResponseEntity<Result> getCommonHistory(@RequestParam("transactionID") String transactionID, @RequestParam("randomId") double randomId) {
        Result result = new Result();
        if (transactionID.contains("DFX")) {
            result = new Result(tableService.getCommentHistoryDfx(transactionID));
        } else if (transactionID.contains("VS")) {
            result = new Result(tableService.getCommentHistory(transactionID));
        } else {
            ngLogger.errorLog("getCommentHistory -  Invalid Product code: " + transactionID);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Comment History")
    @GetMapping(path = "/getUserRoleDetails/")
    public ResponseEntity<Result> getUserRoleDetails(@RequestParam("userIndex") int userIndex) {
        Result result = new Result(tableService.getUserRoleDetails(userIndex));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Fetch the Callback Amendment History.
     *
     * @param pid
     * @return
     */
    @ApiOperation(value = "Get Callback Amendment History")
    @GetMapping(path = "/callbackAmendmentHistory/")
    public ResponseEntity<Result> callbackAmendmentHistory(@RequestParam("pid") String pid) {
        Result result = new Result(tableService.callbackAmendmentHistory(pid));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Fetch the Utilization History.
     *
     * @param pid
     * @return
     */
    @ApiOperation(value = "Get Utilization History")
    @GetMapping(path = "/utilizationHistory/")
    public ResponseEntity<Result> utilizationHistory(@RequestParam("pid") String pid) {
        Result result = new Result(tableService.getUtilizationHistory(pid));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get User Session")
    @PostMapping(path = "/getSessionStatus/")
    public ResponseEntity<Result> getSessionStatus(@Valid @RequestBody String body) {
        Result result;
        try {
            JSONObject obj = new JSONObject(body);
            JSONObject fields = new JSONObject(obj.getString("field"));
            DynamicTable dt = new DynamicTable();
            dt.setPassingValue1(Integer.parseInt(fields.getString("randomNumber")));
            dt.setPassingValue2(Integer.parseInt(fields.getString("userIndex")));
            List<Map> list = tableService.getSessionStatus(dt);
            if (!CollectionUtils.isEmpty(list)) {
                result = new Result("true");
            } else {
                result = new Result("false");
            }

        } catch (Exception e) {
            result = new Result(e);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get the Iform based on Product")
    @GetMapping(path = "/getIformData")
    public ResponseEntity<Result> getIformDetails(@RequestParam(value = "productCode") String productCode) {
        Result result = new Result(tableService.getIformDetails(productCode));
        ngLogger.consoleLog("TAble " + result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Version History")
    @GetMapping(path = "/getVersionHistory/")
    public ResponseEntity<Result> getVersionHistory(@RequestParam("contractNo") String contractNo, @RequestParam("transactionID") String transactionID) {
        DynamicTable versionQuery = new DynamicTable();
        versionQuery.setContractNo("'" + contractNo + "'");
        versionQuery.setTransactionID("'" + transactionID + "'");
        Result result = new Result(tableService.getVersionHistory(versionQuery));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Swap History")
    @GetMapping(path = "/getSwapHistory/")
    public ResponseEntity<Result> getSwapHistory(@RequestParam("contractNo") String contractNo, @RequestParam("transactionID") String transactionID) {
        DynamicTable versionQuery = new DynamicTable();
        versionQuery.setContractNo("'" + contractNo + "'");
        versionQuery.setTransactionID("'" + transactionID + "'");
        Result result = new Result(tableService.getSwapHistory(versionQuery));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Custom Dashboard Data")
    @GetMapping(path = "/getCustomDashboardDataSubProduct")
    public ResponseEntity<Result> getCustomDashboardDataSubProduct() {
        DynamicTable dashboardQuery = new DynamicTable();
        Result result = new Result(tableService.getCustomDashboardDataSubProduct(dashboardQuery));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Custom Dashboard Data")
    @GetMapping(path = "/getCustomDashboardDataCurrency")
    public ResponseEntity<Result> getCustomDashboardDataCurrency() {
        DynamicTable dynamicTable = new DynamicTable();
        Result result = new Result(tableService.getCustomDashboardDataCurrency(dynamicTable));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/callbackAmendment")
    public ResponseEntity<Result> callbackAmendment(@RequestParam String pid, @RequestParam String username) {
        String response = tableService.callbackAmendment(pid, username);
        HttpStatus type = HttpStatus.OK;
        if (!"Callback Amendment request submitted successfully".equals(response)) {
            type = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        Result result = new Result(response);
        return new ResponseEntity<>(result, type);
    }

    @GetMapping(path = "/utilizationReversal")
    public ResponseEntity<Result> utilizationReversal(@RequestParam String pid, @RequestParam String username) {
        String response = tableService.utilizationReversal(pid, username);
        HttpStatus type = HttpStatus.OK;
        if (!"Utilization reversal request submitted successfully".equals(response)) {
            type = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        Result result = new Result(response);
        return new ResponseEntity<>(result, type);
    }

    @ApiOperation(value = "Get TransactionSubType")
    @GetMapping(path = "/getMasterData/")
    public ResponseEntity<Result> getMasterData(@RequestParam String type) {
        List<String> masterData = tableService.getMasterData(type);
        HttpStatus status = HttpStatus.OK;
        if (CollectionUtils.isEmpty(masterData)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        Result result = new Result(masterData);
        return new ResponseEntity<>(result, status);
    }
}
