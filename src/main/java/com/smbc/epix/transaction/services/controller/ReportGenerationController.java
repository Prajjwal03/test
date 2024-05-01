package com.smbc.epix.transaction.services.controller;

import java.util.List;

import com.smbc.epix.core.utils.Result;
import com.smbc.epix.transaction.services.dto.ReportGenerationDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;
import com.smbc.epix.transaction.services.service.ReportGenerationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/report")
public class ReportGenerationController {

    @Autowired
    private ReportGenerationService reportGenerationService;

    @ApiOperation(value = "Get Report Details")
    @GetMapping(value = "/dtls")
    public ResponseEntity<Result> getReportDetails(@RequestParam(value = "userIndex") int userIndex,
                                                @RequestParam(value = "branchCode") String branchCode,
                                                @RequestParam(value = "productCode") String productCode) {

        DynamicTable dynamicQuery = new DynamicTable();
        dynamicQuery.setPassingValue1(userIndex);
        dynamicQuery.setSearchString1("'" + branchCode + "'");
        dynamicQuery.setSearchString2("'" + productCode + "'");
        List<ReportGenerationDTO> reportDetails = reportGenerationService.getReportDetails(dynamicQuery);

        Result result;
        if (!CollectionUtils.isEmpty(reportDetails)) {
            result = new Result(reportDetails);
        } else {
            result = new Result("No Record Found");
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
