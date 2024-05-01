package com.smbc.epix.transaction.services.service;

import com.smbc.epix.transaction.services.model.WatchListDetails;

public interface MyWatchListService {

    String createMyWatchList(WatchListDetails myWatchList);

    String unWatchList(WatchListDetails myWatchList);
}
