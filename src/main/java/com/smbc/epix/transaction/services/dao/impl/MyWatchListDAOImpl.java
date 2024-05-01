package com.smbc.epix.transaction.services.dao.impl;

import com.smbc.epix.transaction.services.dao.BaseDAO;
import com.smbc.epix.transaction.services.dao.MyWatchListDAO;
import com.smbc.epix.transaction.services.model.WatchListDetails;
import org.springframework.stereotype.Repository;

@Repository
public class MyWatchListDAOImpl extends BaseDAO<WatchListDetails> implements MyWatchListDAO {

    @Override
    public Boolean createMyWatchList(WatchListDetails myWatchList) {
        return insert("MyWatchListNameSpace.createMyWatchList", myWatchList);
    }

    @Override
    public Long getCountForWatchlist(WatchListDetails myWatchList) {
        return count("MyWatchListNameSpace.getCountForWatchlist", myWatchList);
    }

    @Override
    public Boolean updateMyWatchList(WatchListDetails myWatchList) {
        return update("MyWatchListNameSpace.updateMyWatchList", myWatchList);
    }

    @Override
    public Boolean unWatchList(WatchListDetails myWatchList) {
        return update("MyWatchListNameSpace.unWatchList", myWatchList);
    }
}
