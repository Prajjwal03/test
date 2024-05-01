package com.smbc.epix.transaction.services.controller;

import com.smbc.epix.transaction.services.service.impl.FoxContractServiceImpl;
import com.smbc.epix.transaction.services.utils.NGLogger;
import com.smbc.epix.transaction.services.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/contract", produces = "application/json")
public class FoxContractController {

    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private FoxContractServiceImpl foxContractServiceImpl;

    @GetMapping(path = "/latestVersion")
    public ResponseEntity<Result> contractStatus(@RequestParam("contractNo") String contractNo) {
        ngLogger.consoleLog("Attempting to get latest version for the contract: " + contractNo);
        Result result = new Result(foxContractServiceImpl.getLatestVersion(contractNo));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
