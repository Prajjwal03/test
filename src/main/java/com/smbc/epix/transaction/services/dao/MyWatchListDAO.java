package com.smbc.epix.transaction.services.dao;

import com.smbc.epix.transaction.services.model.WatchListDetails;

public interface MyWatchListDAO {

    Long getCountForWatchlist(WatchListDetails myWatchList);

    Boolean createMyWatchList(WatchListDetails myWatchList);

    Boolean updateMyWatchList(WatchListDetails myWatchList);

    Boolean unWatchList(WatchListDetails myWatchList);
}
