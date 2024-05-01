package com.smbc.epix.transaction.services.service.impl;

import com.smbc.epix.transaction.services.dao.MyWatchListDAO;
import com.smbc.epix.transaction.services.exception.DAOException;
import com.smbc.epix.transaction.services.model.WatchListDetails;
import com.smbc.epix.transaction.services.service.MyWatchListService;
import com.smbc.epix.transaction.services.utils.NGLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyWatchListServiceImpl implements MyWatchListService {
    @Autowired
    private NGLogger ngLogger;

    @Autowired
    private MyWatchListDAO watchListDAO;

    @Override
    public String createMyWatchList(WatchListDetails watchListDetails) {
        Long count = watchListDAO.getCountForWatchlist(watchListDetails);

        String statusCode = "Failed";

        try {
            Boolean status;
            if (count != null && count >= 1) {
                status = watchListDAO.updateMyWatchList(watchListDetails);
            } else {
                status = watchListDAO.createMyWatchList(watchListDetails);
            }

            if (Boolean.TRUE.equals(status)) {
                statusCode = "Watchlisted Successfully";
            }
        } catch (DAOException e) {
            ngLogger.errorLog(String.format("Exception while adding transaction: %s, to watchlist for user %s, with error: %s",
                    watchListDetails.getTransactionRefNo(), watchListDetails.getCreatedBy(), e.getMessage()));
        }

        return statusCode;
    }

    @Override
    public String unWatchList(WatchListDetails watchListDetails) {
        String statusCode = "Failed";

        try {
            if (Boolean.TRUE.equals(watchListDAO.unWatchList(watchListDetails))) {
                statusCode = "Un-Watched Successfully";
            }
        } catch (DAOException e) {
            ngLogger.errorLog(String.format("Exception while un-watching transaction: %s, for user %s, with error: %s",
                    watchListDetails.getTransactionRefNo(), watchListDetails.getCreatedBy(), e.getMessage()));
        }

        return statusCode;
    }
}
