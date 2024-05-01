package com.smbc.epix.transaction.services.dao;

import com.smbc.epix.transaction.services.dto.table.ToDOTableDetailsDTO;
import com.smbc.epix.transaction.services.exception.DAOException;
import com.smbc.epix.transaction.services.model.CommonToDO;

public interface DashBoardDAO {

    ToDOTableDetailsDTO getCommonToDoDetails(CommonToDO commonToDO) throws DAOException;

    ToDOTableDetailsDTO getMyWatchListDetails(CommonToDO commonToDO) throws DAOException;
}
