package com.smbc.epix.transaction.services.controller;

import javax.validation.Valid;

import com.smbc.epix.core.utils.Result;
import com.smbc.epix.transaction.services.model.WatchListDetails;
import com.smbc.epix.transaction.services.service.MyWatchListService;
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
@RequestMapping(path = "/myWatchList", produces = "application/json")
public class MyWatchListController {
    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private MyWatchListService myWatchListService;

    @ApiOperation(value = "Create-WatchList")
    @PostMapping(path = "/createWatchList")
    public ResponseEntity<Result> createWatchList(@Valid @RequestBody WatchListDetails watchListDetails) {
        ngLogger.consoleLog(String.format("Attempt to add transaction: %s, to watchlist for user %s",
                watchListDetails.getTransactionRefNo(), watchListDetails.getCreatedBy()));
        return new ResponseEntity<>(new Result(myWatchListService.createMyWatchList(watchListDetails)), HttpStatus.OK);
    }

    @ApiOperation(value = "un-WatchList")
    @PostMapping(path = "/unWatchList")
    public ResponseEntity<Result> unWatchList(@Valid @RequestBody WatchListDetails watchListDetails) {
        ngLogger.consoleLog(String.format("Attempt to remove transaction: %s, from watchlist for user %s",
                watchListDetails.getTransactionRefNo(), watchListDetails.getCreatedBy()));
        return new ResponseEntity<>(new Result(myWatchListService.unWatchList(watchListDetails)), HttpStatus.OK);
    }
}
