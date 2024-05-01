package com.smbc.epix.transaction.services.service;

import com.amazonaws.util.json.JSONException;
import com.smbc.epix.transaction.services.dto.table.ToDOTableDetailsDTO;
import com.smbc.epix.transaction.services.model.DynamicTable;

public interface DashBoardService {

    ToDOTableDetailsDTO getDashBoardDetailsCommon(DynamicTable dynamicQuery) throws JSONException;

    ToDOTableDetailsDTO getMyWatchList(DynamicTable dynamicQuery) throws JSONException;
}
