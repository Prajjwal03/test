package com.smbc.epix.transaction.services.dao.impl;

import com.smbc.epix.transaction.services.dao.BaseDAO;
import com.smbc.epix.transaction.services.dao.DashBoardDAO;
import com.smbc.epix.transaction.services.dto.table.ToDOTableDetailsDTO;
import com.smbc.epix.transaction.services.model.CommonToDO;
import com.smbc.epix.transaction.services.utils.Constants;
import org.springframework.stereotype.Repository;

@Repository
public class DashBoardDAOImpl extends BaseDAO implements DashBoardDAO {

    @Override
    public ToDOTableDetailsDTO getCommonToDoDetails(CommonToDO commonToDo) {
        ToDOTableDetailsDTO toDoDetails = new ToDOTableDetailsDTO();
        int count = commonToDo.getCount();
        String countMethod = "";
        String detailMethod = "";
        if (commonToDo.getExtTableName().equals(Constants.DFX_EXTERNAL_TABLE)) {
            countMethod = "DashBoardDetails.getCommonToDoDetailsCountDfx";
            detailMethod = "DashBoardDetails.getCommonToDoDetailsDfx";
        } else {
            if (commonToDo.getExtTableName().equals(Constants.TREASURY_EXTERNAL_TABLE)) {
                countMethod = "DashBoardDetails.getCommonToDoDetailsCount";
                detailMethod = "DashBoardDetails.getCommonToDoDetails";
            }
        }
        if (count == 0) {
            ToDOTableDetailsDTO rowCount = (ToDOTableDetailsDTO) selectOne(countMethod, commonToDo);
            toDoDetails.setCount(rowCount.getCount());
            toDoDetails.setToDoDetails(selectList(detailMethod, commonToDo));
            return toDoDetails;

        } else {
            toDoDetails.setCount(count);
            toDoDetails.setToDoDetails(selectList(detailMethod, commonToDo));
            return toDoDetails;

        }
    }

    @Override
    public ToDOTableDetailsDTO getMyWatchListDetails(CommonToDO commonToDO1) {
        ToDOTableDetailsDTO toDoDetails = new ToDOTableDetailsDTO();

        int count = commonToDO1.getCount();
        String countMethod = "";
        String detailMethod = "";
        if (commonToDO1.getExtTableName().equals(Constants.DFX_EXTERNAL_TABLE)) {
            countMethod = "DashBoardDetails.getMyWatchListDetailsCountDfx";
            detailMethod = "DashBoardDetails.getMyWatchListDetailsDfx";
        } else {
            if (commonToDO1.getExtTableName().equals(Constants.TREASURY_EXTERNAL_TABLE)) {
                countMethod = "DashBoardDetails.getMyWatchListDetailsCount";
                detailMethod = "DashBoardDetails.getMyWatchListDetails";
            }
        }
        if (count == 0) {
            ToDOTableDetailsDTO rowCount = (ToDOTableDetailsDTO) selectOne(countMethod, commonToDO1);
            toDoDetails.setCount(rowCount.getCount());
            toDoDetails.setToDoDetails(selectList(detailMethod, commonToDO1));
            return toDoDetails;
        } else {
            toDoDetails.setCount(count);
            toDoDetails.setToDoDetails(selectList(detailMethod, commonToDO1));
            return toDoDetails;
        }
    }
}
